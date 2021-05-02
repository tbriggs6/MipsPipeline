/*
 * Created on Oct 3, 2006
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package multiplier;

public class MultiplierState {

    int multiplier;
    long multiplicand;
    long product;
    
    int step;

    /**
     * @return Returns the multiplicand.
     */
    public long getMultiplicand() {
        return multiplicand;
    }

    /**
     * @return Returns the multiplier.
     */
    public int getMultiplier() {
        return multiplier;
    }

    /**
     * @return Returns the product.
     */
    public long getProduct() {
        return product;
    }

    /**
     * @return Returns the step.
     */
    public int getStep() {
        return step;
    }

    public MultiplierState(int multiplier, long multiplicand, long product, int step) {
        // TODO Auto-generated constructor stub
        this.multiplier = multiplier;
        this.multiplicand = multiplicand;
        this.product = product;
        this.step = step;
    }

        
}
