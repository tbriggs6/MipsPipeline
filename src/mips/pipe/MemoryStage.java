/*
 * Created on Aug 10, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package mips.pipe;

import mips.*;
import instr.*;

public class MemoryStage extends PipeStage {

	RegisterFile regs;
	Memory memory;
	PipeRegister EXMEM, MEMWB;
	CPIMap cpi;
	
	WriteBackResult currentResult;
	int finishCycle = 0;
	int currState = 0;
	
	
	public MemoryStage(Memory memory , RegisterFile regs, PipeRegister EXMEM, PipeRegister MEMWB, CPIMap cpi) {
		super("MEM", "Memory");
		
		this.memory = memory;
		this.regs = regs;
		this.EXMEM = EXMEM;
		this.MEMWB = MEMWB;
		this.cpi = cpi;
	}

	
	private void executeMemoryOp( )
	{
		int tmp, address, register;
		byte high, low;
		
		if (!currentResult.isMemoryOp()) {
			return;
		}
		
		address = currentResult.getValue();
		register = currentResult.getTarget();
		Instruction instr = currentResult.getInstr();
		
		
		if (currentResult.isMemIn() || currentResult.isFMemIn())		// a load
		{
			
			switch(currentResult.getMemType())
			{
			case WriteBackResult.BYTE:
			
				tmp = memory.getByte( address );

				if (currentResult.isMemIn())
					currentResult = WriteBackResult.setRegResult(instr,register, tmp);
				else
					throw new RuntimeException("FRegs do not support byte operations");
				break;
				
			case WriteBackResult.HWORD:
				high = memory.getByte( address);
				low = memory.getByte( address + 1);
				
				tmp = high << 8 | low;
				
				if (currentResult.isMemIn())
					currentResult = WriteBackResult.setRegResult(instr,register, tmp);
				else
					throw new RuntimeException("FRegs do not support half-word operations");
				break;
				
			case WriteBackResult.WORD:
				tmp = memory.getWord(address);
				
				if (currentResult.isMemIn())
					currentResult = WriteBackResult.setRegResult(instr,register, tmp);
				else
					throw new RuntimeException("FRegs do not support word operations");
				break;
				
			case WriteBackResult.SINGLE:
				tmp = memory.getWord(address);
				
				if (currentResult.isFMemIn())
					currentResult = WriteBackResult.setFRegResult(instr,true, register, tmp);
				else
					throw new RuntimeException("Integer regs do not support single operations");
				break;
				
			case WriteBackResult.DOUBLE:
				tmp = memory.getWord(address);
				
				if (currentResult.isFMemIn())
					currentResult = WriteBackResult.setFRegResult(instr,false, register, tmp);
				else
					throw new RuntimeException("Integer regs do not support  double operations");

				break;
			}
		
		}
		else if (currentResult.isMemOut()) 
		{
			tmp = regs.getReg(register);

			switch(currentResult.getMemType())
			{
			case WriteBackResult.BYTE:
				tmp = tmp & 0xff;
				memory.storeByte(address, (byte) tmp);
				break;
				
			case WriteBackResult.HWORD:
				tmp = tmp & 0xffff;
				
				memory.storeByte(address, (byte)((tmp >> 8) & 0xff));
				memory.storeByte(address+1, (byte)(tmp & 0xff));
				
				break;
				
			case WriteBackResult.WORD:
				memory.storeWord(address, tmp);
				break;
			}
			
			currentResult = WriteBackResult.setNoResult(instr);
		}
		else if (currentResult.isFMemOut()) 
		{
			tmp = Float.floatToRawIntBits(regs.getSFreg(register));

			switch(currentResult.getMemType())
			{
			case WriteBackResult.BYTE:
			case WriteBackResult.HWORD:
			case WriteBackResult.WORD:
				throw new RuntimeException("There shouldn't ever be an integer write from MFC");
				
			case WriteBackResult.SINGLE:
				memory.storeWord(address, tmp);
				break;
				
			case WriteBackResult.DOUBLE:
				double dtmp = currentResult.getValue();
				long lword = Double.doubleToRawLongBits(dtmp);
				
				int ihigh = (int)(lword >> 32) & 0xFFFFFFFF;
				int ilow = (int)(lword & 0xFFFFFFFF);
				
				memory.storeWord(address, ihigh);
				memory.storeWord(address+4, ilow);
				break;
			}
			
			currentResult = WriteBackResult.setNoResult(instr);
		}
		
	}
	
	/**
	 * handleClock - respond to the clock input.  a clock signal is
	 * either a low edge or a hight edge (split clock).
	 */
	public boolean handleClock(ClockPulse clock) {

		/**
		 * state 0 is idle state
		 * state 1 is reading/writing state
		 */
		if (currState == 0)
		{
			 if (clock.isLow() && !EXMEM.isReady())
			 {
				 send(new DebugMessage(10,"MEM is idle waiting for EX"));
				 
				 super.incrementStalled();
				 currState = 0;
			 }
			 else if (!clock.isLow() && !EXMEM.isAvailable())
			 {
				 /* latch the value from the register */
				 this.currentResult = (WriteBackResult) EXMEM.getValue();
				 EXMEM.clearLatchLock();
				 
				 if (this.currentResult == null) {
					 send(new DebugMessage(10,"MEM idle, waiting for first operation"));
				
					 super.incrementStalled();
					 return false;
				 }
				
				 
				 send(new DebugMessage(5,"MEM latched result from EX in clock " + clock));
				 
				 
				 super.incrementBusy();
				 
				 if (currentResult.isMemoryOp())
				 {
					 this.finishCycle = clock.getClock() + cpi.getCycles("LD");
				 }
				 else
				 {
					 this.finishCycle = clock.getClock();
				 }
				 
				 currState = 1;
			 }
			 else
			 {
				 super.incrementStalled();
			 }
		}
		else if (currState == 1)
		{
			super.setCurrentInstruction(currentResult.getInstr());
			if (clock.isLow())
			{
				send(new DebugMessage(10,"MEM waiting for memory "));
			}
			else
			{ // high clock
				
				if (MEMWB.isAvailable())
				{
					executeMemoryOp( );
					send(new DebugMessage(5,"MEM completed memory access in clock " + clock));
					MEMWB.setValue(currentResult);
					super.setCurrentInstruction(currentResult.getInstr());
					currState = 0;
					super.incrementBusy();
				}
				else
				{
					send(new DebugMessage(10,"MEM waiting for WB to clear in clock " +  clock));
					super.incrementStalled();
					currState = 1;
				}
			}
		}
		else
		{
			throw new RuntimeException("invalid state.");
		}

		return true;
	}

	public void handlePoisonPill( )
	{
		this.currentResult = WriteBackResult.setNoResult(this.currentInstruction);
		send(new DebugMessage(5,"received a poison pill"));
	}
}
