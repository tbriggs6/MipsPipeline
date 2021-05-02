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
public class Multiplier {
	
	public int count_mul = 0;
	private BitString64 result;
	Adder add64;
	
	public Multiplier( )
	{
		count_mul = 0;
		add64 = new Adder( );
		result = null;
	}
	
	/**
	 * Get cycles used
	 * @return the number of cycles used.
	 */
	public int getCycles( )
	{
		return count_mul + add64.getCycles();
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
	public int multiply(int a, int b)  
	{
		BitString32 A = new BitString32(a);
		BitString32 B = new BitString32(b);
		
		BitString64 C = multiply(A,B);
		result = C;
		
		return C.getLower();
	}
	
	
	/**
	 * Implement "simple" add operation
	 * @param A a BitString32 to add 
	 * @param B a BitString32 to add
	 * @return a+b
	 */
	public BitString64 multiply(BitString32 A, BitString32 B) 
	{
		int carry = 0;
		BitString64 C = new BitString64(0);
		BitString64  D = new BitString64(B.getValue());
		
		
		for (int i = 0; i < BitString32.NUM_BITS; i++) {
			
			if (A.isSet(i)) {
				C = add64.add(C,D);
			}
			D.shiftLeft(1);
			this.count_mul++;
		}
		return C;
	}
	 
	
}
