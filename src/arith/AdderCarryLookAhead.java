/*
/*
 * FILE:    AdderCarryLookAhead.java
 * PACKAGE: arith
 * PROJECT:	CSC110
 * AUTHOR:  Tom Briggs (c) 2004
 * DATE:    Jun 17, 2004
 */ 
package arith;

/**
 * This implements a 32-bit adder with carry-lookahead.
 * @author tbriggs
 */
public class AdderCarryLookAhead {
	
	private int cycles;	// number of "cycles" used
	BitString32 A,B;		// two summands.  Set and used internally.
	
	/**
	 * Construct a new adder
	 *
	 */
	public AdderCarryLookAhead( ) 
	{
		cycles = 0;
		A = null;
		B = null;
	}
	
	/**
	 * Return the number of "cycles" 
	 * @return number of cycles
	 */
	public int getCycles( )
	{
		return cycles;
	}
	
	/**
	 * Adds two numbers together using the add-with-carry technique
	 * @param a summand A
	 * @param b summand B
	 * @return sum of A and B
	 * @throws OverflowException when A+B requires > 32 bits
	 */
	public int add(int a, int b) throws OverflowException
	{
		BitString32 A = new BitString32(a);
		BitString32 B = new BitString32(b);
		BitString32 C = add(A,B);
		
		return C.getValue();
	}
	
	
	/**
	 * Implement a 4-bit ALU with carry-look ahead propgation.
	 * @param A Input operand A
	 * @param B Input operand B
	 * @return Result
	 */
	public BitString32 add(BitString32 A, BitString32 B) throws OverflowException
	{
		BitString32 result = new BitString32(0);
		this.A = A;
		this.B = B;
		
		
		// iterate over the bit-string, process 4-bits at a time
		int carryIn = 0;
		for (int i = 0; i < 32; i+=4) {
			
			int c1 = G(i) + (P(i) * carryIn);
			int c2 = G(i+1) + 
						(P(i+1)*G(i)) + (P(i+1)*P(i)*carryIn);
			int c3 = G(i+2)+(P(i+2)*G(i+1)) +
						(P(i+2)*P(i+1)*G(i)) +
						(P(i+2)*P(i+1)*P(i)*carryIn);
			int c4 = G(i+3) + (P(i+3)*G(i+2)) + 
						(P(i+3) * P(i+2) * G(i+1)) +
						(P(i+3) * P(i+2) * P(i+1) * G(i)) +
						(P(i+3) * P(i+2) * P(i+1) * P(i+0) * carryIn);
			
			result.setBit(i,addBit(A.getBit(i),B.getBit(i),carryIn));
			result.setBit(i+1,addBit(A.getBit(i+1),B.getBit(i+1),c1));
			result.setBit(i+2,addBit(A.getBit(i+2),B.getBit(i+2),c2));
			result.setBit(i+3,addBit(A.getBit(i+3),B.getBit(i+3),c3));
			
			carryIn = c4;
			
			cycles++;
			
		}

		
		if (carryIn == 1) 
			throw new OverflowException("overflow");
		
		return result;
	}
	
	
	/**
	 * A one-bit adder...
	 * @param a
	 * @param b
	 * @param c
	 * @return a + b + c ( no carry )
	 */
	private int addBit(int a, int b, int c)
	{
		int result = a + b + c;
		if (result > 1) result = result-2;
		return result;
	}
	
	
	/**
	 * G() helper function
	 * @param i
	 * @return if A * B are set.
	 */
	private int G(int  i)
	{
		int ai,bi;
		
		ai = 0;
		bi = 0;
		
		if (A.isSet(i)) ai = 1;
		if (B.isSet(i)) bi = 1;
		
		return ai * bi;
	}
	 
	/**
	 * P() help function
	 * @param i
	 * @return if a or bi are set.
	 */
	private int P(int  i)
	{
		int ai,bi;
		
		ai = 0;
		bi = 0;
		
		if (A.isSet(i)) ai = 1;
		if (B.isSet(i)) bi = 1;
		
		return ai + bi;
	}
}
