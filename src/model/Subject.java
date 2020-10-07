package model;

public class Subject {
	private String subjectId; // Mã môn học
	private String subjectName; // Tên môn học
	private int subjectUnit; // Số đơn vị học trình

	public Subject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Subject(String subjectId, String subjectName, int subjectUnit) {
		super();
		this.subjectId = subjectId;
		this.subjectName = subjectName;
		this.subjectUnit = subjectUnit;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public int getSubjectUnit() {
		return subjectUnit;
	}

	public void setSubjectUnit(int subjectUnit) {
		this.subjectUnit = subjectUnit;
	}

	@Override
	public String toString() {
		return "Subject [subjectId=" + subjectId + ", subjectName=" + subjectName + ", subjectUnit=" + subjectUnit
				+ "]";
	}

}
