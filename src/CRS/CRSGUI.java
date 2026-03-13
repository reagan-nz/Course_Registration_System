package CRS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CRSGUI {

    private JFrame loginFrame;
    private Student loggedStudent;
    private String LoginStatus;
    private String AdminStatus;

    public CRSGUI() {
        SwingUtilities.invokeLater(this::loginStatus);
    }
    
    private void loginStatus() {
    	JFrame loginStatus = new JFrame("NYU Course Registration System");
    	loginStatus.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	loginStatus.setSize(400, 240);
    	loginStatus.setLocationRelativeTo(null);
    	
    	JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        
        JButton adminBtn = new JButton("Administrator");
        JButton stuBtn = new JButton("Student");
        JButton quitBtn = new JButton("Exit");
        JPanel buttons = new JPanel(new GridLayout(3, 1, 8, 8));
        buttons.add(adminBtn);
        buttons.add(stuBtn);
        buttons.add(quitBtn);
        main.add(buttons, BorderLayout.CENTER);
        
        loginStatus.setContentPane(main);
        loginStatus.setVisible(true);
        
        adminBtn.addActionListener(e -> {
        	LoginStatus = "Admin";
        	SwingUtilities.invokeLater(this::createLoginFrame);
        });
        
        stuBtn.addActionListener(e -> {
        	LoginStatus = "Student";
        	SwingUtilities.invokeLater(this::createLoginFrame);
        });
        quitBtn.addActionListener(e -> {
            int r = JOptionPane.showConfirmDialog(loginFrame,
                "Save all data before exit?", "Confirm Exit", JOptionPane.YES_NO_CANCEL_OPTION);
            if (r == JOptionPane.CANCEL_OPTION) return;
            if (r == JOptionPane.YES_OPTION) {
                try {
                    CRSystem.saveAll();  // add this new public static helper below
                    JOptionPane.showMessageDialog(loginFrame, "Data saved successfully.", "Saved", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(loginFrame, "Error saving data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            System.exit(0);
        });

    }

    private void createLoginFrame() {
        loginFrame = new JFrame("NYU Course Registration System - Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 240);
        loginFrame.setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        
        if ("Admin".equals(LoginStatus)) {
        	JLabel title = new JLabel("Course Registration - Admin Login", SwingConstants.CENTER);
        	title.setFont(new Font("SansSerif", Font.BOLD, 16));
            main.add(title, BorderLayout.NORTH);
        	}
        else {
        	JLabel title = new JLabel("Course Registration - Student Login", SwingConstants.CENTER);
        	title.setFont(new Font("SansSerif", Font.BOLD, 16));
            main.add(title, BorderLayout.NORTH);
        	}

        JPanel form = new JPanel(new GridLayout(4, 1, 6, 6));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        form.add(new JLabel("Username:"));
        form.add(usernameField);
        form.add(new JLabel("Password:"));
        form.add(passwordField);
        main.add(form, BorderLayout.CENTER);

        JButton loginBtn = new JButton("Login");
        JButton logout = new JButton("Exit");
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttons.add(loginBtn);
        buttons.add(logout);
        main.add(buttons, BorderLayout.SOUTH);

        loginFrame.setContentPane(main);
        loginFrame.setVisible(true);

        loginBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if ("Admin".equals(username) && "Admin001".equals(password)) {
                loginFrame.dispose();
                SwingUtilities.invokeLater(() -> adminStatus());
                return;
            }

            Student matched = null;
            ArrayList<Student> students = CRSystem.getAllStudents();
            if (students != null) {
                for (Student s : students) {
                    if (s.getUsername().equals(username) && s.getPassword().equals(password)) {
                        matched = s;
                        break;
                    }
                }
            }

            if (matched != null) {
                loggedStudent = matched;
                loginFrame.dispose();
                SwingUtilities.invokeLater(() -> showStudentFrame(loggedStudent));
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid username or password.", "Login failed", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        logout.addActionListener(e -> {
            loginFrame.dispose();
            SwingUtilities.invokeLater(this::loginStatus);
        });
    }

    private void adminStatus() {
    	JFrame adminStatus = new JFrame("NYU Course Registration System");
    	adminStatus.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	adminStatus.setSize(400, 240);
    	adminStatus.setLocationRelativeTo(null);
    	
    	JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        
        JButton menuBtn = new JButton("Operation");
        JButton reportBtn = new JButton("Report");
        JButton logout = new JButton("Logout");
        JPanel buttons = new JPanel(new GridLayout(3, 1, 8, 8));
        buttons.add(menuBtn);
        buttons.add(reportBtn);
        buttons.add(logout);
        main.add(buttons, BorderLayout.CENTER);
        
        adminStatus.setContentPane(main);
        adminStatus.setVisible(true);
        
        menuBtn.addActionListener(e -> {
        	LoginStatus = "Menu";
        	SwingUtilities.invokeLater(this::showAdminMenu);
        });
        
        reportBtn.addActionListener(e -> {
        	LoginStatus = "Report";
        	SwingUtilities.invokeLater(this::showAdminReport);
        });
        
        // Logout returns to login frame
        logout.addActionListener(e -> {
            adminStatus.dispose();
            SwingUtilities.invokeLater(this::loginStatus);
        });
    }
    
    private void showAdminMenu() {
        JFrame frame = new JFrame("Admin Operation");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(650, 420);
        frame.setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        JLabel title = new JLabel("Operation", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        main.add(title, BorderLayout.NORTH);
        
        JPanel buttons = new JPanel(new GridLayout(0, 1, 8, 8));
        JButton addCour = new JButton("Create A New Course");
        JButton deleteCour = new JButton("Delete A Course");
        JButton editCour = new JButton("Edit A Course");
        JButton displayCour = new JButton("Display Info For A Course");
        JButton registStu = new JButton("Register A New Student Account");
        JButton reset = new JButton("Reset All Data");
        JButton logout = new JButton("Exit");
        
        buttons.add(addCour);
        buttons.add(deleteCour);
        buttons.add(editCour);
        buttons.add(displayCour);
        buttons.add(registStu);
        buttons.add(reset);
        buttons.add(logout);
        

        main.add(buttons, BorderLayout.CENTER);
        frame.setContentPane(main);
        frame.setVisible(true);
        
        
        addCour.addActionListener(e -> {
            JTextField nameF = new JTextField();
            JTextField idF = new JTextField();
            JTextField maxF = new JTextField();
            JTextField instF = new JTextField();
            JTextField secF = new JTextField();
            JTextField locF = new JTextField();

            Object[] fields = {
                    "Course Name:", nameF,
                    "Course ID:", idF,
                    "Max Students (integer):", maxF,
                    "Instructor:", instF,
                    "Section Number:", secF,
                    "Location:", locF
            };
            int res = JOptionPane.showConfirmDialog(frame, fields, "Add New Course", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                try {
                    String name = nameF.getText().trim();
                    String id = idF.getText().trim();
                    int max = Integer.parseInt(maxF.getText().trim());
                    String inst = instF.getText().trim();
                    String sec = secF.getText().trim();
                    String loc = locF.getText().trim();

                    // Use Admin.CreateNewCourse to preserve checks
                    Admin admin = new Admin("Admin", "Admin001", "Admin", "User");
                    boolean ok = admin.CreateNewCourse(name, id, max, 0, null, inst, sec, loc);
                    if (ok) {
                        JOptionPane.showMessageDialog(frame, "Course added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Course not added (may already exist).", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Max students must be an integer.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        deleteCour.addActionListener(e -> {
        	JTextField idF = new JTextField();
        	JTextField secF = new JTextField();
        	
        	Object[] fields = {
                    "Course ID:", idF,
                    "Section Number:", secF,
            };
        	
        	int res = JOptionPane.showConfirmDialog(frame, fields, "Delete Course", JOptionPane.OK_CANCEL_OPTION);
        	if (res == JOptionPane.OK_OPTION) {
                try {
                    String id = idF.getText().trim();
                    String sec = secF.getText().trim();

                    // Use Admin.CreateNewCourse to preserve checks
                    Admin admin = new Admin("Admin", "Admin001", "Admin", "User");
                    boolean ok = admin.DeleteCourse(id, sec);
                    if (ok) {
                        JOptionPane.showMessageDialog(frame, "Course deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Course not deleted (may not exist).", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        editCour.addActionListener(e -> {
            JTextField idF = new JTextField();
            JTextField secF = new JTextField();
            Object[] findFields = {
                "Course ID:", idF,
                "Section Number:", secF
            };
            int res = JOptionPane.showConfirmDialog(frame, findFields, "Find Course to Edit", JOptionPane.OK_CANCEL_OPTION);
            if (res != JOptionPane.OK_OPTION) return;

            String id = idF.getText().trim();
            String sec = secF.getText().trim();
            ArrayList<Course> allCourses = CRSystem.getMainCourseList();
            Course target = null;
            for (Course c : allCourses) {
                if (c.getCourse_id().equalsIgnoreCase(id) && c.getSection_num().equalsIgnoreCase(sec)) {
                    target = c;
                    break;
                }
            }

            if (target == null) {
                JOptionPane.showMessageDialog(frame, "Course not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] options = {
                "Max Students",
                "Current Students",
                "Student List",
                "Instructor",
                "Section Number",
                "Location"
            };
            String choice = (String) JOptionPane.showInputDialog(
                    frame,
                    "Select field to edit:",
                    "Edit Course",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == null) return;

            switch (choice) {
                case "Max Students": {
                    String input = JOptionPane.showInputDialog(frame, "Enter new max students:", target.getMax_stu());
                    if (input == null) return;
                    try {
                        int newMax = Integer.parseInt(input);
                        if (newMax < target.getCur_num()) {
                            JOptionPane.showMessageDialog(frame, "Cannot set max < current number of students.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        target.setMax_stu(newMax);
                        String[] oldList = target.getStudent_list();
                        String[] newList = new String[newMax];
                        for (int i = 0; i < Math.min(newMax, oldList.length); i++) {
                            newList[i] = oldList[i];
                        }
                        target.setStudent_list(newList);
                        JOptionPane.showMessageDialog(frame, "Max students updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
                case "Current Students": {
                    String input = JOptionPane.showInputDialog(frame, "Enter new current students:", target.getCur_num());
                    if (input == null) return;
                    try {
                        int newCur = Integer.parseInt(input);
                        if (newCur > target.getMax_stu()) {
                            JOptionPane.showMessageDialog(frame, "Cannot exceed max students.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        target.setCur_num(newCur);
                        JOptionPane.showMessageDialog(frame, "Current students updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
                case "Student List": {
                    int n = target.getMax_stu();
                    JTextArea area = new JTextArea(10, 40);
                    StringBuilder sb = new StringBuilder();
                    String[] oldList = target.getStudent_list();
                    for (String s : oldList) if (s != null) sb.append(s).append("\n");
                    area.setText(sb.toString());
                    int result = JOptionPane.showConfirmDialog(frame, new JScrollPane(area), "Enter Student Names (one per line, up to " + n + ")", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        String[] lines = area.getText().split("\\R");
                        if (lines.length > n) {
                            JOptionPane.showMessageDialog(frame, "Too many names (max " + n + ").", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        String[] newList = new String[n];
                        for (int i = 0; i < lines.length; i++) {
                            newList[i] = lines[i].trim();
                        }
                        target.setStudent_list(newList);
                        target.setCur_num(lines.length);
                        JOptionPane.showMessageDialog(frame, "Student list updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                }
                case "Instructor": {
                    String newInstructor = JOptionPane.showInputDialog(frame, "Enter new instructor name:", target.getCourse_instructor());
                    if (newInstructor != null) {
                        target.setCourse_instructor(newInstructor.trim());
                        JOptionPane.showMessageDialog(frame, "Instructor updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                }
                case "Section Number": {
                    String newSec = JOptionPane.showInputDialog(frame, "Enter new section number:", target.getSection_num());
                    if (newSec != null) {
                        target.setSection_num(newSec.trim());
                        JOptionPane.showMessageDialog(frame, "Section number updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                }
                case "Location": {
                    String newLoc = JOptionPane.showInputDialog(frame, "Enter new location:", target.getCourse_loc());
                    if (newLoc != null) {
                        target.setCourse_loc(newLoc.trim());
                        JOptionPane.showMessageDialog(frame, "Location updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                }
                default:
                    JOptionPane.showMessageDialog(frame, "Invalid choice.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        
        displayCour.addActionListener(e -> {
            JTextField idF = new JTextField();
            JTextField secF = new JTextField();
            Object[] fields = {
                "Course ID:", idF,
                "Section Number:", secF,
            };
            int res = JOptionPane.showConfirmDialog(frame, fields, "Display Course", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                String id = idF.getText().trim();
                String sec = secF.getText().trim();
                Admin admin = new Admin("Admin", "Admin001", "Admin", "User");

                java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                java.io.PrintStream ps = new java.io.PrintStream(baos);
                java.io.PrintStream oldOut = System.out;
                System.setOut(ps);
                admin.DisplayInfo(id, sec);
                System.out.flush();
                System.setOut(oldOut);
                String info = baos.toString();

                if (info.isBlank()) info = "Course not found.";
                JTextArea ta = new JTextArea(info);
                ta.setEditable(false);
                JOptionPane.showMessageDialog(frame, new JScrollPane(ta),
                    "Course Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        
        registStu.addActionListener(e -> {
            JTextField unF = new JTextField();
            JTextField pwF = new JTextField();
            JTextField fnF = new JTextField();
            JTextField lnF = new JTextField();
            Object[] fields = {
                "Username:", unF,
                "Password:", pwF,
                "First Name:", fnF,
                "Last Name:", lnF
            };

            int res = JOptionPane.showConfirmDialog(frame, fields, "Register New Student", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                String un = unF.getText().trim();
                String pw = pwF.getText().trim();
                String fn = fnF.getText().trim();
                String ln = lnF.getText().trim();

                Admin admin = new Admin("Admin", "Admin001", "Admin", "User");
                boolean ok = admin.RegisterStudent(un, pw, fn, ln);
                if (ok) {
                    CRSystem.getAllStudents().add(new Student(un, pw, fn, ln));
                    JOptionPane.showMessageDialog(frame, "Student registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Registration failed (username may exist).", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        
        reset.addActionListener(e -> {
            int res = JOptionPane.showConfirmDialog(frame,
                "This will clear all courses and students. Continue?",
                "Confirm Reset", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                CRSystem.getMainCourseList().clear();
                CRSystem.getAllStudents().clear();
                JOptionPane.showMessageDialog(frame, "All data cleared.", "Reset Done", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        logout.addActionListener(e -> {
            frame.dispose();
            SwingUtilities.invokeLater(this::adminStatus);
        });
    }
    
    private void showAdminReport() {
    	JFrame frame = new JFrame("Admin Report");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(650, 420);
        frame.setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        JLabel title = new JLabel("Report", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        main.add(title, BorderLayout.NORTH);
        
        JPanel buttons = new JPanel(new GridLayout(0, 1, 8, 8));
        JButton viewAll = new JButton("View All Courses");
        JButton viewFull = new JButton("View Full Courses");
        JButton writeFullFile = new JButton("Write Full Courses to File");
        JButton viewStu = new JButton("View Students In A Course");
        JButton viewCour = new JButton("View Courses For A Student");
        JButton sortCour = new JButton("Sort Courses By Registration Count");
        JButton logout = new JButton("Exit");
        
        buttons.add(viewAll);
        buttons.add(viewFull);
        buttons.add(writeFullFile);
        buttons.add(viewStu);
        buttons.add(viewCour);
        buttons.add(sortCour);
        buttons.add(logout);
        
        main.add(buttons, BorderLayout.CENTER);
        frame.setContentPane(main);
        frame.setVisible(true);
        
        viewStu.addActionListener(e -> {
            JTextField idF = new JTextField();
            JTextField secF = new JTextField();
            Object[] fields = {
                "Course ID:", idF,
                "Section Number:", secF
            };
            int res = JOptionPane.showConfirmDialog(frame, fields, "Students In Course", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                String id = idF.getText().trim();
                String sec = secF.getText().trim();
                Admin admin = new Admin("Admin", "Admin001", "Admin", "User");
                java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                java.io.PrintStream ps = new java.io.PrintStream(baos);
                java.io.PrintStream oldOut = System.out;
                System.setOut(ps);
                admin.displayNameOfCourse(id, sec);
                System.out.flush();
                System.setOut(oldOut);
                JTextArea ta = new JTextArea(baos.toString());
                ta.setEditable(false);
                JOptionPane.showMessageDialog(frame, new JScrollPane(ta), "Students in Course", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        viewCour.addActionListener(e -> {
            JTextField stuF = new JTextField();
            Object[] fields = {"Student Username:", stuF};
            int res = JOptionPane.showConfirmDialog(frame, fields, "Courses of Student", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                String username = stuF.getText().trim();
                Admin admin = new Admin("Admin", "Admin001", "Admin", "User");
                java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                java.io.PrintStream ps = new java.io.PrintStream(baos);
                java.io.PrintStream oldOut = System.out;
                System.setOut(ps);
                admin.displayCourseOfStu(username);
                System.out.flush();
                System.setOut(oldOut);
                JTextArea ta = new JTextArea(baos.toString());
                ta.setEditable(false);
                JOptionPane.showMessageDialog(frame, new JScrollPane(ta), "Courses of Student", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        sortCour.addActionListener(e -> {
            Admin admin = new Admin("Admin", "Admin001", "Admin", "User");
            admin.sortCourses();
            JOptionPane.showMessageDialog(frame, "Courses sorted by current enrollment (see console for details).", "Sorted", JOptionPane.INFORMATION_MESSAGE);
        });


        viewAll.addActionListener(e -> {
            ArrayList<Course> courses = CRSystem.getMainCourseList();
            if (courses == null || courses.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No courses available.", "All Courses", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            JTextArea ta = new JTextArea(20, 50);
            ta.setEditable(false);
            StringBuilder sb = new StringBuilder();
            for (Course c : courses) {
                sb.append(c.toString()).append("\n-----------------\n");
            }
            ta.setText(sb.toString());
            JScrollPane sp = new JScrollPane(ta);
            JOptionPane.showMessageDialog(frame, sp, "All Courses", JOptionPane.INFORMATION_MESSAGE);
        });

        viewFull.addActionListener(e -> {
            ArrayList<Course> courses = CRSystem.getMainCourseList();
            StringBuilder sb = new StringBuilder();
            boolean any = false;
            if (courses != null) {
                for (Course c : courses) {
                    if (c.getCur_num() == c.getMax_stu()) {
                        sb.append(c.toString()).append("\n-----------------\n");
                        any = true;
                    }
                }
            }
            if (!any) sb.append("No full courses found.");
            JTextArea ta = new JTextArea(sb.toString());
            ta.setEditable(false);
            JOptionPane.showMessageDialog(frame, new JScrollPane(ta), "Full Courses", JOptionPane.INFORMATION_MESSAGE);
        });

        writeFullFile.addActionListener(e -> {
            Admin admin = new Admin("Admin", "Admin001", "Admin", "User");
            admin.writeFullFile();
            JOptionPane.showMessageDialog(frame, "writeFullFile() invoked. Check FullCourses.txt in working directory.", "Done", JOptionPane.INFORMATION_MESSAGE);
        });
        
        logout.addActionListener(e -> {
            frame.dispose();
            SwingUtilities.invokeLater(this::adminStatus);
        });
    }

    private void showStudentFrame(Student student) {
        JFrame frame = new JFrame("Student Dashboard - " + student.getFirstName());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(650, 450);
        frame.setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        JLabel title = new JLabel("Welcome, " + student.getFirstName() + " " + student.getLastName(), SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        main.add(title, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new GridLayout(0, 1, 8, 8));

        JButton viewAll = new JButton("View All Courses");
        JButton viewAvailable = new JButton("View All Available Courses");
        JButton registerCourse = new JButton("Register for a Course");
        JButton withdrawCourse = new JButton("Withdraw from a Course");
        JButton viewMine = new JButton("View My Registered Courses");
        JButton logout = new JButton("Logout");

        buttons.add(viewAll);
        buttons.add(viewAvailable);
        buttons.add(registerCourse);
        buttons.add(withdrawCourse);
        buttons.add(viewMine);
        buttons.add(logout);
        main.add(buttons, BorderLayout.CENTER);

        frame.setContentPane(main);
        frame.setVisible(true);

        viewAll.addActionListener(e -> {
            ArrayList<Course> courses = CRSystem.getMainCourseList();
            if (courses == null || courses.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No courses available.", "All Courses", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            JTextArea ta = new JTextArea(20, 50);
            ta.setEditable(false);
            StringBuilder sb = new StringBuilder();
            for (Course c : courses) sb.append(c.report()).append("\n-----------------\n");
            ta.setText(sb.toString());
            JOptionPane.showMessageDialog(frame, new JScrollPane(ta), "All Courses", JOptionPane.INFORMATION_MESSAGE);
        });

        viewAvailable.addActionListener(e -> {
            ArrayList<Course> courses = CRSystem.getMainCourseList();
            StringBuilder sb = new StringBuilder();
            boolean found = false;
            for (Course c : courses) {
                if (c.getCur_num() < c.getMax_stu()) {
                    sb.append(c.report()).append("\n-----------------\n");
                    found = true;
                }
            }
            if (!found) sb.append("No available (not full) courses found.");
            JTextArea ta = new JTextArea(sb.toString());
            ta.setEditable(false);
            JOptionPane.showMessageDialog(frame, new JScrollPane(ta), "Available Courses", JOptionPane.INFORMATION_MESSAGE);
        });

        registerCourse.addActionListener(e -> {
            JTextField idF = new JTextField();
            JTextField secF = new JTextField();
            Object[] fields = {
                "Course ID:", idF,
                "Section Number:", secF
            };
            int res = JOptionPane.showConfirmDialog(frame, fields, "Register for Course", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                String id = idF.getText().trim();
                String sec = secF.getText().trim();
                student.registerForCourse(CRSystem.getMainCourseList(), id, sec);
                JOptionPane.showMessageDialog(frame, "Attempted to register (check console or My Courses for confirmation).", "Registration", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        withdrawCourse.addActionListener(e -> {
            JTextField idF = new JTextField();
            JTextField secF = new JTextField();
            Object[] fields = {
                "Course ID:", idF,
                "Section Number:", secF
            };
            int res = JOptionPane.showConfirmDialog(frame, fields, "Withdraw from Course", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                String id = idF.getText().trim();
                String sec = secF.getText().trim();
                student.withdrawFromCourse(CRSystem.getMainCourseList(), id, sec);
                JOptionPane.showMessageDialog(frame, "Attempted to withdraw (check console or My Courses for confirmation).", "Withdrawal", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        viewMine.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            String fullName = student.getFirstName() + " " + student.getLastName();
            ArrayList<Course> courses = CRSystem.getMainCourseList();
            boolean found = false;
            for (Course c : courses) {
                for (String sName : c.getStudent_list()) {
                    if (sName != null && sName.equalsIgnoreCase(fullName)) {
                        sb.append(c.report()).append("\n-----------------\n");
                        found = true;
                        break;
                    }
                }
            }
            if (!found) sb.append("You are not registered for any courses.");
            JTextArea ta = new JTextArea(sb.toString());
            ta.setEditable(false);
            JOptionPane.showMessageDialog(frame, new JScrollPane(ta), "My Registered Courses", JOptionPane.INFORMATION_MESSAGE);
        });

        // 0. Logout
        logout.addActionListener(e -> {
            frame.dispose();
            loggedStudent = null;
            SwingUtilities.invokeLater(this::createLoginFrame);
        });
    }


    
    public static void main(String[] args) {
        CRSystem.initForGUI();
        SwingUtilities.invokeLater(() -> new CRSGUI());
    }
}
