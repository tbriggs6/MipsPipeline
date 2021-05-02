/*
 * Created on Sep 11, 2006
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package wrk5;

public class Driver {

    
    public static void main(String args[])
    {
        Major majors[] = { 
                new Major("CS", "Computer Science"),
                new Major("Math", "Mathematics"),
                new Major("Math Ed", "Mathematics"),
                new Major("CS Grad", "Computer Science"),
                new Major("Olde Englishe", "English")
        };
        
        Student students[] = {
                new Student("Joe", 17, majors[0], 3.5f),
                new Student("Jane", 18, majors[1], 3.8f),
                new Student("Jack", 17, majors[2], 2.5f),
                new Student("Jill", 22, majors[3], 4.0f),
                new Student("John", 24, majors[4], 1.0f)
        };
        
        for (int i = 0; i < students.length; i++)
        {
            if (students[i].getMajor().equals("CS"))
                System.out.println(students[i]);    
        }
    }
}
