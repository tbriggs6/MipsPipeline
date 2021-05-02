/*
 * Created on Aug 10, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package mips.gui;

import java.util.Observable;
import java.util.Observer;
import java.io.*;

import javax.swing.JTextArea;

import mips.*;
import mips.pipe.*;

public class TextAreaObserver implements Observer {
	
	int minValue;
	JTextArea console;
	StringBuffer buff;
	
	public TextAreaObserver(JTextArea console)
	{
		this.minValue = 99;
		this.console = console;
		this.buff = new StringBuffer( );
	}
	
	private void println(String txt)
	{
		buff.append(txt + "\n");
		this.console.setText(buff.toString());
		
	}
	
	private void print(String txt)
	{
		buff.append(txt );
		this.console.setText(buff.toString());
	}
	
	public void update(Observable arg0, Object arg1) {
		
		if (arg1 instanceof ClockPulse)
			return;
		
		else if (arg0 instanceof PipeRegister)
		{
			PipeRegister reg = (PipeRegister) arg0;
			println(reg.getName() + " = " + arg1);
		}
		
		else if (arg0 instanceof PipeStage)
		{
			PipeStage P = (PipeStage) arg0;
			
			if (arg1 instanceof DebugMessage)
			{
				DebugMessage dbg = (DebugMessage) arg1;
				if (dbg.getLevel() <= minValue)
					println(P.getShortName() + " : " + dbg);
				
			}
			else
				println(P.getShortName() + " : " + arg1);
			
		}
		else
			
			if (arg1 instanceof DebugMessage)
			{
				DebugMessage dbg = (DebugMessage) arg1;
				if (dbg.getLevel() <= minValue)
					println(arg0 + " : " + dbg);
			}
			else
				println(arg0 + " : " + arg1);
		
	}
	
	
	
	
}
