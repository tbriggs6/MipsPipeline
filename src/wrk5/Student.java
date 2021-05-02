/*
 * Created on Sep 11, 2006
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package wrk5;

public class Student extends Person {

    Major major;
    float gpa;
    
    public Student( String name, int age, Major major, float gpa)
    {
        super(name, age);
        this.major = major;
        this.gpa = gpa;
    }
    
    public String toString()
    {
        return super.toString() + major + " " + gpa;
    }

    public Major getMajor() {
        return major;
    }
}
