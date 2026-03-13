package CRS;
import java.io.Serializable;

//This class represents a Course in the Course Registration System (CRS).
// It implements Serializable so it can be saved to a file (object persistence).
public class Course implements Serializable{
	
	// This ID is used when saving/loading objects to ensure compatibility.
	private static final long serialVersionUID = 2L;
	
	// --- Fields (information about a course) ---
	private String course_name, course_id,course_instructor, course_loc,section_num;
	private int max_stu, cur_num;
	private String[] student_list;
	
	// --- Constructors ---
	// Original constructor
	public Course(String course_name, String course_id, int maxStudent, int currentStudents, String[] nameList, String courseInstructor, String sectionNum, String courseLocation) {
		this.course_name = course_name;
		this.course_id = course_id;
		this.max_stu = maxStudent;
		this.cur_num = currentStudents;
		this.course_instructor = courseInstructor;
		this.section_num = sectionNum;
		this.course_loc = courseLocation;
		
		// Initialize the student list with max size
		this.student_list = new String[this.max_stu];
		
		// Copy existing students into the list (if any were passed in)
		if (nameList != null) {
			for (int i = 0; i < currentStudents && i < max_stu; i++) {
				this.student_list[i] = nameList[i];
			}
		}
	}

	// Overloaded constructor: no instructor or location yet
	public Course(String name, String id, int maxStu){
	    this.course_name = name;
	    this.course_id = id;
	    this.max_stu = maxStu;
	    this.cur_num = 0;
	    this.student_list = new String[maxStu];
	    this.course_instructor = "TBD";
	    this.section_num = "001";
	    this.course_loc = "TBD";
	}
	
	// --- Equality check ---
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Course course = (Course)obj;
		return course_id != null && course_id.equalsIgnoreCase(course.course_id) && section_num != null && section_num.equalsIgnoreCase(course.section_num);
	}
	
	// --- Getters and Setters (to read or update values safely) ---
	public String getCourse_name() { return course_name; }
    public void setCourse_name(String cn) { this.course_name = cn; }
    
    public String getCourse_id() { return course_id; }
    public void setCourse_id(String course_id) { this.course_id = course_id; }
    
    public int getMax_stu() { return max_stu; }
    public void setMax_stu(int max_stu) { this.max_stu = max_stu; }
    
    public int getCur_num() { return cur_num; }
    public void setCur_num(int cur_num) { this.cur_num = cur_num; }
    
    public String[] getStudent_list() { return student_list; }
    public void setStudent_list(String[] student_list) { this.student_list = student_list; }
    
    public String getCourse_instructor() { return course_instructor; }
    public void setCourse_instructor(String course_instructor) { this.course_instructor = course_instructor; }
    
    public String getSection_num() { return section_num; }
    public void setSection_num(String section_num) { this.section_num = section_num; }
    
    public String getCourse_loc() { return course_loc; }
    public void setCourse_loc(String course_loc) { this.course_loc = course_loc; }
	
    // --- Simple report (short summary about the course) ---
	public String report() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("Course Name: ").append(course_name).append(" (").append(course_id).append(")\n");
		sb.append("  Registerted: ").append(cur_num).append("\n");
		sb.append("  Maximum Number of Student: ").append(max_stu);
		
		return sb.toString();
	}
	
	
	// --- Full details about the course ---
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("Course Name: ").append(course_name).append(" (").append(course_id).append(")\n");
		sb.append("  Instructor: ").append(course_instructor);
		sb.append(" | Section: ").append(section_num);
		sb.append(" | Location: ").append(course_loc).append("\n");
		sb.append("  Registration: ").append(cur_num).append(" / ").append(max_stu);
		
		if (cur_num >= max_stu) {
		    sb.append(" (FULL)");
		}
		
		return sb.toString();
	}
}
