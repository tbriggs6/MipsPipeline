/*
 * Created on Aug 10, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package mips.pipe;

import mips.Memory;
import mips.RegisterFile;
import mips.WriteBackResult;
import instr.CPIMap;
import instr.Instruction;
import instr.InstructionFormatException;

import java.text.DecimalFormat;
import java.util.HashMap;
import instr.Instruction;
import java.io.PrintStream;

public class FiveStagePipeline {

	PipeRegister IFID, IDEX, EXMEM, MEMWB;
	Clock C ;
	InstructionFetch IF;
	InstructionDecode ID;
	Execute EX;
	MemoryStage MEM;
	WriteBack WB;
	PipeObserver ob;
	PipeForwarder FWD;
	RegisterFile regs;
	Memory memory;
	
	boolean usingForward = false;
	boolean usingWriteback = false;
	
	public FiveStagePipeline(Memory memory, RegisterFile regs )
	{
		/* create the pipe registers */
		IFID = new PipeRegister("IFID");
		IDEX = new PipeRegister("IDEX");
		EXMEM = new PipeRegister("EXMEM");
		MEMWB = new PipeRegister("MEMWB");
		
		this.regs = regs;
		this.memory = memory;
		
		
		/* create the clock */
		C = new Clock( );
		
		/* create the PC forwarder */
		FWD = new PipeForwarder(this);
		
		/* construction the functional units */
		CPIMap alucpi = new CPIMap(0);
		alucpi.setFPArith(3);
		alucpi.setFPMove(3);
		
		CPIMap memcpi = new CPIMap(1);
		
		IF = new InstructionFetch(memory, regs, IFID);
		ID = new InstructionDecode(memory, regs, IFID, IDEX);
		EX = new Execute(memory, regs,IDEX, EXMEM, alucpi);
		MEM = new MemoryStage(memory, regs, EXMEM, MEMWB, memcpi);
		WB = new WriteBack(this, memory, regs,MEMWB);

		/* connect the units in sequence using observer/observable */
		C.addObserver(IF);
		IF.addObserver(ID);
		ID.addObserver(EX);
		EX.addObserver(MEM);
		MEM.addObserver(WB);
		
		/* add the branch forwarding connection */
		EXMEM.addObserver(FWD);
		
		/* add a debugging observer */
		ob = new PipeObserver(99);
		C.addObserver(ob);
		IF.addObserver(ob);
		ID.addObserver(ob);
		EX.addObserver(ob);
		MEM.addObserver(ob);
		WB.addObserver(ob);
		
		IFID.addObserver(ob);
		IDEX.addObserver(ob);
		EXMEM.addObserver(ob);
		MEMWB.addObserver(ob);
	}

	
	public void addObserver(java.util.Observer nob )
	{
		C.addObserver(nob);
		IF.addObserver(nob);
		ID.addObserver(nob);
		EX.addObserver(nob);
		MEM.addObserver(nob);
		WB.addObserver(nob);
		
		IFID.addObserver(nob);
		IDEX.addObserver(nob);
		EXMEM.addObserver(nob);
		MEMWB.addObserver(nob);
	}
	
	public void tic( )
	{
		this.usingForward = false;
		this.usingWriteback = false;
		
		System.out.println("---------------------------------------------");
		C.handleTick();
		System.out.println("---------------------------------------------");
		
	}
	
	protected void forwardWB( )
	{
		this.usingWriteback = true;
	}
	
	protected void forwardPC(int newPC)
	{
		IF.setPC( newPC );
		this.usingForward = true;
	}
	
	protected void poisonUnit(String unitName, Instruction instr)
	{
		if (unitName.equals("ID"))
		{
			ID.handlePoisonPill();
		}
		else if (unitName.equals("IF"))
		{
			IF.handlePoisonPill();
		}
		else if (unitName.equals("EX"))
		{
			EX.handlePoisonPill();
		}
		else if (unitName.equals("MEM"))
		{
			MEM.handlePoisonPill();
		}
		else if (unitName.equals("WB"))
		{
			WB.handlePoisonPill();
		}
		else if (unitName.equals("IFID"))
		{
			IFID.setValue(new Integer(0));
		}
		else if (unitName.equals("IDEX"))
		{
			try {
				IDEX.setValue(Instruction.parseInstruction(0));
			}
			catch(InstructionFormatException E)
			{
				IDEX.setValue(null);
			}
		}
		else if (unitName.equals("EXMEM"))
		{
			
			EXMEM.setValue(WriteBackResult.setNoResult(instr));
		}
		else if (unitName.equals("MEMWB"))
		{
			MEMWB.setValue(WriteBackResult.setNoResult(instr));
		}
		
	}


	/**
	 * @return Returns the c.
	 */
	public Clock getC() {
		return C;
	}


	/**
	 * @return Returns the eX.
	 */
	public Execute getEX() {
		return EX;
	}


	/**
	 * @return Returns the eXMEM.
	 */
	public PipeRegister getEXMEM() {
		return EXMEM;
	}


	/**
	 * @return Returns the fWD.
	 */
	public PipeForwarder getFWD() {
		return FWD;
	}


	/**
	 * @return Returns the iD.
	 */
	public InstructionDecode getID() {
		return ID;
	}


	/**
	 * @return Returns the iDEX.
	 */
	public PipeRegister getIDEX() {
		return IDEX;
	}


	/**
	 * @return Returns the iF.
	 */
	public InstructionFetch getIF() {
		return IF;
	}


	/**
	 * @return Returns the iFID.
	 */
	public PipeRegister getIFID() {
		return IFID;
	}


	/**
	 * @return Returns the mEM.
	 */
	public MemoryStage getMEM() {
		return MEM;
	}


	/**
	 * @return Returns the mEMWB.
	 */
	public PipeRegister getMEMWB() {
		return MEMWB;
	}


	/**
	 * @return Returns the wB.
	 */
	public WriteBack getWB() {
		return WB;
	}
	
	
	private int totalInstr(HashMap<String, Integer> stats )
	{
		int total = 0;
		for (String s : stats.keySet())
		{
			total += stats.get(s);
		}
		return total;
	}
	
	private String getCPIstats( )
	{
		HashMap<String,Integer> stats = EX.getRetiredStats();
		
		StringBuffer buff = new StringBuffer( );
		DecimalFormat C = new DecimalFormat("###0");
		DecimalFormat D = new DecimalFormat("#.00%");
		
		int total = totalInstr(stats);
	
		for (String s : stats.keySet())
		{
			int i = stats.get(s);
			double pcnt = (double)i / (double)total;
			buff.append(s + "\t" + C.format(i) + "(" + D.format(pcnt) + ")\n");
		}
		
		buff.append("-----------------------");
		buff.append("total: " + total);
		
		return buff.toString( );
	}
	
	public String getStatsString( )
	{
		return getCPIstats( ) +   
			IF.getStatsStrng() + "\n" +
			ID.getStatsStrng() + "\n" +
			EX.getStatsStrng() + "\n" + 
			MEM.getStatsStrng() + "\n" + 
			WB.getStatsStrng() ;
	}


	/**
	 * @return Returns the memory.
	 */
	public Memory getMemory() {
		return memory;
	}


	/**
	 * @return Returns the regs.
	 */
	public RegisterFile getRegs() {
		return regs;
	}


	/**
	 * @return Returns the usingForward.
	 */
	public boolean isUsingForward() {
		return usingForward;
	}


	/**
	 * @return Returns the usingWriteback.
	 */
	public boolean isUsingWriteback() {
		return usingWriteback;
	}
	
}
