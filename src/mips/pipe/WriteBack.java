/*
 * Created on Aug 10, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package mips.pipe;

import mips.*;
import instr.*;

public class WriteBack extends PipeStage {

	RegisterFile regs;
	Memory memory;
	PipeRegister MEMWB;
	CPIMap cpi;
	FiveStagePipeline pipe;
	
	WriteBackResult currentResult;
	int finishCycle = 0;
	int currState = 0;
	
	
	public WriteBack(FiveStagePipeline pipe, Memory memory , RegisterFile regs, PipeRegister MEMWB) {
		super("WB", "Write Back");
		
		this.memory = memory;
		this.regs = regs;
		this.MEMWB = MEMWB;
		this.pipe = pipe; 
	}

	
	private void executeRegisterOp( )
	{
		int tmp, value, register;
		byte high, low;
		
		if (currentResult.isMemoryOp()) return;
		
		value = currentResult.getValue();
		register = currentResult.getTarget();
		
		if (currentResult.isPCOut())
			regs.setPC(value);
		else if(currentResult.isRegOut())
			regs.setReg(register, value);
		else if(currentResult.isFRegOut())
			regs.setSFreg(register, value);
		else if (currentResult.isNoOut())
			return;
		else
			throw new RuntimeException("Error: could not write back " + currentResult); 
		
	}
	
	/**
	 * handleClock - respond to the clock input.  a clock signal is
	 * either a low edge or a hight edge (split clock).
	 */
	public boolean handleClock(ClockPulse clock) {

		if (clock.isLow())
		{
			send(new DebugMessage(10,"WB always idle on low edge of CLK"));
			super.incrementStalled();
			return false;
		}
		else 
		{
			if (!MEMWB.isReady())
			{
				super.setCurrentInstruction(null);
				send(new DebugMessage(10,"WB is idle on high edge of CLK"));
				super.incrementStalled();
				return false;
			}
			else
			{
				currentResult = (WriteBackResult) MEMWB.getValue();
				MEMWB.clearLatchLock();
				
				if (currentResult == null) {
					send(new DebugMessage(10,"WB idle - waiting for initial op"));
					super.incrementStalled();
					return false;
				}
					
				send(new DebugMessage(5,"WB writing " + currentResult));
				super.setCurrentInstruction(currentResult.getInstr());
				
				executeRegisterOp( );
				pipe.forwardWB();
				super.incrementBusy();
				return true;
			}
		}
	}

	public void handlePoisonPill( )
	{
		this.currentResult = WriteBackResult.setNoResult(this.currentInstruction);
		send(new DebugMessage(5,"received a poison pill"));
	}
}
