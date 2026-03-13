package CRS;

import java.util.ArrayList;

interface StudentAction {

	// Show all courses (full or not).
    void viewAllCourses(ArrayList<Course> allCourses);
    
    // Show only courses that are not full.
    void viewAvailableCourses(ArrayList<Course> allCourses);
    
    // Register the student into a course (by ID and section).
    void registerForCourse(ArrayList<Course> allCourses, String courseId, String sectionNum);
    
    // Withdraw the student from a course they are already in.
    void withdrawFromCourse(ArrayList<Course> allCourses, String courseId, String sectionNum);
    
    // Show all courses the current student is registered in.
    void viewMyCourses(ArrayList<Course> allCourses);
    
}