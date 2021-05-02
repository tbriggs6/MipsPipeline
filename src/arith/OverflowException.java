package arith;
/*
 /*
 * FILE:    OverflowException.java
 * PACKAGE: 
 * PROJECT:	CSC110
 * AUTHOR:  Tom Briggs (c) 2004
 * DATE:    Jun 14, 2004
 */
/**
 * Handle an arithmetic overflow.
 * @author tbriggs
 */
public class OverflowException extends Exception {
	
	public OverflowException(String E)
	{
		super(E);
	}
}
