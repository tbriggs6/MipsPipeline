/*
 * Created on Oct 3, 2006
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package multiplier;

import java.util.LinkedList;

public class Mutliplier {

    int multiplier;
    long multiplicand;
    int step;
    long product;

    LinkedList<MultiplierListener> listeners;
    
    public Mutliplier(int multiplicand, int multiplier)
    {
        this.multiplicand = multiplicand;
        this.multiplier = multiplier;
        this.step = 0;
        this.product = 0;
        this.listeners = new LinkedList<MultiplierListener>( );
    }
    
    
    public void step( )
    {
        MultiplierState oldState, newState;
        
        oldState = new MultiplierState(multiplier, multiplicand, product, step);
        
        if ((multiplier & 0x1) == 1)
        {
            product = product + multiplicand;
        }
        
        multiplier = multiplier >> 1;
        multiplicand = multiplicand << 1;
        step++;
        
        newState = new MultiplierState(multiplier, multiplicand, product, step);
        
        notifyListeners(oldState, newState);
    }

    public void run( )
    {
        while(multiplier != 0) 
            step( );
    }
    
    public void addListener(MultiplierListener L)
    {
        listeners.add(L);
    }
    
    public void notifyListeners(MultiplierState oldState, MultiplierState newState)
    {
        for (MultiplierListener L : listeners)
        {
           L.handleMultiplierStep(oldState, newState);
        }
    }
}
