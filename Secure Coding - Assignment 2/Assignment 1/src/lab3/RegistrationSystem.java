package lab3;

public class RegistrationSystem {
    private Validator studentIdValidator = new StudentIDValidator();
    private Validator courseCodeValidator = new CourseCodeValidator();

    public void registerStudent(String studentId, String courseCode) {
        if (!studentIdValidator.validate(studentId)) {
            System.out.println("Invalid Student ID");
            return;
        }
        if (!courseCodeValidator.validate(courseCode)) {
            System.out.println("Invalid Course Code");
            return;
        }

        Student student = CSVReader.getStudents().get(studentId);
        if (student == null) {
            System.out.println("Student not found");
            return;
        }
        if (student.getGpa() <= 2.0) {
            System.out.println("Student does not meet GPA requirements");
            return;
        }

        // Proceed with registration
    }
    
    
}

