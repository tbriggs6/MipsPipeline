/*
 * Created on Sep 11, 2006
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package wrk5;

public class Major {

    String name;
    String department;
    
    public Major(String name, String department)
    {
        this.name = name;
        this.department = department;
    }
    
    public String toString()
    {
        return name + " in " + department;
    }
}
