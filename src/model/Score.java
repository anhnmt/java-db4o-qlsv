package model;

public class Score {
	private String studentId; // Mã sinh viên
	private String subjectId; // Mã môn học
	private float score; // Điểm thi
	private int examTime; // Số lần thi

	public Score() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Score(String studentId, String subjectId, float score, int examTime) {
		super();
		this.studentId = studentId;
		this.subjectId = subjectId;
		this.score = score;
		this.examTime = examTime;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public int getExamTime() {
		return examTime;
	}

	public void setExamTime(int examTime) {
		this.examTime = examTime;
	}

	@Override
	public String toString() {
		return "Score [studentId=" + studentId + ", subjectId=" + subjectId + ", score=" + score + ", examTime="
				+ examTime + "]";
	}

}
