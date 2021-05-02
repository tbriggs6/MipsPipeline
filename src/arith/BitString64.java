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
 * @see BitString32
 */

public class BitString64 {
	
	private static final boolean DEBUG = false;
	public static final int NUM_BITS = 64;		// hard code to 64 bit 
	private long value;
	
	
	/**
	 * Construct a BitString from the given value.  The value is a signed 
	 * integer, and is converted into its binary two's complement form.
	 * @param value 
	 */
	public BitString64(long value)
	{
		this.value = value;
	}
	
	/**
	 * Set the value of the given BitString64 to the given long value.
	 * @param value the value to set.
	 */
	public void setValue(long value)
	{
		this.value = value;
	}
	
	/**
	 * Set the value of the given BitString64 to the give value.  Two
	 * values, the upper 32 bit and the lower 32 bit are combined into 
	 * one 64-bit number in bit-endian order.
	 * @param upper the upper 32-bit word
	 * @param lower the lower 32-bit word
	 */
	public void setValue(int upper, int lower)
	{
		long lowerL = (long) lower;
		long upperL = upper;
		
		// shift the upper 32-bits to the left
		upperL = upperL << 32L;
		
		// or that word with the masked lower order word.
		this.value = upperL | (lower & 0x00000000FFFFFFFFL);			
	}

	/**
	 * Set the value from the given bit string.  The ith element of the
	 * bits array corresponds to the 2^i.
	 * @param bits The bits array
	 */
	public void setValue(boolean bits[])
	{
		long sum = 0;
		long exp = 1;
		for (int i = 0; i < Math.min(NUM_BITS,bits.length); i++)
		{
			if (bits[i] == true) {
				sum = sum + exp;	// should this be or??
			}
			exp = exp * 2L;
		}
		this.value = sum;
	}

	/**
	 * getValue converts a binary string representing an integer into the actual
	 * integer representation.
	 * @return the value of "bits[]" converted into an array
	 */
	public long getValue( )
	{
		return this.value;
	}

	
	/**
	 * Convert the given value into an array of bits.
	 * @return the boolean array
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
	 * @return if the ith bit is set.
	 */
	public boolean isSet(int i)
	{
		long mask = 1L << i; 	// must use L here to keep unsigned long
		
		//System.err.println(Long.toBinaryString(mask));
		if ((value & mask) != 0L) return true;
		else return false;
	}
	
	/**
	 * Set bit i to the value v
	 * @param i the bit to set
	 * @param v the value to give it
	 */

	public void setBit(int i, boolean v)
	{
		
		if (v == true) {
			long mask = 1L << i;
			value = value | mask;
		}
		else
		{
			long mask = 1L << i;
			mask = ~ mask;
			value = value & mask;
		}
	}
	
	/**
	 * Get the ith bit from bits[]
	 * @param i the ith bits.
	 * @return 1 if the bit is set, 0 if it is not.
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
		value = (long)value << (long) nBits;
	}

	/**
	 * Shift the bits right by nBits, keeping the sign bit
	 * @param nBits the number of bits to shift
	 */
	public void shiftRightSigned(int nBits)
	{
		value = (long) value >> (long) nBits;
	}

	/**
	 * Shift the bits right by nBits, clearing the sign bit
	 * @param nBits the number of bits to shift
	 */
	public void shiftRightUnsigned(int nBits)
	{
		value = (long) value >>> (long) nBits;
	}
	
	/**
	 * Return the lower 32-bit value from the 64-bit word
	 * @return the lower 32-bit word
	 */
	public int getLower( )
	{
		
		long tmp = value & 0xFFFFFFFFL;
		return (int) tmp;
	}
	
	/**
	 * Return the upper 32-bit value from the 64-bit word
	 * @return the upper 32-bit word
	 */
	public int getUpper( )
	{
		long tmp = (long) value >>> 32;
		tmp = tmp & 0xFFFFFFFFL;
		return (int) tmp;
	}
}
