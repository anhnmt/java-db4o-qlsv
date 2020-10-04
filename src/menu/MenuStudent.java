package menu;

import java.util.List;
import java.util.Scanner;

import qlsv.DB;
import model.Student;
import model.Class;
import com.db4o.query.Query;

public class MenuStudent extends Menu {
	private static Scanner sc = new Scanner(System.in);
	private static DB DB = new DB();

	public void menu() {
		clrscr();

		int action;

		do {
			System.out.println("+-------+-----------------------+");
			System.out.println("|  STT  | QUAN LY SINH VIEN     |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   1   | Them sinh vien        |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   2   | Sua sinh vien         |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   3   | Xoa sinh vien         |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   4   | In danh sach          |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   5   | Tim kiem sinh vien    |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   0   | Quay lai Menu chinh   |");
			System.out.println("+-------+-----------------------+");
			System.out.print("- Vui long chon 1-2-3-4-5-0: ");
			action = sc.nextInt();

			switch (action) {
			case 1:
				create();
				break;
			case 2:
				clrscr();
				read();
				break;
			case 3:
				update();
				break;
			case 4:
				clrscr();
				read();
				break;
			case 5:
				search();
				break;
			}
		} while (action != 0);
	}

	private static void create() {
		DB.beginTransaction();
		int n;

		System.out.println("+-------------- THEM SINH VIEN --------------+");

		try {
			System.out.print("- Nhap so sinh vien can them: ");
			n = sc.nextInt();

			for (int i = 0; i < n; i++) {
				Student sv = new Student();
//				sv.input();
				System.out.println("Nhap thong tin sinh vien: ");

				do {
					System.out.println("- Ma sinh vien: ");

					try {
						String studentId = sc.nextLine();
						if (studentId.length() < 1 || studentId.equals("")) {
							System.out.println("- Ma sinh vien khong duoc de trong");
						} else {
							sv.setStudentId(studentId);
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
							sv.setStudentName(studentName);
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
						String studentClass = sc.nextLine();

						Query query = DB.query();
						query.constrain(Class.class);
//						query.descend("_giaovien").descend("_ten").constrain(studentClass);
						List<Class> listClass = DB.execute(query);

						if (studentClass.length() < 1 || studentClass.equals("")) {
							System.out.println("- Ma lop hoc khong duoc de trong");
						} else if (listClass.stream()
								.anyMatch(obj -> obj.getClassId().compareToIgnoreCase(studentClass) == 0)) {
							System.out.println("- Ma lop hoc da ton tai, vui long chon lai");
						} else {
							sv.setStudentClass(studentClass);
							break;
						}
					} catch (Exception e) {
						System.out.println("- Ma lop hoc phai la kieu chuoi");
					}
				} while (true);

				DB.store(sv); // luu sinh vien vao database
			}

			DB.commitTransaction();
		} catch (Exception e) {
			DB.rollbackTransaction();
		}
	}

	private static void read() {
		List<Student> list = DB.getClass(Student.class);

		System.out.println("+------------ DANH SACH SINH VIEN ------------+");
		System.out.println("+-------+------------+-----------+------------+");
		System.out.println("|  ID   | TEN        | GIOI TINH | LOP HOC    |");
		System.out.println("+-------+------------+-----------+------------+");
		for (Student sv : list) {
			System.out.printf("| %-5s | %-10s | %-9s | %-10s |\n", sv.getStudentId(), sv.getStudentName(),
					sv.getStudentGender() ? "Nam" : "Nu", sv.getStudentClass());
			System.out.println("+-------+------------+-----------+------------+");
		}
	}

	private void update() {
		// TODO Auto-generated method stub

	}

	private void search() {
		// TODO Auto-generated method stub

	}
}
