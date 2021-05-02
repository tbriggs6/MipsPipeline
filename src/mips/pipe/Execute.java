/*
 * Created on Aug 10, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package mips.pipe;

import mips.*;
import instr.*;
import java.util.HashMap;

public class Execute extends PipeStage {
	
	RegisterFile regs;
	
	Memory memory;
	
	PipeRegister IDEX, EXMEM;
	
	IntegerALU ALU;
	
	Instruction currentInstr = null;
	
	CPIMap cpi;
	
	int finishCycle = 0;
	int currState = 0;
	
	HashMap<String, Integer> stats;
	
	public Execute(Memory memory, RegisterFile regs, PipeRegister IDEX,	PipeRegister EXMEM, CPIMap cpi) {
		super("EX", "Execute");
		
		this.memory = memory;
		this.regs = regs;
		this.IDEX = IDEX;
		this.EXMEM = EXMEM;
		this.ALU = new IntegerALU(regs, memory);
		this.cpi = cpi;
		
		this.stats = new HashMap<String, Integer>( );
	}
	
	/**
	 * handleClock - respond to the clock input. a clock signal is either a low
	 * edge or a hight edge (split clock).
	 */
	public boolean handleClock(ClockPulse clock) {
		
		/*
		 * State = 0 (idle) if (clk == high) do nothing, state --> 0 else if
		 * (IDEX is clear) do nothing, state --> 0 else (IDEX is not clear)
		 * latch IDEX, state --> 1 State = 1 (executing) if (clk == low) do
		 * nothing, state --> 1 else if (clk >= finish && EXMEM is clear) latch
		 * EXMEM, state --> 0 else do nothing, state --> 1
		 */
		
		switch (currState) {
		case 0: 
			if (clock.isLow()) {
				if (IDEX.isReady()) {
					this.currentInstr = (Instruction) IDEX.getValue();
					IDEX.clearLatchLock();
					currState = 1;
					
					int stalls = cpi.getCycles(this.currentInstr.getMnemonic());
					
					send(new DebugMessage(5, "EX latched instruction "	+ this.currentInstr));
					this.finishCycle = clock.getClock() + stalls;
					
					super.incrementBusy();
					
				} 
				else {
					send(new DebugMessage(10, "EX is idle in low edge clock"));
					super.incrementStalled();
					currState = 0;
				}
				
			} else {
				send(new DebugMessage(10, "EX is idle in high edge clock "));
				currState = 0;
				super.incrementStalled();
			}
			break;
			
		case 1: 
			if (clock.isLow()) {
				super.incrementBusy();
				send(new DebugMessage(5, "EX is executing instruction "	+ this.currentInstr + " until clock " + this.finishCycle));
				super.setCurrentInstruction( this.currentInstr );
				currState = 1;
				break;
			} else {
				
				/* into the high edge of the execute cycle */
				if (clock.getClock() >= this.finishCycle) // the instruction
					// is finished
				{
					if (EXMEM.isAvailable()) {
						try {
							ALU.execute(this.currentInstr);
						} catch (InstructionExecuteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						WriteBackResult result = ALU.getResult();
						
						super.incrementBusy();
						send(new DebugMessage(5, "EX completed instruction " + this.currentInstr + " in clock "	+ clock.getClock() + " results: " + result));
						super.setCurrentInstruction( this.currentInstr );
						accumulateStats( );
						EXMEM.setValue(result);
						currState = 0;
						break;
					}
					if (EXMEM.isReady()) {
						send(new DebugMessage(5, "EX completed instruction " + this.currentInstr + " in clock " + clock.getClock() + " but stalled on EXMEM latch"));
						super.setCurrentInstruction( this.currentInstr );
						super.incrementStalled();
						currState = 1;
						break;
					}
				} else {
					send(new DebugMessage(5,"EX is still executing instruction " + this.currentInstr + " in clock " + clock.getClock()));
					super.setCurrentInstruction( this.currentInstr );
					super.incrementBusy();
					currState = 1;
					break;
				}
				
			} // end if
			break;
		} // end switch
		
		return true;
	} // end method
	
	public void handlePoisonPill( )
	{
		try {
			this.currentInstr = Instruction.parseInstruction(0);
		}
		catch(InstructionFormatException E)
		{
			this.currentInstr = null;
		}
		
		send(new DebugMessage(5,"received a poison pill"));
	}
	
	private void accumulateStats( )
	{
		String m = this.currentInstr.getMnemonic();
		
		int count = 0;
		if (stats.containsKey(m))
			count = stats.get(m);
		
		stats.put(m,count+1);
	}
	
	public HashMap<String,Integer>getRetiredStats( )
	{
		return stats;
	}
}
