package controller;

import java.util.List;
import java.util.Scanner;

import com.db4o.ObjectSet;

import model.Score;
import model.Subject;
import model.Student;
import qlsv.DB;

public class ScoreController {
	private static Scanner sc = new Scanner(System.in);
	private static DB DB = new DB();

	public ScoreController() {
		int action;

		try {
			do {
				System.out.println("+-------+-----------------------+");
				System.out.println("|  STT  | QUAN LY BANG DIEM     |");
				System.out.println("+-------+-----------------------+");
				System.out.println("|   1   | Them bang diem        |");
				System.out.println("+-------+-----------------------+");
				System.out.println("|   2   | In danh sach          |");
				System.out.println("+-------+-----------------------+");
				System.out.println("|   3   | Sua bang diem         |");
				System.out.println("+-------+-----------------------+");
				System.out.println("|   4   | Xoa bang diem         |");
				System.out.println("+-------+-----------------------+");
				System.out.println("|   5   | Tim kiem bang diem    |");
				System.out.println("+-------+-----------------------+");
				System.out.println("|   0   | Quay lai Menu chinh   |");
				System.out.println("+-------+-----------------------+");
				System.out.print("- Vui long chon 1-2-3-4-5-0: ");
				action = Integer.parseInt(sc.nextLine());

				switch (action) {
				case 1:
					create();
					break;
				case 2:
					read();
					break;
				case 3:
					update();
					break;
				case 4:
					delete();
					break;
				case 5:
					search();
					break;
				}
			} while (action != 0);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private List<Score> query() {
		List<Score> list = DB.container.queryByExample(Score.class);
		return list;
	}

	private void create() {
		DB.begin();
		int n;

		System.out.println("+-------------- THEM BANG DIEM --------------+");

		try {
			System.out.println("- Nhap so bang diem can them: ");
			n = Integer.parseInt(sc.nextLine());

			for (int i = 0; i < n; i++) {
				Score obj = new Score();

				System.out.println("Nhap thong tin bang diem: ");

				do {
					System.out.println("- Ma sinh vien: ");

					try {
						String studentId = sc.nextLine();
						if (studentId.length() < 1 || studentId.equals("")) {
							System.out.println("- Ma sinh vien khong duoc de trong");
						} else if (query().stream()
								.anyMatch(o -> o.getStudentId().compareToIgnoreCase(studentId) < 0)) {
							System.out.println("- Ma sinh vien khong ton tai, vui long chon lai");
						} else {
							obj.setStudentId(studentId.toUpperCase());
							break;
						}
					} catch (Exception e) {
						System.out.println("- Ma sinh vien phai la kieu chuoi");
					}
				} while (true);

				do {
					System.out.println("- Ma mon hoc: ");

					try {
						String subjectId = sc.nextLine();

						if (subjectId.length() < 1 || subjectId.equals("")) {
							System.out.println("- Ma mon hoc khong duoc de trong");
						} else if (query().stream()
								.anyMatch(o -> o.getSubjectId().compareToIgnoreCase(subjectId) < 0)) {
							System.out.println("- Ma mon hoc khong ton tai, vui long chon lai");
						} else {
							obj.setSubjectId(subjectId.toUpperCase());
							break;
						}
					} catch (Exception e) {
						System.out.println("- Ma mon hoc phai la kieu chuoi");
					}
				} while (true);

				do {
					System.out.println("- Diem thi: ");

					try {
						float score = Float.parseFloat(sc.nextLine());
						if (score < 0 || score > 10) {
							System.out.println("- Diem thi khong phai tu 0 - 10");
						} else {
							obj.setScore(score);
							break;
						}
					} catch (Exception e) {
						System.out.println("- Diem thi phai la kieu so");
					}
				} while (true);

				do {
					System.out.println("- So lan thi: ");

					try {
						int examTime = Integer.parseInt(sc.nextLine());
						if (examTime == 1 || examTime == 2) {
							obj.setExamTime(examTime);
							break;
						} else {
							System.out.println("- Diem thi phai la 1 hoac 2");
						}
					} catch (Exception e) {
						System.out.println("- So lan thi phai la kieu so");
					}
				} while (true);

				DB.store(obj); // luu mon hoc vao database
			}

			DB.commit();
		} catch (Exception e) {
			DB.rollback();
		}
	}

	private void read() {
		System.out.println("+------------ DANH SACH BANG DIEM ------------+");
		System.out.println("+-------+--------+---------+--------|");
		System.out.println("| MA SV | MA MON | DIEMTHI | LANTHI |");
		System.out.println("+-------+--------+---------+--------|");

		query().stream().sorted((o1, o2) -> o1.getStudentId().compareTo(o2.getStudentId())).forEach(obj -> {
			System.out.printf("| %-5s | %-6s | %-7.4f | %-6d |\n", obj.getStudentId(), obj.getSubjectId(),
					obj.getScore(), obj.getExamTime());
			System.out.println("+-------+--------+---------+--------|");
		});
	}

	private void update() {
		try {
			Score obj = new Score();

			System.out.println("+------------ CAP NHAT BANG DIEM ------------+");
			System.out.println("- Nhap ma sinh vien: ");
			sc.nextLine();
			String studentId = sc.nextLine();
			obj.setStudentId(studentId.toUpperCase());

			ObjectSet<?> result = DB.container.queryByExample(obj);

			if (result.size() > 0) {
				Score presult = (Score) result.next();
				System.out.println("- Nhap diem thi: ");
				float score = Float.parseFloat(sc.nextLine());
				presult.setScore(score);

				System.out.println("- Nhap so lan thi: ");
				int examTime = Integer.parseInt(sc.nextLine());
				presult.setExamTime(examTime);

				DB.store(presult);
				System.out.println("- Sua bang diem thanh cong");
			} else {
				System.out.println("- Ma sinh vien khong ton tai");
			}

			DB.commit();
		} catch (Exception e) {
			DB.rollback();
		}
	}

	private void delete() {
		try {
			Subject obj = new Subject();

			System.out.println("+------------ XOA BANG DIEM ------------+");
			System.out.println("- Nhap ma sinh vien: ");
			System.out.flush();
			String subjectId = sc.nextLine();
			obj.setSubjectId(subjectId.toUpperCase());

			ObjectSet<?> result = DB.container.queryByExample(obj);

			if (result.size() > 0) {
				Subject presult = (Subject) result.next();
				DB.delete(presult);

				System.out.println("- Xoa bang diem thanh cong");
			} else {
				System.out.println("- Ma sinh vien khong ton tai, vui long chon lai");
			}

			DB.commit();
		} catch (Exception e) {
			DB.rollback();
		}
	}

	private void search() {
		System.out.println("+------------ TIM KIEM BANG DIEM ------------+");
		System.out.println("- Nhap ma vinh vien can tim: ");
		System.out.flush();
		String subjectId = sc.nextLine();

		System.out.println("+-------+--------+---------+--------|");
		System.out.println("| MA SV | MA MON | DIEMTHI | LANTHI |");
		System.out.println("+-------+--------+---------+--------|");

		query().stream().filter(o -> {
			return o.getSubjectId().contains(subjectId.toUpperCase());
		}).forEach(obj -> {
			System.out.printf("| %-5s | %-6s | %-7.4f | %-6d |\n", obj.getStudentId(), obj.getSubjectId(),
					obj.getScore(), obj.getExamTime());
			System.out.println("+-------+--------+---------+--------|");
		});
	}
}
