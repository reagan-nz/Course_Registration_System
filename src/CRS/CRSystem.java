package CRS;
import java.util.*;
import java.io.*;
import java.lang.Thread;


public class CRSystem {
	
	// List of all courses
	private static ArrayList<Course> main_courselist = new ArrayList<>();
	
	// List of all students
	private static ArrayList<Student> students = new ArrayList<>();
	
	// File names for saving data
	private static final String cSaved = "courses.ser"; 
	private static final String studSaved = "students.ser"; 
	private static final String CSV_FILE = "src/CRS/MyUniversityCourses.csv";
	
	private static User currUser = null;// current logged-in user
	
	private static final String ADMIN_USERNAME = "Admin";// fixed admin username
	private static final String ADMIN_PASSWORD = "Admin001";// fixed admin password
	
	public static void main(String[] args) throws InterruptedException {
		Scanner scan = new Scanner(System.in);
		init();// load data from files or CSV
		while (true) {
            System.out.println("\n--- Course Registration System ---");
            System.out.println("1. Login as Student");
            System.out.println("2. Login as Admin");
            System.out.println("0. Exit and Save");
            
            try {
                int status = scan.nextInt();
                scan.nextLine(); 
                
                if (status == 0) {
                    save();
                    System.out.println("Your Progress is saved. Exiting...");
                    break;
                }
                else if (status == 1) {
                    studLogIn(scan);
                    if (currUser != null) {
                        studMenu(scan);
                    }
                }
                else if (status == 2) {
                    adminLogIn(scan);
                    if (currUser != null) {
                        adminMenu(scan);
                    }
                }
                else {
                    System.out.println("Invalid Input, please enter either 1, 2, or 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please enter a number.");
                scan.nextLine(); // Clear the buffer
            }
        }
		
	}
	
	// Initialize data from saved files or CSV
	private static void init() {
		try (FileInputStream cFileIn = new FileInputStream(cSaved);
	             ObjectInputStream cIn = new ObjectInputStream(cFileIn);
	             FileInputStream studFileIn = new FileInputStream(studSaved);
	             ObjectInputStream studIn = new ObjectInputStream(studFileIn)) {

	            main_courselist = (ArrayList<Course>) cIn.readObject();
	            students = (ArrayList<Student>) studIn.readObject();

	            System.out.println("Data successfully loaded from saved files.");

	        } catch (FileNotFoundException e) {
	            System.out.println("No saved data found. Initializing from CSV file...");
	            initComSepVal(CSV_FILE);
	            students.add(new Student("defaultUser", "defaultPass", "Default", "Student"));
	        } catch (IOException | ClassNotFoundException e) {
	            System.err.println("Error loading data. Starting with a fresh session.");
	            initComSepVal(CSV_FILE);
	        }
            
	}
	
	// Load courses from CSV file
	private static void initComSepVal(String filename) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] vals = line.split(",");
             // CSV format: name,id,max,cur,list,instructor,section,location
                if (vals.length == 8) {
                     // FIX: Follow Req 70 & 71 - current students is 0, list is null initially.
                    Course course = new Course(vals[0], vals[1], Integer.parseInt(vals[2]), 0, null, vals[5], vals[6], vals[7]);
                    main_courselist.add(course);
                }
            }
            System.out.println(main_courselist.size() + " courses loaded from " + filename);
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        } catch (NumberFormatException e){
            System.err.println("Error parsing number from CSV: " + e.getMessage());
        }
    }
	
	// Save courses and students to files using serialization
	private static void save() {
        // Serialization
            System.out.print("Wait.");// simple loading effect lol
            try {
				Thread.sleep(333);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            System.out.print(".");
            try {
				Thread.sleep(333);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            System.out.println(".");

            try (FileOutputStream cFileOut = new FileOutputStream(cSaved);
                    ObjectOutputStream cOut = new ObjectOutputStream(cFileOut);
                    FileOutputStream studFileOut = new FileOutputStream(studSaved);
                    ObjectOutputStream studOut = new ObjectOutputStream(studFileOut)) {

                   cOut.writeObject(main_courselist);
                   studOut.writeObject(students);

                   System.out.println("Data saved successfully.");
               } catch (IOException e) {
                   System.err.println("ERROR during saving: " + e.getMessage());
               }
           }
	
	
	
	
	// Getters for course list and students
	public static ArrayList<Course> getMainCourseList() {
		return main_courselist;
	}
	
	public static ArrayList<Student> getAllStudents() {
		return students;
	}
	
	// Find course index by ID
	public static int findIndex(String course_id) {
		if (main_courselist == null) return -1;
		for (int i = 0; i < main_courselist.size(); i++) {
			if (main_courselist.get(i).getCourse_id().equalsIgnoreCase(course_id)) {
				return i;
			}
		}
		return -1;
	}
	
	// Print all courses
	public static void displayAllCourses() {
		if (main_courselist == null || main_courselist.isEmpty()) {
			System.out.println("No courses currently available.");
			return;
		}
		else {
			System.out.println("--- ALL COURSES ---");

			for (Course n : main_courselist) {
				System.out.println(n.toString());
			}
		}
	}
	
	// Admin login
	 private static void adminLogIn(Scanner scanner) throws InterruptedException {
	     System.out.print("Enter Admin Username: ");
	     String id = scanner.nextLine();
	     System.out.print("Enter Admin Password: ");
	     String pw = scanner.nextLine();
	        
	     System.out.print("Logging in.");
         Thread.sleep(333);
         System.out.print(".");
         Thread.sleep(333);
         System.out.println(".");


	     if (id.equals(ADMIN_USERNAME) && pw.equals(ADMIN_PASSWORD)) {
	        // Instantiate an Admin object and assign to currUser
	        currUser = new Admin("Admin", "Admin001", "Admin", "User"); 
	        System.out.println("Admin login successful. Welcome!");
	     } else {
	        System.out.println("Invalid admin credentials.");
	     }
	  }
	 
	// Verify student login
	 private static Student studVeri(String id, String pw) {
	        for (Student s : students) { // FIX: Iterate over 'students' list
	            if (s.getUsername().equals(id) && s.getPassword().equals(pw)) {
	                return s;
	            }
	        }
	        return null;
	 }
	 
	// Student login process
	 private static void studLogIn(Scanner scanner) {
	        System.out.print("Enter Student Username: ");
	        String id = scanner.nextLine();
	        System.out.print("Enter Student Password: ");
	        String pw = scanner.nextLine();
	        
	        Student s = studVeri(id, pw);
	        
	        System.out.print("Logging in.");
	        try {
				Thread.sleep(333);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.print(".");
	        try {
				Thread.sleep(333);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println(".");

	        if (s != null) {
	            currUser = s;
	            System.out.println("Welcome, " + s.getFirstName() + "!");
	        } else {
	            System.out.println("Invalid username or password.");
	        }
	    }
    
	// Student menu options
	 private static void studMenu(Scanner scanner) {
	        // Cast the current user to a Student object to call its methods.
	        Student student = (Student) currUser;

	        while (currUser instanceof Student) {
	            System.out.println("\n--- Student Menu ---");
	            System.out.println("Welcome, " + student.getFirstName() + "!");
	            System.out.println("1. View all courses"); 
	            System.out.println("2. View all available (not full) courses"); 
	            System.out.println("3. Register for a course"); 
	            System.out.println("4. Withdraw from a course"); 
	            System.out.println("5. View your registered courses"); 
	            System.out.println("0. Logout"); 
	            System.out.print("Enter your choice: ");

	            try {
	                int choice = scanner.nextInt();
	                scanner.nextLine(); // Consume the newline character after reading an integer.

	                switch (choice) {
	                    case 1:
	                        student.viewAllCourses(main_courselist);
	                        break;
	                    case 2:
	                        student.viewAvailableCourses(main_courselist);
	                        break;
	                    case 3:
	                        System.out.print("Enter the course id: "); String regCourseId = scanner.nextLine();
	                        System.out.print("Enter the course section number: "); String regSectionNum = scanner.nextLine();
	                        student.registerForCourse(main_courselist, regCourseId, regSectionNum);
	                        break;
	                    case 4:
	                    	System.out.print("Enter the course id: "); String reCourseId = scanner.nextLine();
	                        System.out.print("Enter the course section number: "); String reSectionNum = scanner.nextLine();
	                        student.withdrawFromCourse(main_courselist, reCourseId, reSectionNum);
	                        break;
	                    case 5:
	                        student.viewMyCourses(main_courselist);
	                        break;
	                    case 0:
	                        currUser = null; // Set current user to null to exit the loop
	                        System.out.println("You have been logged out.");
	                        break; // This break exits the switch, and the loop condition will then be false.
	                    default:
	                        System.out.println("Invalid choice. Please enter a number between 0 and 5.");
	                }
	            } catch (java.util.InputMismatchException e) {
	                System.out.println("Invalid input. Please enter a number.");
	                scanner.nextLine(); // Clear the invalid input from the scanner.
	            }
	        }
	    }
	 	
		// Student menu options
	    private static void adminMenu(Scanner scanner) {
	        Admin admin = (Admin) currUser;

	        while (currUser instanceof Admin) {
	            System.out.println("\n--- Admin Menu ---");
	            System.out.println("Course Management:");
	            System.out.println("  1. Create a new course");
	            System.out.println("  2. Delete a course");
	            System.out.println("  3. Edit a course"); 
	            System.out.println("  4. Display info for a course"); 
	            System.out.println("  5. Register a new student account");
	            System.out.println("Reports:");
	            System.out.println("  6. View all courses"); 
	            System.out.println("  7. View all full courses"); 
	            System.out.println("  8. Write full courses to a file"); 
	            System.out.println("  9. View students in a course");
	            System.out.println("  10. View courses for a student"); 
	            System.out.println("  11. Sort courses by registration count");
	            System.out.println("  12. Reset ALL data (wipe history and reload from CSV)");
	            System.out.println("0. Logout");
	            System.out.print("Enter your choice: ");

	            try {
	                int choice = scanner.nextInt();
	                scanner.nextLine(); // Consume newline

	                switch (choice) {
	                    case 1: // Create Course
	                        System.out.print("Enter Course Name: "); String name = scanner.nextLine();
	                        System.out.print("Enter Course ID: "); String id = scanner.nextLine();
	                        System.out.print("Enter Max Students: "); int max = scanner.nextInt(); scanner.nextLine();
	                        System.out.print("Enter Instructor: "); String inst = scanner.nextLine();
	                        System.out.print("Enter Section Number: "); String sec = scanner.nextLine();
	                        System.out.print("Enter Location: "); String loc = scanner.nextLine();
	                        admin.CreateNewCourse(name, id, max, 0, null, inst, sec, loc);
	                        break;
	                    case 2: // Delete Course
	                        System.out.print("Enter Course ID to delete: ");String d_id = scanner.nextLine();
	                        System.out.print("Enter Section Number to delete: "); String d_sec = scanner.nextLine();
	                        admin.DeleteCourse(d_id, d_sec);
	                        break;
	                    case 3: // Edit Course
	                    	System.out.print("Enter Course ID to edit: ");String e_id = scanner.nextLine();
	                        System.out.print("Enter Section Number to edit: "); String e_sec = scanner.nextLine();
	                        admin.EditCourse(e_id, e_sec);
	                        break;
	                    case 4: // Display Course Info
	                    	System.out.print("Enter Course ID to display: ");String c_id = scanner.nextLine();
	                        System.out.print("Enter Section Number to display: "); String c_sec = scanner.nextLine();
	                        admin.DisplayInfo(c_id, c_sec);
	                        break;
	                    case 5: // Register Student
	                        System.out.print("Enter Student First Name: "); String fn = scanner.nextLine();
	                        System.out.print("Enter Student Last Name: "); String ln = scanner.nextLine();
	                        System.out.print("Enter desired Username: "); String un = scanner.nextLine();
	                        System.out.print("Enter temporary Password: "); String pw = scanner.nextLine();
	                        admin.RegisterStudent(fn, ln, un, pw);
	                        break;
	                    case 6: // View All Courses Report
	                        admin.viewAllCourse();
	                        break;
	                    case 7: // View Full Courses
	                        admin.viewAllFull();
	                        break;
	                    case 8: // Write Full Courses to File
	                        admin.writeFullFile();
	                        break;
	                    case 9: // View Students in a Course
	                    	System.out.print("Enter Course ID to display: ");String f_id = scanner.nextLine();
	                        System.out.print("Enter Section Number to display: "); String f_sec = scanner.nextLine();
	                        admin.displayNameOfCourse(f_id, f_sec);
	                        break;
	                    case 10: // View Courses for a Student
	                        System.out.print("Enter a student's username to see their courses: ");
	                        admin.displayCourseOfStu(scanner.nextLine());
	                        break;
	                    case 11: // Sort Courses
	                        admin.sortCourses();
	                        break;
	                    case 0: // Logout
	                        currUser = null;
	                        System.out.println("Admin has been logged out.");
	                        break;
	                    case 12: // Reset All Data
	                        resetAllData();
	                        break;
	                    default:
	                        System.out.println("Invalid choice. Please enter a number between 0 and 11.");
	                }
	            } catch (java.util.InputMismatchException e) {
	                System.out.println("Invalid input. Please enter a number.");
	                scanner.nextLine(); // Clear buffer
	            }
	        }
	    }
	    
	    private static void resetAllData() {
	        // Delete serialized files
	        String[] files = {cSaved, studSaved};
	        for (String f : files) {
	            File file = new File(f);
	            if (file.exists()) {
	                if (file.delete()) {
	                    System.out.println("Deleted file: " + f);
	                } else {
	                    System.out.println("⚠️ Could not delete " + f);
	                }
	            }
	        }

	        // Clear in-memory lists
	        main_courselist.clear();
	        students.clear();

	        // Reload courses from CSV
	        initComSepVal(CSV_FILE);

	        // Re-add default student (so login system still works)
	        students.add(new Student("defaultUser", "defaultPass", "Default", "Student"));

	        System.out.println("All data has been reset. System reloaded from CSV.");
	        System.out.println("You will now be logged out...");

	        // Force logout
	        currUser = null;
	    }
	    
	    public static void initForGUI() {
	        if (students == null) students = new ArrayList<>();
	        if (main_courselist == null) main_courselist = new ArrayList<>();
	        init(); // if you already load from CSV/serialization
	    }

	    
	    public static void saveAll() {
	        save();
	    }

}
	
