/*
 * Created on Aug 4, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package instr;

public class InstructionFormatException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InstructionFormatException(String msg, int opcode, int func)
	{
		super(msg + " \n(offending opcode=" + opcode + "  function: " + func + ")");
	}
	
	public InstructionFormatException(String msg, String mnemonic)
	{
		super(msg + " \n(offending instr=" + mnemonic + ")");
	}
	
	public InstructionFormatException(String msg)
	{
		super(msg);
	}
}
