/*
 * Created on Aug 5, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package mips;

public class Memory {

	
	byte bytes[];
		
	public Memory(int sizeBytes)
	{
		bytes = new byte[sizeBytes];
	}
	
	public int getWord(int n)
	{
		if ((n % 4) != 0) 
		{
			throw new RuntimeException("Cannot retrieve unaligned word.");
		}
		
		int w = ((bytes[n] << 24) & 0xff000000) | 
				 ((bytes[n+1] << 16) & 0x00ff0000) | 
				 ((bytes[n+2] << 8) & 0x0000ff00) | 
				 (bytes[n+3] & 0x000000ff);
		
		return w;
	}
	
	public byte getByte(int n)
	{
		return bytes[n];
	}
	
	public void  storeWord(int n, int word)
	{
		bytes[n] = (byte) ((word >> 24) & 0xff);
		bytes[n+1] = (byte) ((word >> 16) & 0xff);
		bytes[n+2] = (byte) ((word >> 8) & 0xff);
		bytes[n+3] = (byte) (word & 0xff);
		
	}
	
	public void storeByte(int n, byte b)
	{
		bytes[n] = b;
	}
}
