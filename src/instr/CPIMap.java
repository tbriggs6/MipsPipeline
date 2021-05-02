/*
 * Created on Aug 10, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package instr;

import java.util.HashMap;

public class CPIMap {

	HashMap<String,Integer> cpi;
	int defaultCPI;

	String arithmetic[] = { "add", "addi", "addiu", "addu", "subu", "sub"  };
	String data[] = { "lw", "sw","lb", "sb", "lui" , "lbu", "lhu" , "sh" , "mhfi", "mflo" };
	String logical[] = { "and", "or", "nor", "andi", "ori", "sll", "srl" };
	String branch[] = { "beq", "bne", "slt", "slti", "sltiu", "sltu" };
	String jump[] = { "j", "jr", "jal" };
	String fpmove[] = { "lwc1", "ldc1", "mfc0", "mfc1",  "swc1", "sdc1" };
	String fparith[] = { "div", "divu", "add.s", "add.d", 
			"c.eq.s", "c.lt.s", "c.le.s", "c.eq.d", "c.lt.d", "c.le.d", 
			"div.s", "div.d", "mul.s", "mul.d", "sub.s", "sub.d", "mult", "multu" };
			
	
	public CPIMap( int defaultCPI)
	{
		this.defaultCPI = defaultCPI;
		cpi = new HashMap<String,Integer>( );
	}
	
	public void setCPI(String mnemonic,int cycles)
	{
		cpi.put(mnemonic.toLowerCase(),cycles);
	}
	
	public int getCycles(String mnemonic)
	{
		mnemonic = mnemonic.toLowerCase();
		
		if (cpi.containsKey(mnemonic))
			return cpi.get(mnemonic);
		else
			return defaultCPI;
	}
	
	public void setArithemtic(int cycles)
	{
		for(String i : arithmetic) setCPI(i, cycles);
	}
	
	public void setData(int cycles)
	{
		for(String i : data) setCPI(i, cycles);
	}
	
	public void setLogical(int cycles)
	{
		for(String i : logical) setCPI(i, cycles);
	}
	
	public void setBranch(int cycles)
	{
		for(String i : branch) setCPI(i, cycles);
	}
	
	public void setJump(int cycles)
	{
		for(String i : jump) setCPI(i, cycles);
	}
	
	public void setFPMove(int cycles)
	{
		for(String i : fpmove) setCPI(i, cycles);
	}
	
	public void setFPArith(int cycles)
	{
		for(String i : fparith) setCPI(i, cycles);
	}
	
}
