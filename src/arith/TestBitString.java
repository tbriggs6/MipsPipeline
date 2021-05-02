package arith;
import junit.framework.TestCase;

/*
 /*
 * FILE:    TestBitString.java
 * PACKAGE: 
 * PROJECT:	CSC110
 * AUTHOR:  Tom Briggs (c) 2004
 * DATE:    Jun 15, 2004
 */
/**
 * @author tbriggs
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TestBitString extends TestCase {
	
	
	public void testOne( )
	{
		BitString32 B = new BitString32(10);
		int v = B.getValue();
	
		if (v != 10) fail();
		
	}
	
	
}
