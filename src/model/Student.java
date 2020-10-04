package model;

public class Student {
	private String studentId;
	private String studentName;
	private Boolean studentGender;
	private String studentClass;

	public Student() {
		super();
	}

	public Student(String studentId, String studentName, Boolean studentGender, String studentClass) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentGender = studentGender;
		this.studentClass = studentClass;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Boolean getStudentGender() {
		return studentGender;
	}

	public void setStudentGender(Boolean studentGender) {
		this.studentGender = studentGender;
	}

	public String getStudentClass() {
		return studentClass;
	}

	public void setStudentClass(String studentClass) {
		this.studentClass = studentClass;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", studentName=" + studentName + ", studentGender=" + studentGender
				+ ", studentClass=" + studentClass + "]";
	}

}
