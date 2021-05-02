/*
 * Created on Aug 10, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package mips.pipe;

import mips.*;

import java.util.Observable;
import java.util.Observer;
import instr.Instruction;

public class PipeForwarder implements Observer {

	FiveStagePipeline parent;
	
	
	
	public PipeForwarder(FiveStagePipeline parent) {
		this.parent = parent;
	}



	public void update(Observable arg0, Object arg1) {

		/* look for an instance of a PC-Out operation... */
		if (arg0 instanceof PipeRegister)
		{
			PipeRegister reg = (PipeRegister) arg0;
			
			
			if (reg.getName().equals("EXMEM"))
			{
				if (arg1 instanceof WriteBackResult)
				{
					WriteBackResult wb = (WriteBackResult) arg1;
					Instruction instr = wb.getInstr();
					if (wb.isPCOut())
					{
						parent.poisonUnit("IF",instr);
						parent.poisonUnit("ID",instr);
						parent.poisonUnit("EX",instr);
						parent.poisonUnit("IFID",instr);
						parent.poisonUnit("IDMEM",instr);
						parent.forwardPC(wb.getValue());
					}
				}
			}
		}
		
		
	}

	

	
	
}
