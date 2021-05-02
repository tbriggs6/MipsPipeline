/*
 * Created on Aug 10, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package mips.pipe;

import mips.*;
import instr.*;

public class InstructionDecode extends PipeStage {

	RegisterFile regs;
	Memory memory;
	PipeRegister IFID, IDEX;

	int currentWord;
	
	public InstructionDecode(Memory memory , RegisterFile regs, PipeRegister IFID, PipeRegister IDEX ) {
		super("ID", "Instruction Decode");
		
		this.memory = memory;
		this.regs = regs;
		this.IFID = IFID;
		this.IDEX = IDEX;
	}

	
	/**
	 * handleClock - respond to the clock input.  a clock signal is
	 * either a low edge or a hight edge (split clock).
	 */
	public boolean handleClock(ClockPulse clock) {

		if (!clock.isLow())
		{
			if (IFID.isReady()) 
			{
				if (!IDEX.isAvailable())
				{
					send(new DebugMessage(5,"ID waiting for EX"));
					super.incrementStalled();
					return false;
				}
					
				// retrieve the loaded instruction from IFID register 
				Integer word = (Integer) IFID.getValue();
				IFID.clearLatchLock();
				
				if (word == null)
				{
					super.incrementStalled();
					send(new DebugMessage(10,"ID waiting for initial operation."));
					return false;
				}
				currentWord = word.intValue();
				
				send(new DebugMessage(5,"ID retrieved instruction " + Integer.toHexString(currentWord)));
				
				
				super.incrementBusy();
				return true;
			}
			else
			{
				send(new DebugMessage(10,"ID waiting for IF"));
				super.incrementStalled();
				return true;
			}
			
		}
		else
		{
			if (IFID.wasUsed() && IDEX.isAvailable()) {
				try {
					// decode the instruction
					Instruction I = Instruction.parseInstruction(currentWord);
					IDEX.setValue(I);
					
					send(new DebugMessage(5,"ID decoded instruction " + I.toString()));
					super.setCurrentInstruction(currentWord);
					super.incrementBusy();
				
					return true;
				}
				catch(InstructionFormatException E)
				{
					throw new RuntimeException("Error: the instruction was not valid.");
				}
			}
			else
			{
				send(new DebugMessage(10,"ID stalled on EX"));
				super.incrementStalled();
				return false;				
			}
		}
	}

	public void handlePoisonPill( )
	{
		this.currentWord = 0;
		
		send(new DebugMessage(5,"received a poison pill"));
	}
}
