package lab3;

public class CourseCodeValidator implements Validator {
    @Override
    public boolean validate(String courseCode) {
        // Example validation: check if course code is not null and follows a specific pattern
        // You can adjust the pattern as per your requirement
        return courseCode != null && courseCode.matches("[A-Z]{4}\\d{3}");
    }
}

