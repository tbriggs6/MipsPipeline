/*
 * Created on Aug 4, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package instr;

public class Jtype extends Instruction {

	int address;

	public Jtype(String mnemonic, int opcode, int address) {
		super(mnemonic, opcode);
		this.address = address;

	}

	public Jtype(int instruction) {
		super("NOP", 00);

		opcode = (instruction >> 26) & 63;
		address = (instruction & 0x3FFFFFF);
	}

	public Jtype(String instruction) throws InstructionFormatException {
		super("NOP", 00);

		String instr = super.parseStripComment(instruction);
		String opStr = super.parseGetMnemonic(instr);
		String opFlds[] = super.parseRegFields(instruction);

		opcode = super.getOpcode(opStr);
		if (opFlds[0].startsWith("0X"))
			address = Integer.parseInt(opFlds[0].substring(2),16);
		else
			address = Integer.parseInt(opFlds[0]);

	}

	
	public int toInt( )
	{
		return (opcode << 26) | (address & 0x3FFFFFF);
	}
	
	public String toString() {

		try {
			return getMnemonic(opcode, 0) + "\t" + "0x"
					+ Integer.toHexString(address);
		} catch (InstructionFormatException E) {
			E.printStackTrace();
			return E.getMessage();
		}

	}
	
	public boolean isJtype( )
	{
		return true;
	}
	
	
	public char getType( )
	{
		return 'J';
	}

	/**
	 * @return Returns the address.
	 */
	public int getAddress() {
		return address;
	}
}
