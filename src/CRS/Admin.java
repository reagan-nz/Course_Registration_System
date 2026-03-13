package CRS;
import java.util.*;
import java.io.*;

//The Admin class represents an administrator user.
// It inherits from User and implements AdminAction interface.
// Admins can create, edit, delete courses, manage students, and generate reports.

public class Admin extends User implements AdminAction, Serializable {
	
	// Serialization
	private static final long serialVersionUID = 1L;
	
	// Constructor to create an Admin with login info and name
	public Admin(String un, String pw, String fn, String ln ){
        super(un, pw, fn, ln);
    }
	
	// Create a new course and add it to the course list
    @Override
    public boolean CreateNewCourse(String course_name, String course_id, int maxStudent, int currentStudents, String[] nameList, String courseInstructor, String sectionNum, String courseLocation){
    	ArrayList<Course> allCourse = CRSystem.getMainCourseList();

        // Check if course with same ID and section number already exists
        for (Course c : allCourse) {
            if (c.getCourse_id().equalsIgnoreCase(course_id) &&
                c.getSection_num().equalsIgnoreCase(sectionNum)) {
                System.out.println("Course with ID " + course_id + " and Section " + sectionNum + " already exists.");
                return false;
            }
        }

        // Otherwise, add new course
        Course newCourse = new Course(course_name, course_id, maxStudent, currentStudents,
                                      nameList, courseInstructor, sectionNum, courseLocation);
        allCourse.add(newCourse);
        System.out.println("Course " + course_name + " (Section " + sectionNum + ") added successfully.");
        return true;
    }
    
    // Delete a course from the course list
    @Override
    public boolean DeleteCourse(String course_id, String sectionNum){
        ArrayList<Course> allCourse = CRSystem.getMainCourseList();
        for (int i = 0; i < allCourse.size(); i++) {
            Course c = allCourse.get(i);
            if (c.getCourse_id().equalsIgnoreCase(course_id) &&
                c.getSection_num().equalsIgnoreCase(sectionNum)) {
                allCourse.remove(i); // remove by index
                System.out.println("Course with ID " + course_id + " and Section " + sectionNum + " deleted successfully.");
                return true;
            }
        }

        System.out.println("Course with ID " + course_id + " and Section " + sectionNum + " not found.");
        return false;
    }
    
    // Edit details of a course except its ID and name
    @Override
    public boolean EditCourse(String course_id, String sectionNum){
    	ArrayList<Course> allCourse = CRSystem.getMainCourseList();
    	Course currentCourse = null;
    	
    	// Find the course
    	for (int i = 0; i < allCourse.size(); i++) {
            Course c = allCourse.get(i);
            if (c.getCourse_id().equalsIgnoreCase(course_id) &&
                c.getSection_num().equalsIgnoreCase(sectionNum)) {
                currentCourse = c;
            }
        }
    	
    	// If not found, exit
        if (currentCourse == null) {
            System.out.println("Course with that name and section number not found.");
            return false;
        }
    	
        // Menu for editing
        System.out.println("What do you want to change for course " + currentCourse.getCourse_name()+ "?");
        System.out.println("1. Maximum Students.");
        System.out.println("2. Current Students.");
        System.out.println("3. Registered Student List.");
        System.out.println("4. Course Instructor.");
        System.out.println("5. Course Section Number.");
        System.out.println("6. Course Location.");
        System.out.print("Enter you choice: ");
        Scanner sc = new Scanner(System.in);
        int ans = sc.nextInt();
        
    	// Update the selected field
        if (ans == 1) {
        	System.out.print("What is the new maximum student number of the course? ");
        	currentCourse.setMax_stu(sc.nextInt());
        	String[] newlist = new String[currentCourse.getStudent_list().length];
        	for(int i = 0; i < currentCourse.getStudent_list().length;i++) {
        		newlist[i] = currentCourse.getStudent_list()[i];
        	}
        	currentCourse.setStudent_list(newlist);
        }
        else if (ans == 2) {
        	System.out.print("What is the new current student number of the course? ");
        	int newCurrStu =sc.nextInt();
        	if (newCurrStu > currentCourse.getMax_stu()) {
        		System.out.print("The entered number is greater than maximum number of this course. Try next time~");
        		return false;
        	}
        	currentCourse.setCur_num(newCurrStu);
        }
        else if (ans == 3) {
        	String[] arr = new String[currentCourse.getMax_stu()];
        	System.out.print("What is the new student list of the course? ");
        	for(int i = 0; i < arr.length; i++) {
        		System.out.print("Student "+(i+1)+":");
        		arr[i] = sc.next();
        	}
        	currentCourse.setStudent_list(arr);
        }
        else if (ans == 4) {
        	System.out.print("What is the new instructor of the course? ");
        	currentCourse.setCourse_instructor(sc.next());
        }
        else if (ans == 5) {
        	System.out.print("What is the new course section number of the course? ");
        	currentCourse.setSection_num(sc.next());
        }
        else if (ans == 6) {
        	System.out.print("What is the new course location of the course? ");
        	currentCourse.setCourse_loc(sc.next());
        }
        else {
        	System.out.print("Unacceptable input, please enter integer 1-6!");
        	return false;
        }
        System.out.print("Successfully updated the course.");
        return true;
        
    }
    
    // Display detailed info for a given course
    @Override
    public void DisplayInfo(String course_id, String sectionNum) {
        ArrayList<Course> allCourse = CRSystem.getMainCourseList();
    	for (int i = 0; i < allCourse.size(); i++) {
            Course c = allCourse.get(i);
            if (c.getCourse_id().equalsIgnoreCase(course_id) &&
                c.getSection_num().equalsIgnoreCase(sectionNum)) {
            	System.out.println(c.toString());
            }
        }
        System.out.println("Course with that name and section number not found.");
    }
    
    // Register a new student (create Student object and add to student list)
    @Override
    public boolean RegisterStudent(String fn, String ln, String un, String pw) {
    	ArrayList<Student> allStudent = CRSystem.getAllStudents();
    	
    	// Check if username already exists
        for (Student s : allStudent) { 
            if (s.getUsername().equalsIgnoreCase(un)) {
                System.out.println("A student with this username already exists.");
                return false;
            }
        }
        
        // Add student
        Student newStudent = new Student(un, pw, fn, ln);
        allStudent.add(newStudent);
        System.out.println("Student " + fn + " " + ln + " registered successfully.");
        return true;
    }
    
    // Show a report of all courses
    @Override
    public void viewAllCourse() {
    	ArrayList<Course> allCourse = CRSystem.getMainCourseList(); // FIX: Added ()
        System.out.println("--- All Courses Report ---");
        for (Course course : allCourse) { // FIX: Correct for-each loop syntax
            System.out.println(course.report());
        }
        System.out.println("--------------------------");
    }
    
    // Show only courses that are full
    @Override
	public void viewAllFull() {
    	 ArrayList<Course> allCourse = CRSystem.getMainCourseList(); // FIX: Added ()
         System.out.println("--- Full Courses ---");
         boolean found = false;
         for (Course course : allCourse) { // FIX: Correct for-each loop syntax
             if (course.getCur_num() == course.getMax_stu()) {
                 System.out.println(course.getCourse_name() + " (" + course.getCourse_id() + ")");
                 found = true;
             }
         }
         if (!found) {
             System.out.println("No courses are currently full.");
         }
         System.out.println("--------------------");
    }
    
    // Write all full courses to a text file
    @Override
    public void writeFullFile() {
    	ArrayList<Course> allCourse = CRSystem.getMainCourseList(); 
        try (FileWriter myWriter = new FileWriter("FullCourses.txt")) { 
            for (Course course : allCourse) { 
                if (course.getCur_num() == course.getMax_stu()) {
                    myWriter.write(course.getCourse_name() + "," + course.getCourse_id() + "\n");
                }
            }
            System.out.println("Successfully wrote full courses to FullCourses.txt.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
    
    
    // Show the names of students registered in a specific course
    @Override
    public void displayNameOfCourse(String course_id, String sectionNum) {
    	ArrayList<Course> allCourse = CRSystem.getMainCourseList(); 
    	for (int i = 0; i < allCourse.size(); i++) {
            Course c = allCourse.get(i);
            if (c.getCourse_id().equalsIgnoreCase(course_id) &&
                c.getSection_num().equalsIgnoreCase(sectionNum)) {
            	System.out.println("Students in " + c.getCourse_name() + ":");
                System.out.println(Arrays.toString(c.getStudent_list()));
                return;
            }
        }
        System.out.println("Course not found.");
    }
    
    // Show all courses that a student is registered in
    @Override
	public void displayCourseOfStu(String stu) {
    	ArrayList<Student> allStudents = CRSystem.getAllStudents();
        Student targetStudent = null;
        for (Student s : allStudents) {
            if (s.getUsername().equalsIgnoreCase(stu)) {
                targetStudent = s;
                break; // Student found, exit the loop.
            }
        }

        // Step 2: If the student doesn't exist, print an error and exit.
        if (targetStudent == null) {
            System.out.println("Student with username '" + stu + "' was not found in the system.");
            return;
        }

        String studentFullName = targetStudent.getFirstName() + " " + targetStudent.getLastName();
        System.out.println("--- Courses Registered by " + studentFullName + " (" + stu + ") ---");

        // Step 3: Iterate through every course to check if the student's full name is in its list.
        ArrayList<Course> allCourses = CRSystem.getMainCourseList();
        boolean foundACourse = false;

        for (Course course : allCourses) {
            String[] registeredStudents = course.getStudent_list();
            if (registeredStudents != null) { // Ensure the student list isn't null
                for (String registeredName : registeredStudents) {
                    // Check if the current name in the course list matches the student's full name.
                    if (registeredName != null && registeredName.equalsIgnoreCase(studentFullName)) {
                        System.out.println(course.report()); // Print the concise course report.
                        foundACourse = true;
                        break; // Found in this course, no need to check other names in the same course.
                    }
                }
            }
        }

        // Step 4: If after checking all courses, none were found, print a message.
        if (!foundACourse) {
            System.out.println(studentFullName + " is not registered for any courses.");
        }
        System.out.println("--------------------------------------------------");
    }
    
    // Sort courses by current number of registered students
    @Override
	public void sortCourses() {
    	ArrayList<Course> allCourses = CRSystem.getMainCourseList();
        // Use Collections.sort with a custom Comparator
        Collections.sort(allCourses, new Comparator<Course>() {
            @Override
            public int compare(Course c1, Course c2) {
                // Sorts in ascending order of current students
                return Integer.compare(c1.getCur_num(), c2.getCur_num());
            }
        });
        System.out.println("Courses sorted by current registration number:");
        viewAllCourse(); // Display the sorted list
    }
    
    
}
