package model;

public class Class {
	private String classId; // Mã lớp học
	private String className; // Tên lớp học

	public Class() {
		super();
	}

	public Class(String classId, String className) {
		super();
		this.classId = classId;
		this.className = className;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public String toString() {
		return "Class [classId=" + classId + ", className=" + className + "]";
	}

}
