/*
 * Created on Nov 21, 2006
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package cache;

public class DirectMapped extends Cache {

    int numEntries;         // number of cache blocks, should be power of 2
    int lineSize;           // n bits for the number of entries
    
    int tagMask;
    int indexMask;
    
    CacheEntry entries[];
    
    public DirectMapped(int numEntries, int blockSize, Cache lowerLevel) {
        super(blockSize, lowerLevel);

        this.numEntries = numEntries;
        this.entries = new CacheEntry[numEntries];
        this.lineSize = (int)(Math.log(numEntries) / Math.log(2.0));

        
        indexMask = (1 << (lineSize+blockLineSize)) -1;
        tagMask = ~ indexMask;
        
        
        // assert that the number of entries is a power of 2
        assert this.numEntries == (1 << lineSize) : "" + numEntries + " <> " + (1 << lineSize);

        // initialize the array
        for (int i = 0; i < numEntries; i++)
        {
            entries[i] = new CacheEntry(i, blockSize >> 2);
        }
    }

    @Override
    public int[] accessBlock(int byteAddress) throws CacheException {

        int tag = (byteAddress & tagMask) >> (blockLineSize + lineSize);
        int index = ((byteAddress & (indexMask << blockLineSize)) >> blockLineSize) % numEntries;
         
        CacheEntry E = entries[index];
        if (!E.isValid() || (E.getTag() != tag)) {
            super.incrementMiss();
            int replacement[] = super.lowerLevel.accessBlock(byteAddress);
            E.setData(tag,replacement);
            return replacement;
        }
        else
        {
            super.incrementHit();
            return E.getData();
        }
    }

    public String toString() 
    {
        return "Direct mapped: nentries = " + numEntries + "\n" + super.toString();
    }
    
    
}
