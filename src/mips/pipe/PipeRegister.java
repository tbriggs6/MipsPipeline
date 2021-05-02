/*
 * Created on Aug 10, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package mips.pipe;
import java.util.Observable;

public class PipeRegister extends Observable {

	boolean latched = false;
	boolean sent = false;
	
	String name;
	Object value = null;
	
	public PipeRegister(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.value = null;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public Object getValue( )
	{
		return value; 
	}

	protected void clearLatchLock( )
	{
		this.latched = false;
	}
	
	public void setValue(Object value)
	{
		if (latched == true)
			throw new RuntimeException("ERROR in " + this.getName() + " - Clobbering unread value.");
		latched = true;
		sent = true;
		this.value = value;
		
		super.setChanged();
		super.notifyObservers(value);
	}
	
	public boolean isAvailable( )
	{
		return !latched;
	}
	
	public boolean isReady( )
	{
		return (latched && sent);
	}

	public boolean wasUsed( )
	{
		return sent;
	}
	
	public String toString()
	{
		return this.name;
	}
}
