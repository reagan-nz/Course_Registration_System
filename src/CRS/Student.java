package CRS;
import java.io.Serializable;
import java.util.ArrayList;

//The Student class represents a student user in the system.
// - It extends User (so it inherits username, password, firstName, lastName).
// - It implements StudentAction (so it must define all methods from the interface).
// - It is Serializable, meaning Student objects can be saved to a file.

public class Student extends User implements StudentAction, Serializable {
	
	//Serialization
    private static final long serialVersionUID = 1L;


    // Constructor: creates a new Student object by calling the User constructor
    public Student(String un, String pw, String fn, String ln) {
        super(un, pw, fn, ln);
    }
    
 // Getter method for username
    public String getUsername() {
    	return username;
    }

    //View all courses
    @Override
    public void viewAllCourses(ArrayList<Course> allCourses) {
        System.out.println("\n--- All University Courses ---");
        for (Course course : allCourses) {
            System.out.println(course.toString());
            System.out.println("--------------------");
        }
    }

    //View all courses that are not full
    @Override
    public void viewAvailableCourses(ArrayList<Course> allCourses) {
        System.out.println("\n--- Available Courses (Not Full) ---");
        boolean found = false;
        for (Course course : allCourses) {
            if (course.getCur_num() < course.getMax_stu()) {
                System.out.println(course.toString());
                System.out.println("--------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No available courses found at this time.");
        }
    }

    //Register for a course
    @Override
    public void registerForCourse(ArrayList<Course> allCourses, String courseId, String sectionNum) {
        String studentFullName = this.firstName + " " + this.lastName;
        Course targetCourse = null;
        
        for (int i = 0; i < allCourses.size(); i++) {
            Course c = allCourses.get(i);
            if (c.getCourse_id().equalsIgnoreCase(courseId) &&
                c.getSection_num().equalsIgnoreCase(sectionNum)) {
            	targetCourse = c;     	
            }
        }
        // 1. Check if the course exists
        if (targetCourse == null) {
            System.out.println("Error: Course with ID '" + courseId + "' not found.");
            return;
        }

        // 2. Check if the course is full
        if (targetCourse.getCur_num() == targetCourse.getMax_stu()) {
            System.out.println("Error: Registration failed. The course '" + targetCourse.getCourse_name() + "' is full.");
            return;
        }

        // 3. Check if the student is already registered
        for (String registeredName : targetCourse.getStudent_list()) {
            if (registeredName != null && registeredName.equalsIgnoreCase(studentFullName)) {
                System.out.println("Error: You are already registered for '" + targetCourse.getCourse_name() + "'.");
                return;
            }
        }

        // 4. Find an empty spot in the roster and register the student
        String[] roster = targetCourse.getStudent_list();
        for (int i = 0; i < roster.length; i++) {
            if (roster[i] == null) {
                roster[i] = studentFullName;
                targetCourse.setCur_num(targetCourse.getCur_num() + 1); // Increment student count
                targetCourse.setStudent_list(roster);
                System.out.println("Success! You have been registered for " + targetCourse.getCourse_name() + ".");
                return;
            }
        }
    }

    //Withdraw from a course
    @Override
    public void withdrawFromCourse(ArrayList<Course> allCourses, String courseId, String sectionNum) {
        String studentFullName = this.firstName + " " + this.lastName;
        
        Course targetCourse = null;
        
        for (int i = 0; i < allCourses.size(); i++) {
            Course c = allCourses.get(i);
            if (c.getCourse_id().equalsIgnoreCase(courseId) &&
                c.getSection_num().equalsIgnoreCase(sectionNum)) {
            	targetCourse = c;     	
            }
        }
        
        // 1. Check if the course exists
        if (targetCourse == null) {
            System.out.println("Error: Course with ID '" + courseId + "' not found.");
            return;
        }

        // 2. Find the student in the course roster and remove them
        String[] roster = targetCourse.getStudent_list();
        for (int i = 0; i < roster.length; i++) {
            if (roster[i] != null && roster[i].equalsIgnoreCase(studentFullName)) {
                roster[i] = null; // Remove the student's name
                targetCourse.setCur_num(targetCourse.getCur_num() - 1); // Decrement student count
                System.out.println("Success! You have withdrawn from " + targetCourse.getCourse_name() + ".");
                return;
            }
        }

        System.out.println("Error: Withdrawal failed. You are not registered for '" + targetCourse.getCourse_name() + "'.");
    }

    
    // Show only the courses THIS student is registered in
    @Override
    public void viewMyCourses(ArrayList<Course> allCourses) {
        System.out.println("\n--- Your Registered Courses ---");
        String studentFullName = this.firstName + " " + this.lastName;
        boolean foundCourse = false;

        for (Course course : allCourses) {
            for (String registeredName : course.getStudent_list()) {
                if (registeredName != null && registeredName.equalsIgnoreCase(studentFullName)) {
                    System.out.println(course.report()); // Use the concise report view
                    foundCourse = true;
                    break; // Move to the next course
                }
            }
        }

        if (!foundCourse) {
            System.out.println("You are not registered for any courses.");
        }
    }
    
    // Helper method: find a course by ID
    private Course findCourseById(ArrayList<Course> allCourses, String courseId) {
        for (Course course : allCourses) {
            if (course.getCourse_id().equalsIgnoreCase(courseId)) {
                return course;
            }
        }
        return null;
    }
}

