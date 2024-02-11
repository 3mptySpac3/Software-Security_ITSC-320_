package lab3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.FileWriter;



public class CSVReader {
	    private static Map<String, Student> students = new HashMap<>();
	    private static Map<String, Course> courses = new HashMap<>();
	    private static Map<String, String> studentCourseMap = new HashMap<>();



	    public static Map<String, Course> getCourses() {
	        return courses;
	    }
	    
	    public static Map<String, Student> getStudents() {
	        return students;
	    }
	    
	    public static Map<String, String> getStudentCourseMap() {
	        return studentCourseMap;
	    }

	    
	    public static void appendStudentToCSV(String filePath, Student student, String courseCode) {
	        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)))) {
	            // Retrieve the course name using the course code
	            Course course = courses.get(courseCode);
	            String courseName = (course != null) ? course.getName() : "Not enrolled";
	            
	            // Append a single student's data along with the course name
	            pw.printf("\n%s,%s,%f,%s", student.getStudentId(), student.getName(), student.getGpa(), courseName);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public static void addStudent(String studentId, String name, double gpa, String courseCode) {
	        Student newStudent = new Student(studentId, name, gpa);
	        students.put(studentId, newStudent);
	        studentCourseMap.put(studentId, courseCode); // Associate the student with a course
	        appendStudentToCSV("resources/Students.csv", newStudent, courseCode); // Now includes courseCode
	    }





	    public static void readStudentsCSV(String filePath) {
	        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            boolean isFirstLine = true;
	            while ((line = br.readLine()) != null) {
	                // Skip the header line
	                if (isFirstLine) {
	                    isFirstLine = false;
	                    continue;
	                }

	                String[] studentDetails = line.split(",");
	                if (studentDetails.length >= 4) {
	                    String studentId = studentDetails[0];
	                    String name = studentDetails[1];
	                    double gpa = Double.parseDouble(studentDetails[2]);
	                    students.put(studentId, new Student(studentId, name, gpa));
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }


	    
	
	    public static void readCoursesCSV(String filePath) {
	        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            boolean isFirstLine = true;
	            while ((line = br.readLine()) != null) {
	                if (isFirstLine) {
	                    isFirstLine = false; // Skip the header line
	                    continue;
	                }

	                String[] courseDetails = line.split(",");
	                if (courseDetails.length >= 3) {
	                    String courseCode = courseDetails[0].trim();
	                    String name = courseDetails[1].trim();
	                    int credits = Integer.parseInt(courseDetails[2].trim());
	                    courses.put(courseCode, new Course(courseCode, name, credits));
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }


    


    public static void main(String[] args) {
        CSVReader.readCoursesCSV("resources/Courses.csv");
        CSVReader.readStudentsCSV("resources/Students.csv");
        Validator studentIdValidator = new StudentIDValidator();
        Validator nameValidator = new NameValidator();
        Validator courseCodeValidator = new CourseCodeValidator();



        Scanner scanner = new Scanner(System.in);
        RegistrationSystem registrationSystem = new RegistrationSystem();

        while (true) {
            System.out.println("\nWelcome to the Student Course Registration System");
            System.out.println("1. Find a Student");
            System.out.println("2. Register a Student");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1, 2, or 3): ");
            


            String input = scanner.nextLine();
            int choice = 0;
            
            // Check if the input is a single digit and a valid choice
            if (input.matches("[1-3]")) {
                choice = Integer.parseInt(input);
            } else {
                System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                continue; // Go back to the start of the loop
            }

            switch (choice) {
            case 1: // Find a student
                System.out.print("Enter Student ID to find: ");
                String studentIdToFind = scanner.nextLine();
                Student foundStudent = CSVReader.getStudents().get(studentIdToFind);
                System.out.println("");
                if (foundStudent != null) {
                    System.out.println("Found Student: " + foundStudent.getName() + " (ID: " + studentIdToFind + ", GPA: " + foundStudent.getGpa() + ")");
                    
                    String courseCode = CSVReader.getStudentCourseMap().get(studentIdToFind);
                    if (courseCode != null && CSVReader.getCourses().containsKey(courseCode)) {
                        Course course = CSVReader.getCourses().get(courseCode);
                        System.out.println("Enrolled in Course: " + course.getCourseCode() + " - " + course.getName() + " (" + course.getCredits() + " credits)");
                    } else {
                        System.out.println("Not enrolled in a course or course not found.");
                    }
                } else {
                    System.out.println("Student not found");
                }
                scanner.nextLine();
                break;


                case 2: // Register a student
                    System.out.println("Enter details for new student registration (ex.S0000 - S000000):");
                    System.out.print("Enter New Student ID: ");
                    
                    String newStudentId = "";
                    while (newStudentId.length() == 0 || newStudentId.length() > 6) {
                        newStudentId = scanner.nextLine();
                        if (newStudentId.length() > 6) {
                            System.out.println("Student ID too long. Please limit to 6 characters.");
                        }
                    }
                    
                    if (CSVReader.getStudents().containsKey(newStudentId)) {
                        System.out.println("This Student ID is already in use. Please use a different ID.");
                        break;
                    }
                    
                    if (!studentIdValidator.validate(newStudentId)) {
                        System.out.println("Invalid Student ID format.");
                        continue; // Go back to the start of the loop
                    }

                    System.out.print("Enter Student Name: ");
                    String studentName = scanner.nextLine();
                    
                    if (!nameValidator.validate(studentName)) {
                        System.out.println("Invalid name format.");
                        continue; // Go back to the start of the loop
                    }

                    System.out.print("Enter Student GPA: ");
                    double studentGpa = scanner.nextDouble();
                    
                    scanner.nextLine();
                    
            
                 
                    if (studentGpa <= 2.0) {
                        System.out.println("Student GPA needs to be higher than 2.0 to register.");
                        break; 
                    }
                    
                    System.out.print("Congratulations! You Registered! Enjoy a life os pain. (✿◡‿◡) ");
                    
                    System.out.println("Enter Course Code for registration:");
                    System.out.println("CSCI101 - Introduction to Computer Science");
                    System.out.println("MATH201 - Advanced Calculus");
                    System.out.println("HIST102 - World History");
                    System.out.println("PHYS303 - Physics III");
                    System.out.println("ENGL205 - Literature and Composition");
                    
                    String courseCode = scanner.nextLine();
                    
                    if (!courseCodeValidator.validate(courseCode)) {
                        System.out.println("Invalid Course Code format. Please try again.");
                        continue; // Go back to the start of the loop
                    }

                    CSVReader.addStudent(newStudentId, studentName, studentGpa, courseCode);
                    System.out.println("Student Registered! ID: " + newStudentId + ", GPA: " + studentGpa + ", Course Code: " + courseCode);

                    registrationSystem.registerStudent(newStudentId, courseCode);
                    break;

                case 3: // Exit
                    System.out.println("Exiting the system.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                    break;
            }
        }
    }
    }

