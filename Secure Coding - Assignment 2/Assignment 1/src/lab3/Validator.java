package lab3;

@FunctionalInterface
public interface Validator {
    boolean validate(String input);
}