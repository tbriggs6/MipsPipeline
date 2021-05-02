/*
 * Created on Aug 4, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package instr;

public class Itype extends Instruction {

	int rs, rt, address;
	
	public Itype( String mnemonic, int opcode, int rs, int rt, int address)
	{
		super(mnemonic, opcode);
		
		this.rs = rs;
		this.rt = rt;

	}
	
	public Itype(int instruction)
	{
		super("NOP", 00);

		
		opcode = (instruction >> 26) & 63;
		rs = (instruction >> 21) & 31;
		rt = (instruction >> 16) & 31;
		address = (instruction & 65535);
	}

	
	public int toInt( )
	{
		return (opcode << 26) | (rs << 21) | (rt << 16) | ( address & 65535);
	}
	
	public Itype(String instruction)
	throws InstructionFormatException
{
	super("NOP",00);
	
	String instr = super.parseStripComment(instruction);
	String opStr = super.parseGetMnemonic(instr);
	String opFlds[] = super.parseRegFields(instruction);
	
	
	opcode = super.getOpcode(opStr);
	
	if (opFlds.length == 2)
	{
		rt = super.getRegNum(opFlds[0]);
		rs = 0;
	
		if ((opFlds[1].startsWith("0x") || opFlds[1].startsWith("0X")))
			address = Integer.parseInt(opFlds[1].substring(2),16);
		else
			address = Integer.parseInt(opFlds[1]);
	}
	else 
	{
		try {
			rt = super.getRegNum(opFlds[0]);
			rs = super.getRegNum(opFlds[1]);

			
			if ((opFlds[2].startsWith("0x") || opFlds[2].startsWith("0X")))
				address = Integer.parseInt(opFlds[2].substring(2),16);
			else
				address = Integer.parseInt(opFlds[2]);
		}
		catch(NumberFormatException E)
		{
			System.err.println("Err: " + instr + " caused a numeric format exception on " + opFlds[1]);
			E.printStackTrace();
		}
	}
	
}
	
	public String toString( )
	{

		try {
			return getMnemonic(opcode, 0) + "\t" + 
				getRegName(rt) + "," + getRegName(rs) + "," + "0x" + Integer.toHexString(address) ;
		}
		catch(InstructionFormatException E)
		{
			E.printStackTrace();
			return E.getMessage();
		}
	
	}

	public boolean isItype( )
	{
		return true;
	}
	
	
	public char getType( )
	{
		return 'I';
	}

	/**
	 * @return Returns the address.
	 */
	public int getAddress() {
		return address;
	}

	/**
	 * @return Returns the rs.
	 */
	public int getRs() {
		return rs;
	}

	/**
	 * @return Returns the rt.
	 */
	public int getRt() {
		return rt;
	}
}

