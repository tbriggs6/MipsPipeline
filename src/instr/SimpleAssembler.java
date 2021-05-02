/*
 * Created on Aug 5, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package instr;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;

public class SimpleAssembler {
	
	
	
	public static Instruction[] assemble(String source ) throws InstructionFormatException
	{
		String instrStr[] = source.split("\n");
		Instruction instr[] = new Instruction[instrStr.length];
		
		for (int i = 0; i < instr.length; i++)
		{
			instr[i] = Instruction.parseInstruction(instrStr[i]);
		}
		
		return instr;
	}
	
	public static Instruction[] assembleURL(String urlName)
	throws InstructionFormatException, IOException
	{
		if (!(urlName.startsWith("http:")))
			return assembleFile(urlName);
		
		URL url = new URL(urlName);
		URLConnection conn = url.openConnection();
		
		BufferedReader in = new BufferedReader( new InputStreamReader( conn.getInputStream() ));
		
		return loadFromStream(in);
	}
	
	public static Instruction[] assembleFile(String fileName)
		throws InstructionFormatException, IOException
	{
		
		BufferedReader in = new BufferedReader( new FileReader( fileName ));
		
		return loadFromStream(in);
	}
	
	/**
	 * @param in
	 * @return
	 * @throws IOException
	 * @throws InstructionFormatException
	 */
	private static Instruction[] loadFromStream(BufferedReader in) throws IOException, InstructionFormatException {
		LinkedList<Instruction> instr = new LinkedList<Instruction>( );
		String s;
                int line = 1;
		while ((s = in.readLine()) != null)
		{
	           try {
			instr.add( Instruction.parseInstruction(s));
			line++;
		   } catch(Throwable E) {
            	       System.err.println("Error on line " + line + " " + E);
		       throw E;
		   } 
		}
		
		Instruction tmp[] = new Instruction[ instr.size()];
		for (int i = 0; i < instr.size() ; i++)
			tmp[i] = instr.get(i);
		
		return tmp;
	}
	
}
