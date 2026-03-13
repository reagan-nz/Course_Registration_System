package CRS;

public interface AdminAction {
	// Create a new course with given details.
	boolean CreateNewCourse(String course_name, String course_id, int maxStudent, int currentStudents, String[] nameList, String courseInstructor, String sectionNum, String courseLocation);

	// Delete a course using its ID and section number.
	boolean DeleteCourse( String course_id, String sectionNum);
	
	// Edit a course (but not the ID or name).
	boolean EditCourse(String course_id, String sectionNum);
	
	// Show information about a course.
	void DisplayInfo(String course_id, String sectionNum);
	
	// Register a new student into the system (not into a course).
	boolean RegisterStudent(String fn, String ln, String un, String pw);
	
	// Show all courses in the system.
	void viewAllCourse();
	
	// Show only the courses that are full (max students reached).
	void viewAllFull();
	
	// Save all full courses into a text file.
	void writeFullFile();
	
	// Show the names of students in one course.
	void displayNameOfCourse(String course_id, String sectionNum);
	
	// Show all courses that a certain student is registered in.
	void displayCourseOfStu(String stu);
	
	// Sort courses based on the number of students registered.
	void sortCourses();
}
