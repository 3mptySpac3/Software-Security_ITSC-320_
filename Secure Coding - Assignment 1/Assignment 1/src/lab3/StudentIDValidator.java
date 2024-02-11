package lab3;

public class StudentIDValidator implements Validator {
    @Override
    public boolean validate(String studentId) {
        return studentId != null && studentId.matches("S\\d{4,6}");
    }
}

