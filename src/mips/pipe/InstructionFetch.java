/*
 * Created on Aug 10, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package mips.pipe;

import mips.*;

public class InstructionFetch extends PipeStage {

	RegisterFile regs;
	Memory memory;
	PipeRegister IFID;
	
	int memoryDelay = 0;
	int lastReqAvail = 0;
	int reqPC = 0;
	
	public InstructionFetch(Memory memory , RegisterFile regs, PipeRegister IFID) {
		super("IF", "Instruction Fetch");
		
		this.memory = memory;
		this.regs = regs;
		this.IFID = IFID;
	}

	public InstructionFetch(Memory memory , RegisterFile regs, PipeRegister IFID, int delay) {
		this(memory,regs,IFID);
		
		this.memoryDelay = delay;
	}

	
	/**
	 * handleClock - respond to the clock input.  a clock signal is
	 * either a low edge or a hight edge (split clock).
	 */
	public boolean handleClock(ClockPulse clock) {

		if (clock.isLow())
		{
			if (IFID.isAvailable())
			{
				// request memory
				this.lastReqAvail = clock.getClock() + this.memoryDelay;
				this.reqPC = regs.getPC();
				
				regs.setPC(regs.getPC() + 4); // PC = PC + 4

				super.setCurrentInstruction(memory.getWord(reqPC));
				super.incrementBusy();
				
				send(new DebugMessage(5,"read mem at PC=" + Integer.toHexString(this.reqPC) +
						" will be available " + this.lastReqAvail) + " high");
				
				return true;
			}
			else {
				// stalled - so leave instruction set
				
				send(new DebugMessage(10,"skipping low edge, IFID is not clear"));
				super.incrementStalled();
				return false;
			}
			
			
		}
		else
		{
			if (!IFID.isAvailable()) {
				send(new DebugMessage(10,"skipping high edge, IFID is not clear"));
				
				super.incrementStalled();
				return false;
			}
			if (clock.getClock() < this.lastReqAvail) {
				send(new DebugMessage(10,"skipping high edge, blocked on MEM"));
				super.setCurrentInstruction(memory.getWord(reqPC));
				super.incrementStalled();
				return false;
			}
				
			
			// send the instruction in PC to the IF/ID pipe latch
			int word = memory.getWord( this.reqPC );
			
			IFID.setValue(word);
			
			super.setCurrentInstruction(word);
			
			if (word != 0) {
				send(new DebugMessage(5,"sent instruction to ID, PC=" + this.reqPC + " " + Integer.toHexString(word)));
				super.incrementBusy();
				return true;
			}
			else
			{
				send(new DebugMessage(5,"sending NOP"));
				super.incrementStalled();
				return true;
			}
		}
	}

	public void handlePoisonPill( )
	{
		this.lastReqAvail = 0;
		this.reqPC = 0;
		
		send(new DebugMessage(5,"received a poison pill"));
	}
	
	protected void setPC(int pc)
	{
		regs.setPC(pc);
		reqPC = pc;
	}
	
	public String toString( )
	{
		return "lastReqAvail=" + this.lastReqAvail;
	}
}
