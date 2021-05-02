/*
 * Created on Aug 5, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
import mips.*;
import instr.*;

public class executeSimpleMIPS {


	
	
	public static void main(String args[])
	{
	 try {
		 
		/* Construct the memory */
		Instruction instr[] = SimpleAssembler.assembleFile("input.s");
		Memory M = new Memory(0x10000);		// allocate 64K words
		
		int address;
		for (int i = 0; i < instr.length; i++)
		{
			address = 0x4000 + (i*4);
			
			System.out.println("Storing " + instr[i] + "(" + instr[i].toInt() + ") to location " + address + "\n");
			M.storeWord(address, instr[i].toInt( ));
		}
		
		simpleSimulator S = new simpleSimulator(M);
		S.execute();
		
		
	 }
	 catch(Throwable E)
	 {
		 E.printStackTrace( );
	 }
		
		
		
	}
	
}
