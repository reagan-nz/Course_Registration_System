# Course Registration System

A Java-based Course Registration System with a graphical user interface (GUI) built for a class project.  
This application allows administrators and students to manage course registration in a simple desktop environment.

## Overview

This project simulates a basic university course registration system. It supports two types of users:

- **Administrator**
- **Student**

The system provides login functionality, course management features, student registration features, and data persistence using serialized files.

## Features

### Administrator Functions
- Log in as administrator
- Create a new course
- Delete a course
- Edit course information
- Display course information
- Register a new student account
- View reports of all courses
- View full courses
- Export full courses to a text file
- Reset all data

### Student Functions
- Log in as student
- View all courses
- View all available courses that are not full
- Register for a course
- Withdraw from a course
- View all courses the student is registered in

## GUI

The project includes a Java Swing GUI for user interaction.

Main interface flow:
- Choose login type: **Administrator** or **Student**
- Log in with username and password
- Access role-based features through the GUI

## Project Structure

```text
NanyiZhao_HW1/
├── src/
│   └── CRS/
│       ├── CRSGUI.java
│       ├── CRSystem.java
│       ├── Admin.java
│       ├── Student.java
│       ├── Course.java
│       ├── User.java
│       ├── AdminAction.java
│       ├── StudentAction.java
│       └── MyUniversityCourses.csv
├── bin/
├── courses.ser
├── students.ser
├── FullCourses.txt
└── module-info.java
```

## Technologies Used

- **Java**
- **Java Swing**
- **Object Serialization**
- **CSV file input**
- **Eclipse/Java project structure**

## Data Files

The project uses the following files for data storage:

- `MyUniversityCourses.csv` — initial course data
- `courses.ser` — saved course data
- `students.ser` — saved student data
- `FullCourses.txt` — exported list of full courses

## Default Admin Login

```text
Username: Admin
Password: Admin001
```

## How to Run

### Option 1: Run in IntelliJ IDEA
1. Open the project in IntelliJ IDEA
2. Make sure the JDK is configured correctly
3. Locate `CRSGUI.java`
4. Run the `CRSGUI` class

### Option 2: Run from terminal
Compile and run the project using Java if your environment is configured properly.

## Notes

- This project was developed as a course assignment.
- Student and course data can be saved and loaded between sessions.
- The program uses local files for persistence instead of a database.
- The GUI version is intended to make the system easier to use than a console-only application.

## Future Improvements

Possible future enhancements include:
- Better GUI styling
- Improved validation and error handling
- Search and filter functions
- Database integration
- Password encryption
- Role-based permission improvements

## Author

**Nanyi Zhao**
