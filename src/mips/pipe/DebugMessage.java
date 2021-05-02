/*
 * Created on Aug 10, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package mips.pipe;

public class DebugMessage {

	String message;
	int level;
	
	
	/**
	 * @return Returns the level.
	 */
	public int getLevel() {
		return level;
	}


	/**
	 * @param level The level to set.
	 */
	public void setLevel(int level) {
		this.level = level;
	}


	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}


	/**
	 * @param message The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}


	public DebugMessage(int level, String message) {
		// TODO Auto-generated constructor stub
		this.level = level;
		this.message = message;
	}
	
	public String toString( )
	{
		return getMessage( );
	}
	
	
}
