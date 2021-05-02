/*
 * Created on Aug 10, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package mips.pipe;

import java.util.Observable;
import java.util.Observer;
import java.io.*;

public class PipeObserver implements Observer {
	
	int minValue;
	PrintStream out = null;
	
	public PipeObserver(int minValue)
	{
		this.minValue = minValue;
		this.out = System.out;
	}
	
	public PipeObserver( )
	{
		this.minValue = 5;
		this.out = System.out;
	}
	
	public void setOutput(PrintStream out)
	{
		this.out = out;
	}
	
	public void update(Observable arg0, Object arg1) {
		
		if (arg1 instanceof ClockPulse)
			return;
		
		else if (arg0 instanceof PipeRegister)
		{
			PipeRegister reg = (PipeRegister) arg0;
			out.println(reg.getName() + " = " + arg1);
		}
		
		else if (arg0 instanceof PipeStage)
		{
			PipeStage P = (PipeStage) arg0;
			
			if (arg1 instanceof DebugMessage)
			{
				DebugMessage dbg = (DebugMessage) arg1;
				if (dbg.getLevel() <= minValue)
					out.println(P.getShortName() + " : " + dbg);
				
			}
			else
				out.println(P.getShortName() + " : " + arg1);
			
		}
		else
			
			if (arg1 instanceof DebugMessage)
			{
				DebugMessage dbg = (DebugMessage) arg1;
				if (dbg.getLevel() <= minValue)
					out.println(arg0 + " : " + dbg);
			}
			else
				out.println(arg0 + " : " + arg1);
		
	}
	
	
	
	
}
