/*
 * Created on Nov 21, 2006
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package cache;

public class CacheEntry {

    int index;
    boolean valid;
    int tag;
    int data[];
    int size;
    
    public CacheEntry(int index, int size)
    {
        this.index = index;
        this.size = size;
        this.data = new int[size];
        this.valid = false;
        this.tag = -1;
    }

    public int[] getData() {
        return data;
    }

    public void setData(int tag, int[] data) {
        this.data = data;
        this.tag = tag;
        this.valid = true;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public int getSize() {
        return size;
    }
    
}
