/*
 * Created on Oct 2, 2004
 * FILE: BitStringTest.java
 * AUTHOR: Tom Briggs
 * 
 */
package arith;

import junit.framework.TestCase;

/**
 * JUnit testing classes for the arith.BitString class.
 * @author tbriggs
 */
public class BitStringTest extends TestCase {

	public void testUpperLower( ) 
	{
		BitString64 B = new BitString64(0);
		
		B.setValue(1,1);
		if (B.getValue() != 4294967297L)
		{
			Long L = new Long(B.getValue());
			
			System.out.println("Value was: " + L.toString());
			fail();
		}
		
	}
	
	public void testNegativeOne( )
	{
		BitString64 B = new BitString64(-1L);
		
		System.out.println("-1 my way is:  ");
		for (int i = 0; i < 64; i++) {
			System.out.print(B.getBit(i));
		
			if (B.getBit(i) != 1) {
				fail("\nbit i: " + i + " was not set");
			}
		}	
		System.out.println("\n");
		System.out.println("-1 as string is: " + B.toString());
	}
	
	public void testBitsNegOne( )
	{
		BitString64 B = new BitString64(-1L);
		boolean bits[] = B.getBits();
		for (int i = 0; i < BitString64.NUM_BITS; i++)
			if (((bits[i] == false) && (B.getBit(i) == 1)) ||
			    (bits[i] == true) && (B.getBit(i) == 0))
				fail("\nbit " + i + " did not match");
	}
	
	
	public void testSetValueBits( )
	{
		boolean bits[] = new boolean[64];
		for (int i = 0; i < 64; i++)
			bits[i] = false;
		
		bits[12] = true;
		bits[24] = true;
		bits[36] = true;
		bits[48] = true;
		
		BitString64 B = new BitString64( 0 );
		B.setValue(bits);
		
		if (B.getValue() != 281543712968704L)
		{
			System.out.println("Value failed: " + B.getValue());
			fail();
		}
	}

	public void testToBits( )
	{
		boolean bits[] = new boolean[64];
		for (int i = 0; i < 64; i++)
			bits[i] = false;
		
		bits[12] = true;
		bits[24] = true;
		bits[36] = true;
		bits[48] = true;
		
		BitString64 B = new BitString64( 0 );
		B.setValue(bits);
	
		boolean bits2[] = B.getBits();
		for (int i = 0; i < 64; i++)
		{
			if (bits[i] != bits2[i]) {
				System.err.println("Bit " + i + " didn't match.");
				fail();
			}
		}
		
	}
	
	public void testToString( )
	{
		BitString64 B = new BitString64( 11320339233L);
		System.out.println(B.toString());
	}
	
	
	public void testIsSet( )
	{
		boolean bits[] = new boolean[64];
		for (int i = 0; i < 64; i++)
			bits[i] = false;
		
		bits[12] = true;
		bits[21] = true;
		bits[36] = true;
		bits[47] = true;
		
		BitString64 B = new BitString64( 0 );
		B.setValue(bits);
		
		for (int i = 0; i < 64; i++)
		{
			if ((i == 12) || (i == 21) || (i == 36) || (i == 47)) 
			{
				if (!B.isSet(i)) { 
					System.err.println("bit " + i + " should be set.");
					fail();
				}
			}
			else 
			{
				if (B.isSet(i)) {
					System.err.println("bit " + i + " should not be set.");
					fail();
				}
			}
		}
	
	}
	
	public void testSetBit1( )
	{
		BitString64 B = new BitString64( 0 );
		B.setBit(47,true);
		B.setBit(12,true);
		B.setBit(21,true);
		B.setBit(36,true);
		
		for (int i = 0; i < 64; i++)
		{
			if ((i == 12) || (i == 21) || (i == 36) || (i == 47)) 
			{
				if (!B.isSet(i)) { 
					System.err.println("bit " + i + " should be set.");
					fail();
				}
			}
			else 
			{
				if (B.isSet(i)) {
					System.err.println("bit " + i + " should not be set.");
					fail();
				}
			}
		}
	
	}

	public void testSetBit2( )
	{
		BitString64 B = new BitString64( 0 );
		B.setBit(47,true);
		B.setBit(12,true);
		B.setBit(21,true);
		B.setBit(36,true);
		B.setBit(35,false);
		B.setBit(34,false);
		B.setBit(34,true);
		B.setBit(34,false);
		
		for (int i = 0; i < 64; i++)
		{
			if ((i == 12) || (i == 21) || (i == 36) || (i == 47)) 
			{
				if (!B.isSet(i)) { 
					System.err.println("bit " + i + " should be set.");
					fail();
				}
			}
			else 
			{
				if (B.isSet(i)) {
					System.err.println("bit " + i + " should not be set.");
					fail();
				}
			}
		}
	
	}
	
	
	public void testShiftLeft( )
	{
		BitString64 B = new BitString64( 0 );
		B.setBit(47,true);
		B.setBit(12,true);
		B.setBit(21,true);
		B.setBit(36,true);
		
		B.shiftLeft(1);
		for (int i = 0; i < 64; i++)
		{
			if ((i == 13) || (i == 22) || (i == 37) || (i == 48)) 
			{
				if (!B.isSet(i)) { 
					System.err.println("bit " + i + " should be set.");
					fail();
				}
			}
			else 
			{
				if (B.isSet(i)) {
					System.err.println("bit " + i + " should not be set.");
					fail();
				}
			}
		}
	
	}
	
	public void testGetBit( )
	{
		
			BitString64 B = new BitString64( 0 );
			B.setBit(47,true);
			B.setBit(12,true);
			B.setBit(21,true);
			B.setBit(36,true);
			B.setBit(35,false);
			B.setBit(34,false);
			B.setBit(34,true);
			B.setBit(34,false);
		
			if (B.getBit(34) != 0) fail();
			if (B.getBit(36) != 1) fail();
			
	}
	
	
	public void testShiftRightSigned( )
	{
		BitString64 B = new BitString64( 0 );
		B.setBit(47,true);
		B.setBit(12,true);
		B.setBit(21,true);
		B.setBit(36,true);
		B.setBit(63, true);
		
		if (!B.isSet(63)) fail("\nbit 63 is not set (and should be)");
		
		B.shiftRightSigned(2);
		for (int i = 0; i < 64; i++)
		{
			if ((i == 10) || (i == 19) || (i == 34) || (i == 45) ||
				(i == 61) || (i == 62) || (i == 63))
					
			{
				if (!B.isSet(i)) { 
					System.err.println("bit " + i + " should be set.");
					fail();
				}
			}
			else 
			{
				if (B.isSet(i)) {
					System.err.println("bit " + i + " should not be set.");
					fail();
				}
			}
		}
	
	}

	public void testShiftRight( )
	{
		BitString64 B = new BitString64( 0 );
		B.setBit(47,true);
		B.setBit(12,true);
		B.setBit(21,true);
		B.setBit(36,true);
		B.setBit(63, true);
		
		if (!B.isSet(63)) fail("\nbit 63 is not set (and should be)");
		
		B.shiftRightUnsigned(2);
		for (int i = 0; i < 64; i++)
		{
			if ((i == 10) || (i == 19) || (i == 34) || (i == 45) ||
				(i == 61))
					
			{
				if (!B.isSet(i)) { 
					System.err.println("bit " + i + " should be set.");
					fail();
				}
			}
			else 
			{
				if (B.isSet(i)) {
					System.err.println("bit " + i + " should not be set.");
					fail();
				}
			}
		}
	
	}
	
	public void testLower( )
	{
		BitString64 B = new BitString64( 0 );
		B.setBit(47,true);
		B.setBit(12,true);
		B.setBit(21,true);
		B.setBit(36,true);
		B.setBit(63, true);
		
		int lower = B.getLower();
		
		if (lower != 2101248) fail();
	}
	
	public void testUpper( )
	{
		BitString64 B = new BitString64( 0 );
		B.setBit(47,true);
		B.setBit(12,true);
		B.setBit(21,true);
		B.setBit(36,true);
		B.setBit(62, true);
		
		int upper = B.getUpper();
		
		if (upper != 1073774608) fail("" + upper);
	}
	
	public void testOne( )
	{
		BitString64 B = new BitString64( 1 );
		B.setValue(-1,1);
		B.shiftRightSigned(1);
		B.setValue(0,B.getLower());
		for (int i = 0; i < 31; i++)
			B.shiftRightSigned(1);
		
		if (!B.isSet(0)) fail("\nBit zero is wrong: " + B);
		for (int i = 1; i < BitString64.NUM_BITS; i++)
			if (B.isSet(i)) fail("\nBit " + i + " is wrong: " + B);
	}
}
