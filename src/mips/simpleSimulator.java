/*
 * Created on Aug 4, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package mips;

import instr.*;
import java.io.*;
	
public class simpleSimulator {

	Memory mem;
	RegisterFile regs;
	SimpleALU alu;
	
	boolean debug = true;
	
	public simpleSimulator(Memory mem)
	{
		 this.mem = mem;
		 this.regs = new RegisterFile( );
		 this.alu = new SimpleALU(regs, mem);
	}
	
	public simpleSimulator(Memory mem, RegisterFile regs, SimpleALU alu) {
		this.mem = mem;
		this.regs = regs;
		this.alu = alu;
	}

	public void execute() {
		execute(0x4000);
	}

	public void execute(int address) {
		regs.setPC(address);
		
		
		
		try {
			setReturnAddress();
			
			int pc = 0;
			int instr = 0;
			do {
				
				/* fetch instruction */
				pc = regs.getPC();
				instr = mem.getWord(pc);
				
				/* increment PC */
				pc += 4;
				regs.setPC(pc);

				/* decode the instruction */
				Instruction I = Instruction.parseInstruction(instr);

				if (debug) {
					System.out.println("Register File: \n" + regs);
					System.out.println("PC: " + (regs.getPC()-4) + "(" + Integer.toHexString(regs.getPC()-4) + ")" + 							
							"\t" + I.toString() + "\n");
				}
				
				/* execute the instruction */
				alu.execute(I);

				waitForKey( );
				
			} while(regs.getPC() != 0x200);
		} catch (InstructionFormatException E) {
			System.err.println("----------| INVALID INSTRUCTION TRAP |----------------\n");
			System.err.println("Register File: \n" + regs);
			System.err.println(E.getMessage());
			
		} catch (Exception E) {
			System.err.println("----------| GENERAL PROGRAM FAULT |----------------\n");
			System.err.println("Register File: \n" + regs);
			System.err.println(E.getMessage());
		} catch (Throwable E) {
			System.err.println("----------| GENERAL SIMULATOR FAULT |----------------\n");
			System.err.println("Register File: \n" + regs);
			System.err.println(E.getMessage());
		}
	}

	/**
	 * @throws InstructionFormatException
	 */
	private void setReturnAddress() throws InstructionFormatException {
		int RAreg = Instruction.getRegNum("$ra");
		int raValue = 0x200;
		regs.setReg(RAreg, raValue);
	}

	private void waitForKey( )
	{
		try {
			System.out.println("<<press a key>>\n");
			InputStreamReader I = new InputStreamReader(System.in);
			I.read();
			return;
		}
		catch(Exception E)
		{
			;;
		}
		
		try {
			Runtime.getRuntime().wait(10);
		}
		catch(Exception E)
		{
			;;
		}
		
	}
}
