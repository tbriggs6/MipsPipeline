import junit.framework.TestCase;
import instr.*;

/*
 * Created on Aug 4, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

public class TestInstr extends TestCase {

	
	public void testParseInstruction( ) throws InstructionFormatException
	{
		Instruction I = Instruction.parseInstruction(0x00af8020);
		System.out.println(I.toString());
	}
	
	public void testParseInstruction2( ) throws InstructionFormatException
	{
		Instruction I = Instruction.parseInstruction(0x8c10beef);
		System.out.println(I.toString());
	}
	
	public void testParseInstruction3( ) throws InstructionFormatException
	{
		Instruction I = Instruction.parseInstruction(0x0C00beef);
		System.out.println(I.toString());
	}
	
	public void testParseInstruction4( ) throws InstructionFormatException
	{
		String opcode = "010001";
		String fmt = "10000";
		String ft = "00000";
		String fs = "00001";
		String fd = "00011";
		String func = "000000";
		
		String bits = opcode + fmt + ft + fs + fd + func;
		if (bits.length() != 32) 
			fail("Invalid bit string");
		
		Instruction I = Instruction.parseInstruction( Integer.parseInt(bits,2));
		System.out.println(I.toString());
	}
	
	public void testParseInstruction5( ) 
	{
		String opcode = "010001";
		String fmt = "10001";
		String ft = "00000";
		String fs = "00001";
		String fd = "00011";
		String func = "000000";
		
		String bits = opcode + fmt + ft + fs + fd + func;
		if (bits.length() != 32) 
			fail("Invalid bit string");
		
		try {
			Instruction I = Instruction.parseInstruction( Integer.parseInt(bits,2));
			fail("did not throw invalid register format");
		}
		catch(InstructionFormatException E)
		{
			;
		}
	}
	
	public void testParseInstruction6( ) throws InstructionFormatException
	{
		String opcode = "010001";
		String fmt = "10001";
		String ft = "00010";
		String fs = "00100";
		String fd = "00010";
		String func = "000111";
		
		String bits = opcode + fmt + ft + fs + fd + func;
		if (bits.length() != 32) 
			fail("Invalid bit string");
		
		Instruction I = Instruction.parseInstruction( Integer.parseInt(bits,2));
		System.out.println(I.toString());
	}
	
	public void testParseInstr7( ) throws InstructionFormatException
	{
		String instr = "ADD\t$s0,$a1,$t7\t;dkdkd";
		Instruction I = Instruction.parseInstruction(instr);
		System.out.println(I.toString());
	}
	
	public void testParseInstr8( ) throws InstructionFormatException
	{
		String instr = "JAL\t0xf3f3\t;dkdkd";
		Instruction I = Instruction.parseInstruction(instr);
		System.out.println(I.toString());
	}

	public void testParseInstr9( ) throws InstructionFormatException
	{
		String instr = "ADD.D\t$f0,$f2,$f4\t;dkdkd";
		Instruction I = Instruction.parseInstruction(instr);
		System.out.println(I.toString());
	}
}
