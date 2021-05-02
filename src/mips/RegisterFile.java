/*
 * Created on Aug 5, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package mips;

import java.text.DecimalFormat;

import instr.Instruction;
import instr.InstructionFormatException;

public class RegisterFile {

	
	int pc;
	int regs[] = new int[32];
	float sfregs[] = new float[32];
	int hi;
	int low;
	
	public int getReg(int regNum)
	{
		return regs[regNum];
	}
	
	public void setReg(int regNum, int word)
	{
		regs[regNum] = word;
	}
	
	public int getPC( )
	{
		return pc;
	}
	
	public void setPC(int value)
	{
		pc = value;
	}
	
	public float getSFreg(int regNum)
	{
		return sfregs[regNum];
	}
	
	public double getDFreg(int regNum)
	{
		checkDoubleReg(regNum);
		
		int high = Float.floatToRawIntBits(sfregs[regNum]);
		int low = Float.floatToRawIntBits(sfregs[regNum+1]);
		
		long lword = (high << 32) | low;
		
		return Double.longBitsToDouble(lword);
	}

	/**
	 * @param regNum
	 */
	private void checkDoubleReg(int regNum) {
		if ((regNum % 2) == 1) throw new RuntimeException("Cannot access odd double regs");
	}
	
	public void setSFreg(int regNum, float value)
	{
		sfregs[regNum] = value;  
	}
	
	public void setDFreg(int regNum, double value)
	{
		checkDoubleReg(regNum);
		
		long lword = Double.doubleToLongBits(value);
		int high = (int) (lword >> 32) & 0xFFFFFFFF;
		int low = (int) (lword & 0xFFFFFFFF);
		
		sfregs[regNum] = Float.intBitsToFloat(high);
		sfregs[regNum+1] = Float.intBitsToFloat(low);
		
	}

	/**
	 * @return Returns the hi.
	 */
	public int getHi() {
		return hi;
	}

	/**
	 * @param hi The hi to set.
	 */
	public void setHi(int hi) {
		this.hi = hi;
	}

	/**
	 * @return Returns the low.
	 */
	public int getLow() {
		return low;
	}

	/**
	 * @param low The low to set.
	 */
	public void setLow(int low) {
		this.low = low;
	}

	
	public String intRegsToString( )
	{
		StringBuffer buff = new StringBuffer( );
		
		for (int i = 0; i < 32; i++)
		{
			if ((i > 0) && ((i % 8) == 0)) buff.append("\n");
			else if (i > 0) buff.append("\t");
			
			buff.append(Instruction.getRegName(i));
			buff.append(":");
			buff.append(Integer.toHexString(regs[i]));
		}
		
		return buff.toString();
	}

	public String floatRegsToString( )
	{
		StringBuffer buff = new StringBuffer( );
		
		java.text.DecimalFormat F = new DecimalFormat("0.####E0");
		
		for (int i = 0; i < 32; i++)
		{
			if ((i > 0) && ((i % 8) == 0)) buff.append("\n");
			else if (i > 0) buff.append("\t");
			
			try {
				buff.append(Instruction.getFRegName(i));
			} catch (InstructionFormatException e) {
				;;
			}
			
			buff.append(":");
			buff.append(F.format(sfregs[i]));
		}
		
		return buff.toString();
	}
	
	public String toString( )
	{
			
		return intRegsToString( ) ;
	}
}
