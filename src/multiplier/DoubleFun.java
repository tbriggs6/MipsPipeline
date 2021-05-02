/*
 * Created on Oct 3, 2006
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package multiplier;

import instr.Instruction;
import instr.InstructionFormatException;

public class DoubleFun {

    public static void main(String args[])
    {
        for (int i = 0; i < 40; i++)
            System.out.print("/\\");

        System.out.println("IEEE Encoding Fun: PI!");
        int ipi = Float.floatToRawIntBits((float) 0.2);
        
        int sign = (ipi >> 31) & 0x1;
        int mantissa = ((ipi >> 23) & 0xFF);
        int base = ipi & 0x7FFFFF;
        
        System.out.println("Sign: " + Integer.toBinaryString(sign));
        System.out.println("Exponent: " + Integer.toBinaryString(mantissa));
        System.out.println("Fraction: " + Integer.toBinaryString(base));
        
        System.out.format("-1^%d *(", sign);
        System.out.format("2^%d . ", mantissa-127);
        for (int i =0; i < 23; i++)
        {
            if ((base >> (23-i) & 0x1) == 0) continue;
            
            if ((mantissa-127-i) >= 0) 
                System.out.format("+ 2^%d", mantissa - i - 127);
            else
                System.out.format("+  1/%d ", Math.abs(mantissa - i - 127));
        }
        System.out.println(")\n");
        
        for (int i = 0; i < 40; i++)
            System.out.print("/\\");
        
        
        System.out.println("Two's Complement Fun!: ");
        System.out.println("Consider PI:");
        System.out.println(Math.PI);
        System.out.println("IEEE 754 Bits:" + Long.toBinaryString(Double.doubleToRawLongBits(Math.PI)));
        System.out.println("Long 2's Complement: " + Double.doubleToRawLongBits(Math.PI));
        System.out.println("Int 2's Complement: " + (int)(Double.doubleToRawLongBits(Math.PI)));
        
        long ll = Double.doubleToRawLongBits(Math.PI);
        int low = (int)(ll & 0xFFFFFFFF);
        int hi = (int)((ll >> 32) & 0xFFFFFFFF);
        try {
            System.out.println("MIPS 32 Instruction, low 32=" + Instruction.parseInstruction((low)));
            System.out.println("                     hi 32 =" + Instruction.parseInstruction((hi)));
        } catch (InstructionFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
