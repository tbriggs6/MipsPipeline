/*
 * Created on Nov 21, 2006
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package cache;

public abstract class Cache {

    int blockSize;          // keep track ofthe # of blocks transferred in/out
    int blockLineSize;      // number of bits of blockSize
    Cache lowerLevel;       // backing level of cache
    int blockMask;          // the bitmask to get the block number
    
    int miss;               // number of cache misses for this cache
    int hit;                // number of cache hits for this cache
    
    public Cache(int blockSize, Cache lowerLevel)
    {
        this.blockSize = blockSize;
        this.lowerLevel = lowerLevel;
        this.miss = 0;
        this.hit = 0;
        
        this.blockLineSize = (int)(Math.log(blockSize) / Math.log(2.0));
        this.blockMask = (1 << (blockLineSize))-1;
        
        assert this.blockSize == (1 << blockLineSize) : "" + blockSize + " <> " + (1 << blockLineSize);
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }
    
    public abstract int[] accessBlock(int address) throws CacheException;

    
    public void incrementMiss()
    {
        miss++;
    }
    
    public void incrementHit()
    {
        hit++;
    }
    
    public int access(int address) throws CacheException
    {
        int word = (address >> 2) %(blockSize>>2);
        int val = accessBlock(address)[word];
       
        return val;
    }
    
    
    public String toString()
    {
        float rate = (float) hit / (float)(hit + miss);
        return String.format("BlockSize = %d Hits: %d, Misses: %d, Hit Rate: %f",blockSize, hit,miss, rate );
    }
}
