/*
 * Created on Nov 21, 2006
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package cache;

public class AllMemoryOK extends Cache {

    final static boolean DEBUG = true;
    
    int memorySize;
    int data[];
    int blockSize;
    
    public AllMemoryOK(int blockSize, int memorySize ) {
        
        super(blockSize, new NullCache());
        data = new int[blockSize];
    }

    @Override
    public int[] accessBlock(int address) {
        return data;
    }

}
