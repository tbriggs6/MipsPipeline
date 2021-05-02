/*
 * Created on Nov 21, 2006
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package cache;

public class NullCache extends Cache {

    public NullCache( ) {
        super(0, null);
    }

    @Override
    public int[] accessBlock(int address) throws CacheException {
        
        throw new CacheException("Segmentation Fault : invalid memory access attempted.");
    }

}
