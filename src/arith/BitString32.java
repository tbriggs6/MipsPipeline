/*
/*
 * FILE:    BitString.java
 * PACKAGE: arith
 * PROJECT:	CSC220
 * AUTHOR:  Tom Briggs (c) 2004
 * DATE:    Jun 15, 2004
 */ 
package arith;

/**
 * BitString allows convient access to a bit representation of an integer.  Constructed
 * with an integer, an array is created such that each cell of the array represents a 
 * single bit in the file.  In this way, operations such as "shift left" are easy to
 * implement in terms of array elements; and operations such as isSet and setBit are 
 * trivial.  The operation that is more expensive is the getValue( ) method that 
 * converts the bitString back to an integer.
 * @author tbriggs
 */
public class BitString32 {
	
	private static final boolean DEBUG = false;
	public static final int NUM_BITS = 32;		// hard code to 64 bit 
	private int value;
	
	
	/**
	 * Construct a BitString from the given value.  The value is a signed 
	 * integer, and is converted into its binary two's complement form.
	 * @param value 
	 */
	public BitString32(int value)
	{
		this.value = value;
	}
	
	/**
	 * Set the vale of the currennt BitString32 object.
	 * @param value
	 */
	public void setValue(int value)
	{
		this.value = value;
	}
	
	/**
	 * Set the value of the BitString32 to the value represented by the 
	 * boolean array.  The boolean array can be between 0 and 32 entries long.
	 * The value represented by the array is converted to an integer value 
	 * in the following manner:
	 *   <I>if bits[i] = true, then add 2<sup>i</sup> to a sum</I>
	 * @param bits boolean array representing the value
	 */
	public void setValue(boolean bits[])
	{
		int sum = 0;
		int exp = 1;
		for (int i = 0; i < Math.min(NUM_BITS,bits.length); i++)
		{
			if (bits[i] == true) {
				if (DEBUG)
					System.out.println("adding : i: " + i + " exp: " + exp + " sum: " + sum);
				sum = sum + exp;
			}
			exp = exp * 2;
		}
		
		if (DEBUG)
			System.out.println("setting value: " + sum);
		
		this.value = sum;
	}

	/**
	 * getValue converts a binary string representing an integer into the actual
	 * integer representation.
	 * @return the value of "bits[]" converted into an array
	 */
	public int getValue( )
	{
		return this.value;
	}

	
	/**
	 * Convert the given value into an array of bits.  The ith item corresponds
	 * to 2<sup>i</sup>.
	 * @return the bit array representing the number.
	 */
	public boolean[] getBits( )
	{
		boolean bits[] = new boolean[NUM_BITS];
		 
		for (int i = 0; i < NUM_BITS; i++) {
			bits[i] = isSet(i);
		}
		
		return bits;
	}
	
	/**
	 * Convert the current bit vector into a String
	 */
	public String toString()
	{

		StringBuffer S = new StringBuffer();
		S.append("" + getValue() + "\t");
		
		 
		for (int i = NUM_BITS; i >0; i--) {
			if ((i % 8)==0) S.append(" ");
			if ((i != NUM_BITS) && ((i % 32) == 0)) S.append("| ");
			if (isSet(i-1)) S.append('1');
			else S.append('0');
		}
		
		return S.toString();
	}
	
	/**
	 * Return true if the given bit is set, false otherwise
	 * @param i the ith bit
	 * @return true if it is set, false otherwise
	 */
	public boolean isSet(int i)
	{
		int mask = 1 << i; 	// must use L here to keep unsigned int
		
		if ((value & mask) != 0L) return true;
		else return false;
	}
	
	/**
	 * Set bit i to the boolean value v
	 * @param i the bit to set
	 * @param v the value to give it
	 */

	public void setBit(int i, boolean v)
	{
		// if the bit should be set to true, OR the 
		// the value and the mask
		if (v == true) {
			int mask = 1 << i;
			value = value | mask;
		}
		else
		{
			// if the bit should be set to false, AND the
			// value and the mask
			int mask = 1 << i;
			mask = ~ mask;
			value = value & mask;
		}
	}

	/**
	 * Set bit i to the int value v, such that 0 = false, 1 = true.
	 * @param i the bit to set
	 * @param v the value to give it
	 */

	public void setBit(int i, int v)
	{
		// if the bit should be set to true, OR the 
		// the value and the mask
		if (v == 1) {
			int mask = 1 << i;
			value = value | mask;
		}
		else
		{
			// if the bit should be set to false, AND the
			// value and the mask
			int mask = 1 << i;
			mask = ~ mask;
			value = value & mask;
		}
	}
	
	/**
	 * Get the ith bit from bits[]
	 * @param i get the value corresponding to 2<sup>i</sup>
	 * @return true if the bit is set, false otherwise
	 */
	public int getBit(int i)
	{
		if ((i < 0) || (i >= NUM_BITS))
			throw new RuntimeException("bit out of range");

		
		if (isSet(i)) return 1;
		else return 0;
	}
	
	
	/**
	 * Shift the bits left by nBits
	 * @param nBits number of bits to shift
	 */
	public void shiftLeft(int nBits)
	{
		value = (int)value << (int) nBits;
	}

	/**
	 * Shift the bits right by nBits, keeping the sign bit intact.
	 * @param nBits number of bits to shift.
	 */
	public void shiftRightSigned(int nBits)
	{
		value = (int) value >> (int) nBits;
	}

	/**
	 * Shift the bits right by nBits, using the "logical" shift.  This
	 * does not preserve the sign bit of the number.
	 * @param nBits number of bits to shift.
	 */
	public void shiftRightUnsigned(int nBits)
	{
		value = (int) value >>> (int) nBits;
	}
	
}
