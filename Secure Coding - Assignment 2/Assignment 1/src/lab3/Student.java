package lab3;

public class Student {
    private String studentId;
    private String name;
    private double gpa;

    // Constructor and getters
    public Student(String studentId, String name, double gpa) {
        this.studentId = studentId;
        this.name = name;
        this.gpa = gpa;
    }

    public String getStudentId() {
        return studentId;
    }

    public double getGpa() {
        return gpa;
    }

	public String getName() {
		return name;
	}


	
}
