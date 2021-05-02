/*
 * Created on Aug 10, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package mips.pipe;

import instr.Instruction;
import instr.InstructionFormatException;

import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;

public abstract class PipeStage extends java.util.Observable implements Observer {

	String longName;
	String shortName;
	int numBusy = 0;
	int numStalled = 0;
	boolean isBusy = false;
	instr.Instruction currentInstruction = null;
	
	
	public PipeStage(String shortName,String longName)
	{
		this.shortName = shortName;
		this.longName = longName;
	}
	
	public abstract boolean handleClock(ClockPulse clock);
	public abstract void handlePoisonPill( );
	
	
	public final void update(Observable arg0, Object arg1) {
		
		if (arg1 instanceof ClockPulse)
		{
			ClockPulse C = (ClockPulse) arg1;
			handleClock(C);
			send(C);
		}
	}

	public boolean isBusy( )
	{
		return isBusy;
	}
	
	protected void send(Object arg)
	{
		super.setChanged();
		super.notifyObservers(arg);
	}
	
	public String toString( )
	{
		return getShortName();
	}

	/**
	 * @return Returns the name.
	 */
	public String getShortName() {
		return shortName;
	}

	public String getLongName() {
		return longName;
	}
	
	/**
	 * @return Returns the numBusy.
	 */
	public int getNumBusy() {
		return numBusy;
	}

	/**
	 * @return Returns the numStalled.
	 */
	public int getNumStalled() {
		return numStalled;
	}
	
	public void incrementBusy( )
	{
		numBusy++;
		this.isBusy = true;
	}
	
	public void incrementStalled( )
	{
		numStalled++;
		this.isBusy = false;
	}
	
	public String getStatsStrng( )
	{
		int totalClocks = numBusy + numStalled;
		double pcentBusy = ((double)numBusy / (double)totalClocks) ;
		
		DecimalFormat F = new DecimalFormat("#.00%");
		
		return shortName + " clk:" + totalClocks +  
			" stalls:" + numStalled + " (" + F.format(1.0 - pcentBusy) + ") " + 
			" busy:" + numBusy + " (" + F.format(pcentBusy) + ")";
		
	}

	/**
	 * @return Returns the currentInstruction.
	 */
	public instr.Instruction getCurrentInstruction() {
		return currentInstruction;
	}

	/**
	 * @param currentInstruction The currentInstruction to set.
	 */
	public void setCurrentInstruction(instr.Instruction currentInstruction) {
		this.currentInstruction = currentInstruction;
	}
	
	public void setCurrentInstruction(int word)
	{
		try {
			this.currentInstruction = Instruction.parseInstruction(word);
		}
		catch(InstructionFormatException E)
		{
			this.currentInstruction = null;
		}
	}
}
