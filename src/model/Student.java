package model;

public class Student {
	private String studentId; // Mã sinh viên
	private String studentName; // Tên sinh viên
	private Boolean studentGender; // Giới tính
	private String classId; // Mã lớp học

	public Student() {
		super();
	}

	public Student(String studentId, String studentName, Boolean studentGender, String classId) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentGender = studentGender;
		this.classId = classId;
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

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", studentName=" + studentName + ", studentGender=" + studentGender
				+ ", classId=" + classId + "]";
	}

}
