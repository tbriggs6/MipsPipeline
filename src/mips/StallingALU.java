/*
 * Created on Aug 10, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package mips;
import instr.*;

public class StallingALU extends SimpleALU {

	CPIMap map;
	
	public StallingALU(RegisterFile regs, Memory mem,CPIMap map) {
		super(regs, mem);

		this.map = map;
	}

	/**
	 * Execute an instruction, and simulate how long it will take to clear the ALU
	 * @param I
	 * @param clock
	 * @throws InstructionExecuteException
	 */
	public void execute(Instruction I, int clock)
		throws InstructionExecuteException 
	{
		 
	}
	
}
