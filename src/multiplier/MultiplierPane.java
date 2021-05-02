/*
 * Created on Oct 3, 2006
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package multiplier;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MultiplierPane extends JPanel {
    int borderx = 15, bordery = 8;
    
    MultiplierState state = null;
    Graphics G;
    
    public void paint(Graphics G)
    {
        super.paint(G);
        this.G = G;
        
        G.translate(borderx, bordery);
        
        G.setColor(Color.BLACK);
        
        drawMultiplicand();
        drawMultiplier( );
        drawControl( );
        drawProduct( );
        drawALU( );
    }
    
    
    private void drawMultiplicand( )
    {
        int fh = G.getFontMetrics().getHeight();

        G.drawRect(35,15,100,2 * (fh + 5));
        G.drawString("Multiplicand: ", 40,15 + fh + 5);
        
    }
    
    private void drawMultiplier( )
    {
        
        int fh = G.getFontMetrics().getHeight();
        
        G.drawRect(350,90,100,2 * (fh + 5));
        G.drawString("Multiplier: ", 355,90 + fh + 5);
    }
    
    private void drawControl( )
    {
        int fh = G.getFontMetrics().getHeight();
        
        G.drawOval(270,150,100,2 * (fh+5));
        G.drawString("Control",295,150 + fh + 5);
    }
    
    private void drawProduct( )
    {
        int fh = G.getFontMetrics().getHeight();
        
        G.drawRect(15,150,210,2 * (fh + 5));
        G.drawString("Product: ", 20,150 + fh + 5);
    }
    
    private void drawALU( )
    {
        int fh = G.getFontMetrics().getHeight();
        
        G.drawLine(20,90,60,90);
        G.drawLine(60,90,80,100);
        G.drawLine(80,100,100,90);
        G.drawLine(100,90,140,90);
        G.drawLine(140,90,120,130);
        G.drawLine(40,130,120,130);
        G.drawLine(40,130,20,90);
        
        G.drawString("ALU",70,115);
        
        
    }
}
