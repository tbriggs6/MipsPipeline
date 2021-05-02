/*
 * Created on Aug 10, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package mips.pipe;

public class ClockPulse {

	public int clock;
	public boolean low;
	public ClockPulse(int clock, boolean low) {
		// TODO Auto-generated constructor stub
		this.clock = clock;
		this.low = low;
	}
	/**
	 * @return Returns the clock.
	 */
	public int getClock() {
		return clock;
	}
	/**
	 * @param clock The clock to set.
	 */
	public void setClock(int clock) {
		this.clock = clock;
	}
	/**
	 * @return Returns the low.
	 */
	public boolean isLow() {
		return low;
	}
	/**
	 * @param low The low to set.
	 */
	public void setLow(boolean low) {
		this.low = low;
	}
	
	public String toString( )
	{
		return "ClockPulse  time=" + clock + " edge low? " + isLow();
	}
	
	
}
