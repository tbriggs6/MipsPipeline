package mips.gui;
/*
 * Created on Aug 14, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
import java.applet.Applet;

import mips.gui.*;

public class executeSim extends Applet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String args[])
	{
		FiveStageDialog D = new FiveStageDialog( );
		D.setVisible(true);
	}

	public void init( )
	{
		super.init();
		
		FiveStageDialog D = new FiveStageDialog(  );
		D.setVisible(true);
	}
	
}
