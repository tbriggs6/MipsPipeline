/*
 * Created on Aug 4, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package instr;

public class Instruction {

	String mnemonic;

	int opcode;

	static String ops[] = { null, "BLTZ", "J", "JAL", "BEQ", "BNE", "BLEZ",
			"BGTZ", "ADDI", "ADDIU", "SLTI", "SLTIU", "ANDI", "ORI", "XORI",
			"LUI", "TLB", "FLPT", null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, "LB", "LH", "LWL", "LW",
			"LBU", "LHU", "LWR", null, "SB", "SH", "SWL", "SW", null, null,
			"SWR", null, "LWC0", "LWC1", null, null, null, null, null, null,
			"SWC0", "SWC1", null, null, null, null, null, null };

	static String funcs[] = { "SLL", null, "SRL", "SRA", "SLLV", null, "SRLV",
			"SRAV", "JR", "JALR", null, null, "SYSCALL", "BREAK", null, null,
			"MFHI", "MTHI", "MFLO", "MTLO", null, null, null, null, "MULT",
			"MULTU", "DIV", "DIVU", null, null, null, null, "ADD", "ADDU",
			"SUB", "SUBU", "AND", "OR", "XOR", "NOR", null, null, "SLT",
			"SLTU", null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null };

	static String ffuncs[] = { "ADD", "SUB", "MUL", "DIV", null, "ABS", "MOV",
			"NEG", null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, "CVT.S", "CVT.D", null, null, "CVT.W", null,
			null, null, null, null, null, null, null, null, null, null, "C.F",
			"C.UN", "C.EQ", "C.UEQ", "C.OLT", "C.ULT", "C.OLE", "C.ULE",
			"C.SF", "C.NGLE", "C.SEQ", "C.NGL", "C.LT", "C.NGE", "C.LE",
			"C.NGT" };

	static String tlbfun[] = { "MFC0", null, "CFC0", null, "MTC0", null, "CTC0" };

	static String regname[] = { "$zero", "$at", "$v0", "$v1", "$a0", "$a1",
			"$a2", "$a3", "$t0", "$t1", "$t2", "$t3", "$t4", "$t5", "$t6",
			"$t7", "$s0", "$s1", "$s2", "$s3", "$s4", "$s5", "$s6", "$s7",
			"$t8", "$t9", "$k0", "$k1", "$gp", "$sp", "$fp", "$ra" };

	static String fregname[] = { "$f0", "$f1", "$f2", "$f3", "$f4", "$f5",
			"$f6", "$f7", "$f8", "$f9", "$f10", "$f11", "$f12", "$f13", "$f14",
			"$f15", "$f16", "$f17", "$f18", "$f19", "$f20", "$f21", "$f22",
			"$f23", "$f24", "$f25", "$f26", "$f27", "$f28", "$f29", "$f30",
			"$f31", "$f32" };

	public Instruction(String mnemonic, int opcode) {
		this.mnemonic = mnemonic;
		this.opcode = opcode;
	}

	public static String getRegName(int regNum) {
		return regname[regNum];
	}

	public static String getFRegName(int regNum)
			throws InstructionFormatException {
		if (regNum >= 32)
			throw new InstructionFormatException("Not a valid F-Register");

		return "$f" + regNum;
	}

	public static String getRFunc(int opcode, int func)
			throws InstructionFormatException {
		if (opcode != 0)
			throw new InstructionFormatException("Not an R-Type instruction",
					opcode, func);

		if (funcs[func] == null)
			throw new InstructionFormatException("Not a valid R-Type function",
					opcode, func);

		return funcs[func];
	}

	public static String getFRFunc(int opcode, int func)
			throws InstructionFormatException {
		if (opcode != 0x11)
			throw new InstructionFormatException("Not an F-Type instruction",
					opcode, func);

		if (ffuncs[func] == null)
			throw new InstructionFormatException("Not a valid F-Type function",
					opcode, func);

		return ffuncs[func];
	}

	public static int getRFunc(String mnemonic)
			throws InstructionFormatException {
		for (int i = 0; i < funcs.length; i++) {
			if (funcs[i] == null)
				continue;
			if (funcs[i].equalsIgnoreCase(mnemonic))
				return i;
		}

		throw new InstructionFormatException(
				"Not a valid mnemonic for R-Type: " + mnemonic);
	}

	public static int getFRFunc(String mnemonic)
			throws InstructionFormatException {
		
		if (mnemonic.contains("."))
			mnemonic = mnemonic.substring(0,mnemonic.indexOf("."));
		
		for (int i = 0; i < ffuncs.length; i++) {
			if (ffuncs[i] == null)
				continue;
			if (ffuncs[i].equalsIgnoreCase(mnemonic))
				return i;
		}

		throw new InstructionFormatException(
				"Not a valid mnemonic for FR-Type: " + mnemonic);
	}

	public static int getRegNum(String reg) throws InstructionFormatException {
		for (int i = 0; i < regname.length; i++) {
			if (reg.equalsIgnoreCase(regname[i]))
				return i;
		}

		throw new InstructionFormatException("Not a valid register identifier (" + reg + ")");
	}

	public static int getFRegNum(String reg) throws InstructionFormatException {
		for (int i = 0; i < fregname.length; i++) {
			if (reg.equalsIgnoreCase(fregname[i]))
				return i;
		}

		throw new InstructionFormatException("Not a valid register identifier.");
	}

	public static String getMnemonic(int opcode, int func)
			throws InstructionFormatException {
		if (opcode == 0)
			return getRFunc(opcode, func);
		else if (opcode == 0x11)
			return getFRFunc(opcode, func);
		else
			return ops[opcode];
	}

	public static int getOpcode(String mnemonic)
			throws InstructionFormatException {
		for (int i = 0; i < ops.length; i++) {
			if (ops[i] == null)
				continue;

			if (ops[i].equalsIgnoreCase(mnemonic))
				return i;
		}

		for (int i = 0; i < funcs.length; i++) {
			if (funcs[i] == null)
				continue;

			if (funcs[i].equalsIgnoreCase(mnemonic))
				return 0;
		}

		for (int i = 0; i < tlbfun.length; i++) {
			if (tlbfun[i] == null)
				continue;

			if (tlbfun[i].equalsIgnoreCase(mnemonic))
				return 0x10;
		}

		for (int i = 0; i < ffuncs.length; i++) {
			if (mnemonic.equalsIgnoreCase(ffuncs[i] + ".S"))
				return 0x11;
			if (mnemonic.equalsIgnoreCase(ffuncs[i] + ".D"))
				return 0x11;
		}

		throw new InstructionFormatException("Not a Valid Mnemonic ", mnemonic);
	}

	public static Instruction parseInstruction(int instr)
			throws InstructionFormatException {

		int opcode = (instr >> 26) & 63;

		if (opcode == 0)
			return new Rtype(instr);

		if (opcode == 17)
			return new FRtype(instr);

		if ((opcode == 2) || (opcode == 3))
			return new Jtype(instr);

		return new Itype(instr);
	}

	public static Instruction parseInstruction(String instr)
			throws InstructionFormatException {
		String m = parseGetMnemonic(instr);

		try {
			getRFunc(m);
			return new Rtype(instr);
		} catch (InstructionFormatException E) {
			;
			;
		}

		if ((m.equalsIgnoreCase("J") || m.equalsIgnoreCase("JAL")))
			return new Jtype(instr);

		try {
			getFRFunc(m);
			return new FRtype(instr);
		}
		catch(InstructionFormatException E)
		{
		 ;;
		}

		return new Itype(instr);

	}

	public String toString() {
		try {
			return getMnemonic(opcode, 0) + " ";
		} catch (InstructionFormatException E) {
			E.printStackTrace();
			return E.getMessage();
		}

	}

	protected static String parseStripComment(String assemblyLine) {
		StringBuffer buff = new StringBuffer();

		int i = 0;
		while (i < assemblyLine.length()) {
			char ch = assemblyLine.charAt(i++);
			if ((ch == ';') || (ch == '#'))
				break;
			buff.append(ch);
		}

		return buff.toString();

	}

	protected static String parseGetMnemonic(String instr) {

		instr = instr.toUpperCase();

		StringBuffer buff = new StringBuffer();

		/*
		 * DFA to parse a mnemonic 
		 * state read 	next state note 
		 * 0 	space 	0 			skip white space 
		 * 0 	letter 	1 			begin mnemonic 
		 * 1 	letter 	1 		
		 * 1 	number 	2 			end of mnemonic (e.g. LWC1) 
		 * 1 	'.' 	3 	
		 * 2 	space 	(4) accept accept string 
		 * 3 	D 		2		 	(double) ADD.D 
		 * 3 	S 		2 			(single) ADD.S
		 * 
		 * Example parsing ADD.D: 
		 * index	state 	ch => next state buff 
		 * 0 	 	0 		'A' 	1 	'A' 
		 * 1 	 	1 		'D' 	1 	'AD' 
		 * 2 		1 		'D' 	1 	'ADD' 
		 * 3 		1 		'.' 	3 	'ADD.' 
		 * 4 		3 		'D' 	2 	'ADD.D'
		 * 5 		2 		' ' 	4(+) 'ADD.D'
		 */

		int index = 0;
		int state = 0;
		while ((state != 4) && (state != -1)) {
			char ch = instr.charAt(index++);
			switch (state) {
			case 0:

				if (Character.isWhitespace(ch))
					state = 0;
				else if (Character.isLetter(ch))
					state = 1;
				else
					state = -1;
				break;
			case 1:
				if (Character.isLetter(ch))
					state = 1;
				else if (Character.isDigit(ch))
					state = 2;
				else if (ch == '.')
					state = 3;
				else
					state = -1;
				break;
			case 2:
				if (Character.isWhitespace(ch))
					state = 4;
				else
					state = -1;
				break;
			case 3:
				if ((ch == 'D') || (ch == 'S'))
					state = 2;
				else
					state = -1;
				break;
			}

			if ((state == 1) || (state == 2) || (state == 3))
				buff.append(ch);
		}

		return buff.toString();
	}

	public static String[] parseRegFields(String instr) {
		return parseRegFieldString(instr).split(",");
	}

	protected static String parseRegFieldString(String instr) {
		StringBuffer buff = new StringBuffer();

		/*
		 * DFA to parse the fields (if any) ADD.D $f0,$f2,$f4 
		 * state	read	next state 	note 
		 * 0 		space 	0 			skip white space 
		 * 0 		letter 	1 			begin mnemonic, no copy 
		 * 1 		letter 	1 			no copy 
		 * 1 		number 	2 			end of mnemonic (e.g. LWC1) 
		 * 1 		'.'		 3 			no copy 
		 * 2 		space 	4 			no copy, end of mnemonic 
		 * 3 		D 		2 			ADD.D 
		 * 3 		S 		2 			ADD.S
		 * 4 		space 	4 			more white space, no copy 
		 * 4 		$ 		5 			start of regs 
		 * 4 		0 		6 			start of numeric literal 
		 * 5 		; 		-1 			comment, end line 
		 * 5 		# 		-1 			comment, end line
		 * 6 		x 		7 			found 0x 
		 * 7 		digit 	7 	
		 * 7 		a-f 	7
		 * 
		 * 
		 * Example parsing ADD.D $f0,$f2,$f4: 
		 * index state 	ch => next state buff
		 * 0 	0 		'A' 	1 	
		 * 1 	1 		'D' 	1 
		 * 2 	1 		'D' 	1 
		 * 3 	1 		'.' 	3 
		 * 4 	3 		'D' 	2 
		 * 5 	2 		' ' 	4 
		 * 6 	4 		' ' 	4
		 * 7 	4 		'$' 	5 		$ 
		 * 8 	5 		'f' 	5 		$f ...
		 */

		int index = 0;
		int state = 0;

		instr = instr.toUpperCase();

		while ((index < instr.length()) && (state != -1)) {
			char ch = instr.charAt(index++);
			switch (state) {
			case 0:
				if (Character.isWhitespace(ch))
					state = 0;
				else if (Character.isLetter(ch))
					state = 1;
				else
					state = -1;
				break;
			case 1:
				if (Character.isLetter(ch))
					state = 1;
				else if (Character.isDigit(ch))
					state = 2;
				else if (Character.isWhitespace(ch))
					state = 4;
				else if (ch == '.')
					state = 3;
				else
					state = -1;
				break;
			case 2:
				if (Character.isWhitespace(ch))
					state = 4;
				else
					state = -1;
				break;
			case 3:
				if ((ch == 'D') || (ch == 'S'))
					state = 2;
				else
					state = -1;
				break;
			case 4:
				if (Character.isWhitespace(ch))
					state = 4;
				if (ch == '$')
					state = 5;
				if (ch == '0')
					state = 6;
				break;
			case 5:
				if (ch == ';')
					state = 100;
				if (ch == '#')
					state = 100;
				if (Character.isWhitespace(ch))
					state = 100;
				break;
			case 6:
				if (ch == 'X')
					state = 7;
				else
					state = -1;
				break;
			case 7:
				if (Character.isDigit(ch))
					state = 7;
				else if ("ABCDEF".indexOf(ch) >= 0)
					state = 7;
				else if (Character.isWhitespace(ch))
					state = 100;
				else if (ch == ';')
					state = 100;
				else if (ch == '#')
					state = 100;
				else
					state = -1;
				break;
			}

			if ((state >= 5) && (state < 100))
				buff.append(ch);
		}

		if (state > 0)
			return buff.toString();
		else
			return null;
	}
	
	public char getType( )
	{
		return '-';
	}
	
	
	public boolean isItype( )
	{
		return false;
	}
	
	public boolean isRtype( )
	{
		return false;
	}
	
	public boolean isJtype( )
	{
		return false;
	}
	
	public boolean isFtype( )
	{
		return false;
	}
	
	public int getOp( )
	{
		return opcode;
	}
	
	public int toInt( )
	{
		throw new RuntimeException("This method is undefined for generic Instruction.");
		
	}
	
	public String getMnemonic( )
	{
		try {
			return getMnemonic(this.getOp(), 0);
		} catch (InstructionFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
