/*
 * Created on Aug 10, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package mips.pipe;
import java.util.Observable;

public class Clock extends Observable {

	int clockNum = 0;
	boolean lowEdge = true;
	
	public void handleTick( )
	{
		ClockPulse C = new ClockPulse(clockNum, lowEdge);
		
		super.setChanged( );
		super.notifyObservers(C);
		
		lowEdge = ! lowEdge;
		if (lowEdge) clockNum++;
	}

	public int getClock( )
	{
		return clockNum;
	}
	public boolean isLow( )
	{
		return lowEdge;
	}
	
	public String toString()
	{
		if (lowEdge)
			return clockNum + " low ";
		else
			return clockNum + " high ";
	}
}
