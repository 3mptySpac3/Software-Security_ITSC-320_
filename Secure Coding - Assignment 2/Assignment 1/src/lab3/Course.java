package lab3;

public class Course {
    private String courseCode;
    private String name;
    private int credits;

    public Course(String courseCode, String name, int credits) {
        this.courseCode = courseCode;
        this.name = name;
        this.credits = credits;
    }

    // Getters
    public String getCourseCode() { return courseCode; }
    public String getName() { return name; }
    public int getCredits() { return credits; }
}
