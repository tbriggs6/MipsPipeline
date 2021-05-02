/*
 * Created on Nov 21, 2006
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package cache;

public class Memory extends Cache {

    final static boolean DEBUG = true;
    
    int memorySize;
    int data[];
    
    public Memory(int blockSize, int memorySize ) {
        
        super(blockSize, new NullCache());
        this.data = new int[memorySize / 4];
        this.memorySize = memorySize;
        if (DEBUG)
            for (int i = 0; i < memorySize / 4; i++)
                data[i] = i;
    }

    @Override
    public int[] accessBlock(int address) {

        int value[] = new int[blockSize >> 2];
        
        int addr = ((address >> 2) % (blockSize >> 2));
        
        for (int i = 0; i < (blockSize>>2); i++)
        {
            value[i] = data[(addr++) % (memorySize / 4)];
        }
    
        return value;
    }

}
