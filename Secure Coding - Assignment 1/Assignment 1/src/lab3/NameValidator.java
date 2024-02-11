package lab3;

public class NameValidator implements Validator {
    @Override
    public boolean validate(String name) {
        // Allows names with letters and a single space between first and last name
        return name != null && name.matches("^[A-Za-z]+( [A-Za-z]+){0,1}$") && name.length() <= 40;
    }
}

