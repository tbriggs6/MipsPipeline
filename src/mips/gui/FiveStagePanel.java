/*
 * Created on Aug 10, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package mips.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.JPanel;
import mips.pipe.*;
import mips.*;


public class FiveStagePanel extends JPanel {

	private static final long serialVersionUID = 6955943133662295059L;
	FontMetrics FM;
	Graphics G;
	private FiveStagePipeline pipeSim;
	
	public FiveStagePanel(FiveStagePipeline pipeSim)
	{
		this.pipeSim = pipeSim;
		if (pipeSim == null)
			throw new RuntimeException("pipe sim is null");
	}
	
	public void paint(Graphics G)
	{
		super.paint(G);
		
		this.G = G;
		this.FM = G.getFontMetrics();
		
		Rectangle GR = getBounds();
		
		G.setColor(Color.WHITE);
		G.clearRect(0,0,(int)GR.getWidth(),(int)GR.getHeight());
		
		drawIF(0,0);
		drawRegister(115,5,"IFID");

		drawID(130,0);
		
		drawRegister(280,5,"IDEX");
		
		drawEX( 295, 0);
		
		drawRegister(485,5,"EXMEM");
		
		drawMem( 500, 0);
		
		drawRegister(620,5,"MEMWB");
		
		drawWB( 635, 0);
		
		
		// ----
		G.setColor(Color.BLACK);
		
		int instry = 310;
		if (pipeSim.getIF().getCurrentInstruction() != null) {
			instr.Instruction I = pipeSim.getIF().getCurrentInstruction();
			if (I.isFtype())
				G.drawString(pipeSim.getIF().getCurrentInstruction().toString(),10,instry);
			else
				G.drawString(pipeSim.getIF().getCurrentInstruction().toString(),10,instry);
		}
			
		
		if (pipeSim.getID().getCurrentInstruction() != null)
			G.drawString(pipeSim.getID().getCurrentInstruction().toString(),130,instry);
		
		if (pipeSim.getEX().getCurrentInstruction() != null)
			G.drawString(pipeSim.getEX().getCurrentInstruction().toString(),280,instry);
		
		if (pipeSim.getMEM().getCurrentInstruction() != null)
			G.drawString(pipeSim.getMEM().getCurrentInstruction().toString(),485,instry);
		
		if (pipeSim.getWB().getCurrentInstruction() != null)
			G.drawString(pipeSim.getWB().getCurrentInstruction().toString(),620,instry);
	}
	
	private void drawIFPC(int x, int y)
	{
		G.drawRect(x,y,25,50);
		
		G.drawString("P", x+5, y+5+FM.getHeight());
		G.drawString("C", x+5, y+5+(2 * FM.getHeight()));
	}
	
	private void drawIFMEM(int x, int y)
	{
		// draw the rectangle
		G.drawRect(x,y,50,50);
		
		int h = FM.getHeight();
		
		// draw the labels
		G.drawString("Instr. ",x+10,y+5+h);
		G.drawString("Mem.",x+10,y+5+(2 * h));
	}
	
	private void drawALU(int x, int y, double scale, String label)
	{
		java.awt.Polygon P = new java.awt.Polygon( );
		
		P.addPoint(x,y);
		P.addPoint((int)(x+(50*scale)),(int)(y+(12.5*scale)));
		P.addPoint((int)(x+(50*scale)),(int)(y+(37.5*scale)));
		P.addPoint((int)(x),(int)(y+(50*scale)));
		P.addPoint((int)(x) ,(int)(y+(30*scale)));
		P.addPoint((int)(x+(5*scale)), (int)(y+(25*scale)));
		P.addPoint((int)(x),(int)(y+(20*scale)));
		
		G.drawPolygon(P);
		
		int h = FM.getHeight();
		
		G.drawString(label,x+8,y+(int)((25 * scale)+(h / 2.0))-2);
	}
	
	public void drawLine(int startX, int startY, int endX, int endY)
	{
		G.drawLine(startX,startY,endX,endY);
	}
	
	public void drawArrow(int startX, int startY, int endX, int endY)
	{
		drawLine(startX,startY,endX, endY);
		
		Polygon P = new Polygon( );
		P.addPoint(endX, endY);
		P.addPoint(endX-5,endY-3);
		P.addPoint(endX-5,endY+3);
		
		G.drawPolygon(P);
	}
	
	private void drawRegister(int x, int y, String label)
	{
		Object contents;
		if (label.equals("IFID"))
			contents = pipeSim.getIFID().getValue();
		else if (label.equals("IDIF"))
			contents = pipeSim.getIDEX().getValue();
		else if (label.equals("IDEX"))
			contents = pipeSim.getIDEX().getValue();
		else if (label.equals("EXMEM"))
			contents = pipeSim.getEXMEM().getValue();
		else if (label.equals("MEMWB"))
			contents = pipeSim.getMEMWB().getValue();
		else
			contents = null;
		
		if (contents == null)
			G.setColor(Color.DARK_GRAY);
		else
			G.setColor(Color.BLUE);
		
		G.fillRect(x,y+15,15,250);
		G.drawString(label,x, y+260+(FM.getHeight()));
		
		
		if (pipeSim == null) {
			System.err.println("pipe sim is null");
			return;
		}
		
		if (contents != null)
			G.drawString(contents.toString(),x-30,y+290);
	}
	
	private void drawIF(int x, int y)
	{
		if (pipeSim.getIF().isBusy())
			G.setColor(java.awt.Color.BLUE);
		else
			G.setColor(java.awt.Color.DARK_GRAY);
		
		drawIFPC(x+5,y+85);
		drawIFMEM(x+55,y+100);
		
		drawArrow(x+30,y+110, x+55,y+110);
		
		// draw the ALU and its connectors
		drawALU(x+60,y+10,0.8,"PC+4");
		
		G.drawString("4",x+43,y+17);
		drawArrow(x+40, y+22, x+60, y+22);
		
		drawLine(x+40,y+110, x+40,y+40);
		drawArrow(x+40,y+40, x+60, y+40);
		
		// draw the lines going to IFID
		drawArrow(x+100, y+30, x+115, y+30);
		drawArrow(x+105, y+115, x+115, y+115);
	}
	
	private void drawID(int x, int y)
	{
		if (pipeSim.getID().isBusy())
			G.setColor(java.awt.Color.GREEN);
		else
			G.setColor(java.awt.Color.DARK_GRAY);
		
		int h = FM.getHeight();
		
		// draw the reg file box
		G.drawRect(x+15,y+70,110,115);
		G.drawString("Regs",x+50,y+(130-(FM.getHeight() / 2)));
		G.drawString("RS",x+17,y+70+h);
		G.drawString("RT",x+17,y+70+(3*h));
		G.drawString("RD",x+17,y+70+(5*h));
		G.drawString("Wr. Reg",x+17,y+65+(7*h));
		
		G.drawString("Out1",x+95,y+70+(2*h));
		G.drawString("Out2",x+93,y+70+(4*h));
		
		int sex = x+75;
		int sey = y+190;
		
		G.drawOval(sex,sey,25,50);
		G.drawString("Sgn Ex",sex,sey+65);
		
		
		// draw the connector for the IFID register
		drawLine(x,y+100,x+5,y+100);
		drawLine(x+5,y+85,x+5,y+215);
		
		// draw the arrows to the registers in
		drawArrow(x+5,y+70+h,x+15,y+70+h);
		drawArrow(x+5,y+70+(3*h),x+15,y+70+(3*h));
		drawArrow(x+5,y+70+(5*h),x+15,y+70+(5*h));
		
		// draw the arrows to the sign extend
		drawArrow(x+5,sey+25,x+75,sey+25);
		drawArrow(sex+25,sey+25,x+150,sey+25);
		
		// draw the slashes on the sign extend
		drawLine(x+40,sey+20,x+50,sey+30);
		G.drawString("16",x+40,sey+45);
		
		drawLine(x+120,sey+20,x+130,sey+30);
		G.drawString("32",x+120,sey+45);
		
		// draw the out regs
		drawArrow(x+125,y+80+(2*h),x+150,y+80+(2*h));
		drawArrow(x+125,y+80+(4*h),x+150,y+80+(4*h));
		
		// draw the PC bypass line
		drawArrow(x, y+30, x+150, y+30);
		G.drawString("PC",x+50,y+40-h);
	}

	
	private void drawEX(int x, int y)
	{
		if (pipeSim.getEX().isBusy())
			G.setColor(java.awt.Color.RED);
		else
			G.setColor(java.awt.Color.DARK_GRAY);
		
		// draw the ALUs and its connectors
		drawALU(x+60,y+20,0.8,"addr");
		drawALU(x+100,y+100,1.3,"ALU");
		
		int h = FM.getHeight();
		
		// draw the register lines
		G.drawLine(x+25,y+80,x+25,y+200);
		G.drawLine(x,y+200,x+25,y+200);
		
		// draw arrow from IDEX to ALU
		drawArrow(x,y+80+(2*h),x+100,y+80+(2*h));
		
		// draw the ALU mux
		G.drawOval(x+40,y+140,30,50);
		G.drawString("M",x+50,y+155);
		G.drawString("u",x+50,y+165);
		G.drawString("x",x+50,y+175);

		// draw arrow from IDEX to ALU mux
		drawArrow(x,y+80+(4*h),x+45,y+80+(4*h));
		
		// draw arrow from Sign Extended to ALU mux
		drawArrow(x+25,y+170,x+40,y+170);
		
		// draw arrow from ALU mux to ALU
		drawArrow(x+70,y+160,x+100,y+160);
		
		// draw the shift-left-2
		G.drawOval(x+15,y+40,20,40);
		G.drawString("<<2",x+36,y+80);
		
		// draw SE to PC ALU
		drawArrow(x+35,y+55,x+60,y+55);
		
		// draw the PC bypass line
		drawArrow(x, y+30, x+60, y+30);
		G.drawString("PC", x+20,y+40-h);
		
		// draw the PC forwarder
		if (this.pipeSim.isUsingForward())
			G.setColor(Color.RED);
		else
			G.setColor(Color.DARK_GRAY);
		
		drawLine(x+100,40,x+130,40);
		drawLine(x+130,40,x+130,3);
		drawLine(x-280,3,x+130,3);
		drawLine(x-280,3,x-280,85);
		
		// draw the result from the ALU to EXMEM
		drawArrow(x+165,y+135,x+190,y+135);
		
		// draw the forward from IDEX to EXMEM
		drawLine(x+5,y+80+(4*h),x+5,y+230);
		drawArrow(x+5,y+230,x+190,y+230);
	}

	private void drawMem(int x, int y)
	{
		if (pipeSim.getMEM().isBusy())
			G.setColor(Color.MAGENTA);
		else
			G.setColor(java.awt.Color.DARK_GRAY);
		
		// draw data memory
		int h = FM.getHeight();
		G.drawRect(x+15,y+100,80,150);
		G.drawString("Data",x+35,y+150);
		G.drawString("Mem",x+35,y+150+h);
		
		// draw the result from EXMEM to data memory
		drawArrow(x,y+135,x+15,y+135);
		G.drawString("addr",x+17,y+120);
	
		drawArrow(x,y+230,x+15,y+230);
		G.drawString("write", x+17,y+215);
		G.drawString("data", x+17,y+225);
		
		// draw the bypass from EXMEM to MEMWB
		drawLine(x+5,y+135,x+5,y+80);
		drawArrow(x+5,y+80,x+120,y+80);
		G.drawString("Reg Result",x+30,y+75);
				
		// draw the read data line from MEM
		drawArrow(x+95,y+135,x+120,y+135);
		G.drawString("read",x+68,y+130);
		G.drawString("data",x+68,y+140);
	}
	
		
	private void drawWB(int x, int y)
	{
		if (pipeSim.getWB().isBusy())
			G.setColor(Color.GREEN);
		else
			G.setColor(java.awt.Color.DARK_GRAY);
		
		// draw WB mux selector
		G.drawOval(x+15,y+70,30,80);
		drawArrow(x,y+80,x+15,y+80);
		drawArrow(x,y+135,x+15,y+135);
		
		// draw label
		G.drawString("M",x+22, y+90);
		G.drawString("U",x+22, y+110);
		G.drawString("X",x+22, y+130);
		
		// draw result wb
		if (this.pipeSim.isUsingWriteback())
			G.setColor(Color.RED);
		else 
			G.setColor(Color.DARK_GRAY);
		
		drawLine(x+45,y+110,x+60,y+110);
		drawLine(x+60,y+110,x+60,y+265);
		drawLine(x+60,y+265,x-497,y+265);
		drawLine(x-497,y+265,x-497,y+170);
		drawArrow(x-497,y+170,x-490,y+170);
		
	}
} 
