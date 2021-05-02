/*
 * Created on Aug 4, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package instr;

public class FRtype extends Instruction {

	int fmt, ft,fs,fd,func;
	
	public FRtype( String mnemonic, int opcode, int fmt, int ft, int fs, int fd, int func)
	{
		super(mnemonic, opcode);
		
		this.fmt = fmt;
		this.ft = ft;
		this.fs = fs;
		this.fd = fd;
		this.func = func;
		
	}
	
	public FRtype(int instruction)
		throws InstructionFormatException
	{
		super("NOP", 00);
		
		opcode = (instruction >> 26) & 63;
		fmt = (instruction >> 21) & 31;
		ft = (instruction >> 16) & 31;
		fs = (instruction >> 11) & 31;
		fd = (instruction >> 6) & 31;
		func = instruction & 63;

		if ((fmt == 17) && (((ft % 2) == 1) || ((fs %2) == 1) || ((fd %2) == 1)))
			throw new InstructionFormatException("XXX.D (double) instruction cannot use odd register.");
		
	}

	public int toInt( )
	{
		int i = (opcode << 26) | (fmt << 21)  | (ft << 16) | (fs << 11) | (fd << 6) | func;
		System.err.println(i);
		
		return i;
	}
	
	public FRtype(String instruction)
	throws InstructionFormatException
{
	super("NOP",00);
	
	String instr = super.parseStripComment(instruction);
	String opStr = super.parseGetMnemonic(instr);
	String opFlds[] = super.parseRegFields(instruction);
	
	
	opcode = super.getOpcode(opStr);
	func = super.getFRFunc(opStr);
	fd = super.getFRegNum(opFlds[0]);
	fs = super.getFRegNum(opFlds[1]);
	ft = super.getFRegNum(opFlds[2]);
	
	if (opStr.contains(".S")) fmt = 16;
	else if (opStr.contains(".D")) fmt = 17;
	
}

	
	public String toString( )
	{
		String fmtStr = ".X";
		
		if (fmt == 16) fmtStr = ".S";
		else if (fmt == 17) fmtStr = ".D";
		else throw new RuntimeException("Unknown format code: " + fmt);
		
		try {
			return getMnemonic(opcode, func) + fmtStr + "\t" + 
				getFRegName(fd) + "," + getFRegName(fs) + "," + getFRegName(ft);
		}
		catch(InstructionFormatException E)
		{
			E.printStackTrace();
			return E.getMessage();
		}
	
	}

	public boolean isSinglePrec( )
	{
		if (this.fmt == 16) return true;
		else return false;
	}
	
	public boolean isFtype( )
	{
		return true;
	}	
	
	
	public char getType( )
	{
		return 'F';
	}

	/**
	 * @return Returns the fd.
	 */
	public int getFd() {
		return fd;
	}

	/**
	 * @return Returns the fmt.
	 */
	public int getFmt() {
		return fmt;
	}

	/**
	 * @return Returns the fs.
	 */
	public int getFs() {
		return fs;
	}

	/**
	 * @return Returns the ft.
	 */
	public int getFt() {
		return ft;
	}

	/**
	 * @return Returns the func.
	 */
	public int getFunc() {
		return func;
	}
	
	public String getMnemonic( )
	{
		String S;
		try {
			S =  getMnemonic(this.opcode, this.func);
			if (this.isSinglePrec()) S = S + ".S";
			else S = S = ".D";
			
			return S.toUpperCase();
			
		} catch (InstructionFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}

