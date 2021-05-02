/*
 * Created on Oct 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package arith;
/**
 * Implements Booth's Algorithm for mulitplication
 * @author tbriggs
 */
public class BoothAlgorithm {

	int cycles;
	
	public BoothAlgorithm( )
	{
		this.cycles = 0;
	}
	
	public int multiply(int a, int b) throws OverflowException
	{
		BitString32 A = new BitString32(a);
		BitString32 B = new BitString32(b);
		
		BitString64 P = multiply(A,B);
		
		if (P.getUpper() != 0)
			throw new OverflowException("Overflow on multiply");
			
		return P.getLower();
	}
	
	/**
	 * Use booth's algorithm to multipy the two 32-bit values (A & B)
	 * and return the 64-bit product.
	 * @param A multiplicand
	 * @param B multiplier
	 * @return product
	 */
	private BitString64 multiply (BitString32 A, BitString32 B)
	{
		BitString64 product = new BitString64(B.getValue());
		 
		int multiplicand = A.getValue();
		
		int previousBit=0;  // previous bit
		
		//BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		for (int iteration = 0; iteration < 32; iteration++) {

			int currentBit=B.getBit(iteration);  // current bit
			
			// get the upper annd lower  words from product
		 	long upper = (long)product.getUpper();
			long lower = (long)product.getLower();
			this.cycles += 1;
			
			if (previousBit == currentBit) // continue sequence
				; // do nothing
			else { 
				if (previousBit == 1)  // add to upper 
					upper = upper + multiplicand;
				else if (previousBit == 0)  // sub from upper
					upper = upper - multiplicand;
				cycles+=32;
			}
			
			
			// get the upper and lower values in product
			product.setValue((int)upper, (int)lower);
			
			
			
			// shift right
			product.shiftRightSigned(1);
			this.cycles += 1;
			
			// setup next iteration
			previousBit = currentBit;
			
		}
		
		return product;
	}
	
	public int getCycles( )
	{
		return cycles;
	}
	
	public static void main(String args[])
	{
		
		try {
			BoothAlgorithm M = new BoothAlgorithm( );
			int value = M.multiply(5,40);
			System.out.println("result: " + value);
			System.out.println("cycles: " + M.getCycles( ));
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}
}
