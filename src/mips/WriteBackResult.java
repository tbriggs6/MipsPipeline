/*
 * Created on Aug 10, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package mips;

import instr.Instruction;
import instr.InstructionFormatException;

public class WriteBackResult {

	public static final int REGOUT = 0;
	public static final int MEMIN = 1;
	public static final int MEMOUT = 2;
	public static final int PCOUT = 3;
	public static final int NOOUT = 4;
	public static final int FREGOUT = 5;
	public static final int FMEMOUT = 6;
	public static final int FMEMIN = 7;
	 
	public static final int BYTE = 0;
	public static final int HWORD = 1;
	public static final int WORD = 2;
	
	public static final int SINGLE = 3;
	public static final int DOUBLE = 4;
	
	int targetType;
	int memType;
	int target;
	int value;
	double fvalue;
	
	Instruction instr;
	
	boolean singlePrec;
	
	public WriteBackResult(Instruction instr, int type, int target, int memType, double fvalue, boolean single) {
		// TODO Auto-generated constructor stub
		targetType = type;
		this.target = target;
		this.memType = memType;
		this.value = 0;
		this.fvalue = fvalue;
		this.instr = instr;
		this.singlePrec = single;
	}

	public WriteBackResult(Instruction instr, int type, int target, int memType, int value) {
		// TODO Auto-generated constructor stub
		targetType = type;
		this.target = target;
		this.memType = memType;
		this.value = value;
		this.instr = instr;
		this.singlePrec = true;
	}
	
	/**
	 * @return Returns the memType.
	 */
	public int getMemType() {
		return memType;
	}

	/**
	 * @param memType The memType to set.
	 */
	public void setMemType(int memType) {
		this.memType = memType;
	}

	/**
	 * @return Returns the target.
	 */
	public int getTarget() {
		return target;
	}

	/**
	 * @param target The target to set.
	 */
	public void setTarget(int target) {
		this.target = target;
	}

	/**
	 * @return Returns the targetType.
	 */
	public int getTargetType() {
		return targetType;
	}

	/**
	 * @param targetType The targetType to set.
	 */
	public void setTargetType(int targetType) {
		this.targetType = targetType;
	}

	/**
	 * @return Returns the value.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value The value to set.
	 */
	public void setValue(int value) {
		this.value = value;
	}
	

	boolean isSinglePrec( )
	{
		return this.singlePrec;
	}
	
	public void setPrecision( boolean s)
	{
		this.singlePrec = s;
	}
	
	public static WriteBackResult setRegResult(Instruction instr, int register, int value)
	{
		return  new WriteBackResult( instr, REGOUT, register, WORD, value); 
		
	}
	
	public static WriteBackResult setFRegResult(Instruction instr, boolean single, int register, double value)
	{
		return new WriteBackResult(instr, FREGOUT, register, WORD, value, single);
	}
	
	public static WriteBackResult setNoResult(Instruction instr )
	{
		return new WriteBackResult( instr, NOOUT, 0, WORD, 0);
	}
	
	public static WriteBackResult setMemResult(Instruction instr, int address, int value, boolean load, int type)
	{
		
		
		if (load)
			return new WriteBackResult(instr, MEMIN, address, type, value);
		else
			return new WriteBackResult(instr, MEMOUT, address, type, value);
	}

	public static WriteBackResult setFMemResult(Instruction instr, int address, int value, boolean load, int type)
	{
		if (load)
			return new WriteBackResult(instr, FMEMIN, address, type, value);
		else
			return new WriteBackResult(instr, FMEMOUT, address, type, value);
	}
	
	public static WriteBackResult setPCResult(Instruction instr, int address)
	{
		return new WriteBackResult(instr, PCOUT, address, WORD, address);
	}

	public boolean isMemoryOp() { return isMemOut() || isMemIn() || isFMemIn() || isFMemOut(); }
	public boolean isRegOut() { return targetType == REGOUT; } 
	public boolean isMemOut() { return targetType == MEMOUT; }
	public boolean isMemIn() { return targetType == MEMIN; }
	public boolean isPCOut() { return targetType == PCOUT; }
	public boolean isNoOut() { return targetType == NOOUT; }
	public boolean isFMemIn() { return targetType == FMEMIN; }
	public boolean isFMemOut() { return targetType == FMEMOUT; }
	public boolean isFRegOut() { return targetType == FREGOUT; }

	String memOpToString()
	{
		switch(this.memType)
		{
		case BYTE:
			return "BYTE";
		case HWORD:
			return "HWORD";
		case WORD:
			return "WORD";
		default:
			return "ERR";
		}
	}
	
	public String toString()
	{
		if(isRegOut())
		{
			String tgt = Instruction.getRegName(target);
			String tgtValue = Integer.toHexString(value);
			return "R: " + tgt + " <- " + tgtValue;
		}
		else if (isFRegOut())
		{
			String tgt;
			try {
				tgt = Instruction.getFRegName(target);
			} catch (InstructionFormatException e) {
				// TODO Auto-generated catch block
				tgt = "ERR";
			}
			
			String tgtValue = Integer.toHexString(value);
			return "FR: " + tgt + " <- " + tgtValue;
		}
		else if (isMemIn())
		{
			String tgt = Instruction.getRegName(target);
			String addr = Integer.toHexString(value);
			String mt = this.memOpToString();
			
			return "LD: " + tgt + " <- (" + mt + ")MEM[" + addr + "]";
		}
		else if (isFMemIn())
		{
			String tgt;
			try {
				tgt = Instruction.getFRegName(target);
			} catch (InstructionFormatException e) {
				// TODO Auto-generated catch block
				tgt = "ERR";
			}
			String addr = Integer.toHexString(value);
			String mt = this.memOpToString();
			
			return "LD: " + tgt + " <- (" + mt + ")MEM[" + addr + "]";
		}
		else if (isMemOut())
		{
			String src = Instruction.getRegName(target);
			String addr = Integer.toHexString(value);
			String mt = this.memOpToString();
			
			return "ST: (" + mt + ")MEM[" + addr + "] <- " + src;
		}
		else if (isFMemOut())
		{
			String src;
			try {
				src = Instruction.getFRegName(target);
			} catch (InstructionFormatException e) {
				// TODO Auto-generated catch block
				src = "ERR";
			}
			String addr = Integer.toHexString(value);
			String mt = this.memOpToString();
			
			return "ST: (" + mt + ")MEM[" + addr + "] <- " + src;
		}
		else if (isPCOut())
		{
			return "PC:  PC <- " + Integer.toHexString(value);
		}
		else if (isNoOut())
		{
			return "NOP";
		}
		else
			return "ERR - Unknown WB Result";
		
	}

	/**
	 * @return Returns the instr.
	 */
	public Instruction getInstr() {
		return instr;
	}
	
}
