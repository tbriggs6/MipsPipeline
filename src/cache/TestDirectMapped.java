/*
 * Created on Nov 21, 2006
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package cache;

import junit.framework.TestCase;

public class TestDirectMapped extends TestCase {

    public final static int MEMSIZE = 100000;
    
    public void test_DirectMapped( ) throws CacheException
    {
        
        
        for (int i = 3; i < 22; i++)
        {
            for (int j = 4; j < 128; j+=j)
            {
                int nentry = 1 << i;
                int blocksize = j;
                
                AllMemoryOK M = new AllMemoryOK(blocksize, MEMSIZE);
                DirectMapped L1 = new DirectMapped(nentry, blocksize, M);
                
                driveCache(L1, 10000);
                System.out.println("\n" + L1 + "\n");
            }
        }
        
//        Memory M = new Memory(64, MEMSIZE);
//        DirectMapped L1 = new DirectMapped(128,64,M);
//        System.out.println(L1.access(0));
//        System.out.println(L1.access(16));
//        System.out.println(L1.access(20));
//        System.out.println(L1.access(104));
//        
//        System.out.println(L1);
        
    }
    
    
    
    private void driveCache(Cache C, int reps) throws CacheException
    {
        int address;
        
        address = (int) (Math.random()* MEMSIZE);
        
        for (int i =0; i < reps; i++)
        {
            
            int distance = (int) ((2 * Math.log(Math.random() * MEMSIZE)) -
                                Math.log(MEMSIZE));
            
            address += distance;
            address = address % MEMSIZE;
            
            C.access(address);
        }
    }
}
