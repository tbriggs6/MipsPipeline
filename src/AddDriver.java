/*
 /*
 * FILE:    AddDriver.java
 * PACKAGE: 
 * PROJECT:	CSC110
 * AUTHOR:  Tom Briggs (c) 2004
 * DATE:    Jun 14, 2004
 */

import arith.*;
import arith.OverflowException;

/**
 * @author tbriggs
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AddDriver {
	public static void main(String[] args) {
		
		try {
			AdderCarryLookAhead A = new AdderCarryLookAhead();
			System.out.println("Results: " + A.add(32,23));
			System.out.println("Cycles: " + A.getCycles());
		}
		catch(OverflowException E) {
			E.printStackTrace();
		}
		
	}
}
