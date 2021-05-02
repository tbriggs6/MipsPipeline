/*
 * Created on Oct 3, 2006
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package multiplier;

import instr.Instruction;
import instr.InstructionFormatException;

public class Power2 {


    public static long pow2( int i )
    {
        if (i == 0) return 1;
        else return 2*pow2(i-1);
    }
    
    public static void main(String args[])
    {
        double z;
        long y;
        
        for (int i = 0; i < 40; i++)
            System.out.print("/\\");
        
        System.out.println("Selected Powers of 2");
        
        for (int i = 0; i < 40; i++)
            System.out.print("/\\");
        
        
        
        
        for (int i = 0; i < 36; i++)
        {
            z = Math.pow(2.0, -i);
            y = pow2(i);
            
            
            StringBuffer output = new StringBuffer( );
            output.append(String.format("2^%d = %d", i,y));
            
            for (int j = output.length(); j < 28; j++)
                output.append(" ");
            
            if (i == 0)
                output.append("\n");
            else
            {
                String str =String.format("2^-%d = 1/%d ~= %25.25f\n",i, y, z);
                output.append(str);
            }
            System.out.print(output.toString());
        }
        
        
        
        
        
    }
    
}
