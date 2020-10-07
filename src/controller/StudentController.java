package controller;

import java.util.List;
import java.util.Scanner;

import qlsv.DB;
import model.Student;
import model.Class;

import com.db4o.ObjectSet;

public class StudentController {
	private static Scanner sc = new Scanner(System.in);
	private static DB DB = new DB();

	public StudentController() {
		int action;

		do {
			System.out.println("+-------+-----------------------+");
			System.out.println("|  STT  | QUAN LY SINH VIEN     |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   1   | Them sinh vien        |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   2   | In danh sach          |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   3   | Sua sinh vien         |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   4   | Xoa sinh vien         |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   5   | Tim kiem sinh vien    |");
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
	}

	private List<Student> query() {
		List<Student> list = DB.container.queryByExample(Student.class);
		return list;
	}

	private void create() {
		DB.begin();
		int n;

		System.out.println("+-------------- THEM SINH VIEN --------------+");

		try {
			System.out.print("- Nhap so sinh vien can them: ");
			n = Integer.parseInt(sc.nextLine());

			for (int i = 0; i < n; i++) {
				Student sv = new Student();

				System.out.println("Nhap thong tin sinh vien: ");

				do {
					System.out.println("- Ma sinh vien: ");

					try {
						String studentId = sc.nextLine();
						if (studentId.length() < 1 || studentId.equals("")) {
							System.out.println("- Ma sinh vien khong duoc de trong");
						} else if (query().stream()
								.anyMatch(o -> o.getStudentId().compareToIgnoreCase(studentId) == 0)) {
							System.out.println("- Ma sinh vien da ton tai, vui long chon lai");
						} else {
							sv.setStudentId(studentId.toUpperCase());
							break;
						}
					} catch (Exception e) {
						System.out.println("- Ma sinh vien phai la kieu chuoi");
					}
				} while (true);

				do {
					System.out.println("- Ten sinh vien: ");

					try {
						String studentName = sc.nextLine();
						if (studentName.length() < 1 || studentName.equals("")) {
							System.out.println("- Ten sinh vien khong duoc de trong");
						} else {
							sv.setStudentName(studentName.toUpperCase());
							break;
						}
					} catch (Exception e) {
						System.out.println("- Ten sinh vien phai la kieu chuoi");
					}
				} while (true);

				do {
					System.out.println("- Gioi tinh sinh vien: ");
					String strGender = sc.nextLine();

					if (strGender.equals("true") || strGender.equals("false")) {
						Boolean studentGender = Boolean.parseBoolean(strGender);
						sv.setStudentGender(studentGender);
						break;
					} else {
						System.out.println("- Gioi tinh sinh vien chi nhap true/false");
					}
				} while (true);

				do {
					System.out.println("- Ma lop hoc: ");

					try {
						String classId = sc.nextLine();

						if (classId.length() < 1 || classId.equals("")) {
							System.out.println("- Ma lop hoc khong duoc de trong");
						} else {
							sv.setClassId(classId.toUpperCase());
							break;
						}
					} catch (Exception e) {
						System.out.println("- Ma lop hoc phai la kieu chuoi");
					}
				} while (true);

				DB.store(sv); // luu sinh vien vao database
			}

			DB.commit();
		} catch (Exception e) {
			DB.rollback();
		}
	}

	private void read() {
		System.out.println("+------------ DANH SACH SINH VIEN ------------+");
		System.out.println("+-------+------------+-----------+------------+");
		System.out.println("|  ID   | TEN        | GIOI TINH | LOP HOC    |");
		System.out.println("+-------+------------+-----------+------------+");
		query().stream().sorted((o1, o2) -> o1.getStudentId().compareTo(o2.getStudentId())).forEach(obj -> {
			System.out.printf("| %-5s | %-10s | %-9s | %-10s |\n", obj.getStudentId(), obj.getStudentName(),
					obj.getStudentGender() ? "NAM" : "NU", obj.getClassId());
			System.out.println("+-------+------------+-----------+------------+");
		});
	}

	private void update() {

		try {
			Class obj = new Class();

			System.out.println("+------------ CAP NHAT SINH VIEN ------------+");
			System.out.println("- Nhap ma sinh vien: ");
			sc.nextLine();
			String classId = sc.nextLine();
			obj.setClassId(classId.toUpperCase());

			ObjectSet<?> result = DB.container.queryByExample(obj);

			if (result.size() > 0) {
				Class presult = (Class) result.next();
				System.out.println("- Nhap ten sinh vien can sua: ");
				String className = sc.nextLine();
				presult.setClassName(className.toUpperCase());
				DB.store(presult);

				System.out.println("Sua sinh vien thanh cong");
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
			Student obj = new Student();

			System.out.println("+------------ XOA SINH VIEN ------------+");
			System.out.println("- Nhap ma sinh vien: ");
			String studentId = sc.nextLine();
			obj.setStudentId(studentId.toUpperCase());

			ObjectSet<?> result = DB.container.queryByExample(obj);

			if (result.size() > 0) {
				Student presult = (Student) result.next();
				DB.delete(presult);

				System.out.println("Xoa sinh vien thanh cong");
			} else {
				System.out.println("- Ma sinh vien khong ton tai, vui long chon lai");
			}

			DB.commit();
		} catch (Exception e) {
			DB.rollback();
		}
	}

	private void search() {
		System.out.println("+------------ TIM KIEM SINH VIEN ------------+");
		System.out.println("- Nhap ma sinh vien can tim: ");
		String classId = sc.nextLine();

		System.out.println("+-------+------------+-----------+------------+");
		System.out.println("|  ID   | TEN        | GIOI TINH | LOP HOC    |");
		System.out.println("+-------+------------+-----------+------------+");

		query().stream().filter(o -> {
			return o.getClassId().contains(classId.toUpperCase());
		}).forEach(obj -> {
			System.out.printf("| %-5s | %-10s | %-9s | %-10s |\n", obj.getStudentId(), obj.getStudentName(),
					obj.getStudentGender() ? "Nam" : "Nu", obj.getClassId());
			System.out.println("+-------+------------+-----------+------------+");
		});
	}
}
