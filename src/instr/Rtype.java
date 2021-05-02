/*
 * Created on Aug 4, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package instr;

public class Rtype extends Instruction {

	int rs, rt, rd, shamt, func;
	
	public Rtype( String mnemonic, int opcode, int rs, int rt, int rd, int shamt, int func)
	{
		super(mnemonic, opcode);
		
		this.rs = rs;
		this.rt = rt;
		this.rd = rd;
		this.shamt = shamt;
		this.func = func;
	}
	
	public Rtype(int instruction)
	{
		super("NOP", 00);
		
		opcode = (instruction >> 26) & 63;
		rs = (instruction >> 21) & 31;
		rt = (instruction >> 16) & 31;
		rd = (instruction >> 11) & 31;
		shamt = (instruction >> 6) & 31;
		func = instruction & 63;
		
	}

	public Rtype(String instruction)
		throws InstructionFormatException
	{
		super("NOP",00);
		
		String instr = super.parseStripComment(instruction);
		String opStr = super.parseGetMnemonic(instr);
		String opFlds[] = super.parseRegFields(instruction);
		
		
		opcode = 0;
		func = super.getRFunc(opStr);
		
		if (opStr.equalsIgnoreCase("JR"))
			rs = super.getRegNum(opFlds[0]);
		else {
			rd = super.getRegNum(opFlds[0]);
			rs = super.getRegNum(opFlds[1]);
			rt = super.getRegNum(opFlds[2]);
			shamt = 0;
		}
		
	}
	
	public int toInt( )
	{
		return (opcode << 26) | (rs << 21) | (rt << 16) | (rd << 11) | (shamt << 6) | func;
	}
	
	public String toString( )
	{

		try {
			if (func == 8)
				return getMnemonic(opcode, func) + "\t" + getRegName(rs);
			else 
				return getMnemonic(opcode, func) + "\t" + 
					getRegName(rd) + "," + getRegName(rs) + "," + getRegName(rt) + "\t; " + 
						Integer.toHexString(toInt());
		}
		catch(InstructionFormatException E)
		{
			E.printStackTrace();
			return E.getMessage();
		}
	
	}

	public boolean isRtype( )
	{
		return true;
	}
	
	
	public char getType( )
	{
		return 'R';
	}

	/**
	 * @return Returns the func.
	 */
	public int getFunc() {
		return func;
	}

	/**
	 * @return Returns the rd.
	 */
	public int getRd() {
		return rd;
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

	/**
	 * @return Returns the shamt.
	 */
	public int getShamt() {
		return shamt;
	}
	
	public String getMnemonic( )
	{
		try {
			return getMnemonic(this.getOp(), this.getFunc());
		} catch (InstructionFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}

