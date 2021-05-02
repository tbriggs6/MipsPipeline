/*
/*
 * FILE:    Adder.java
 * PACKAGE: arith
 * PROJECT:	CSC110
 * AUTHOR:  Tom Briggs (c) 2004
 * DATE:    Jun 14, 2004
 */ 
package arith;

/**
 * Implement integer ALU adder
 * @author tbriggs
 */
public class Adder {
	
	public int count_adds = 0;
	
	public Adder( )
	{
		count_adds = 0;
	}
	
	/**
	 * Get cycles used
	 * @return the number of cycles used.
	 */
	public int getCycles( )
	{
		return count_adds;
	}
	
	/**
	 * Add two integer.  Converts the integers into BitString32 objects
	 * and uses the overloaded / polymorphic add(BitString32,BitString32).
	 * Also detects if the added sends an overflow.
	 * @param a any integer
	 * @param b any integer
	 * @return a+b
	 * @throws OverflowException
	 */
	public int add(int a, int b) throws OverflowException 
	{
		BitString32 A = new BitString32(a);
		BitString32 B = new BitString32(b);
		
		BitString32 C = add(A,B);
		
		return C.getValue();
	}
	
	
	/**
	 * Implement "simple" add operation
	 * @param A a BitString32 to add 
	 * @param B a BitString32 to add
	 * @return a+b
	 */
	public BitString32 add(BitString32 A, BitString32 B) throws OverflowException
	{
		int carry = 0;
		BitString32 C = new BitString32(0);
		
		for (int i = 0; i < BitString32.NUM_BITS; i++) {
			int a = 0;
			int b = 0;
			int c = 0;
			
			count_adds++;
			
			if (A.isSet(i)) a = 1;
			if (B.isSet(i)) b = 1;
			
			c = a + b + carry;
			
			if (c > 1) {
				carry = 1;
				c = c - 2;
			}
			else
				carry = 0;
			
			/* set the C bit to its proper value */
			if (c == 0) C.setBit(i,0);
			else C.setBit(i,1);
			
			/* at the bottom of this loop, carry will be 
			 * ready for the next loop */
		}
			
		if (carry > 0) {
			throw new OverflowException("overflow");
		}
		return C;
	}
	 
	public BitString64 add(BitString64 A, BitString64 B)
	{
		try {
			return add(A,B,false);
		}
		catch(Exception E)
		{
			return null;
		}
	}
	
	public BitString64 add(BitString64 A, BitString64 B, boolean checkOverflow) throws OverflowException
	{
		int carry = 0;
		BitString64 C = new BitString64(0);
		
		for (int i = 0; i < BitString64.NUM_BITS; i++) {
			int a = 0;
			int b = 0;
			int c = 0;
			
			count_adds++;
			
			if (A.isSet(i)) a = 1;
			if (B.isSet(i)) b = 1;
			
			c = a + b + carry;
			
			if (c > 1) {
				carry = 1;
				c = c - 2;
			}
			else
				carry = 0;
			
			/* set the C bit to its proper value */
			if (c == 0) C.setBit(i,false);
			else C.setBit(i,true);
			
			/* at the bottom of this loop, carry will be 
			 * ready for the next loop */
		}
			
		if (checkOverflow && (carry > 0)) {
			throw new OverflowException("overflow");
		}
		return C;
	}
	 
	
}
