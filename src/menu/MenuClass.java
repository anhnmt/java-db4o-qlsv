package menu;

import java.util.List;
import java.util.Scanner;

import com.db4o.query.Query;

import model.Class;
import qlsv.DB;

public class MenuClass {
	private static Scanner sc = new Scanner(System.in);
	private static DB DB = new DB();

	private List<Class> query() {
		Query query = DB.query();
		query.constrain(Class.class);
//		query.descend("_giaovien").descend("_ten").constrain(studentClass);
		List<Class> listClass = DB.execute(query);
		return listClass;
	}

	public void menu() {
		clrscr();

		int action;

		do {
			System.out.println("+-------+-----------------------+");
			System.out.println("|  STT  | QUAN LY LOP HOC       |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   1   | Them lop hoc          |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   2   | In danh sach          |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   3   | Sua lop hoc           |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   4   | Xoa lop hoc           |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   5   | Tim kiem lop hoc      |");
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
				delete();
				break;
			case 5:
				search();
				break;
			}
		} while (action != 0);
	}

	private void create() {
		DB.beginTransaction();
		int n;

		System.out.println("+-------------- THEM LOP HOC --------------+");

		try {
			System.out.println("- Nhap so lop hoc can them: ");
			sc.nextLine();
			n = Integer.parseInt(sc.nextLine());

			for (int i = 0; i < n; i++) {
				Class lh = new Class();

				System.out.println("Nhap thong tin lop hoc: ");

				do {
					System.out.println("- Ma lop hoc: ");

					try {
//						sc.nextLine();
						String classId = sc.nextLine();

						if (classId.length() < 1 || classId.equals("")) {
							System.out.println("- Ma lop hoc khong duoc de trong");
						} else if (query().stream()
								.anyMatch(obj -> obj.getClassId().compareToIgnoreCase(classId) == 0)) {
							System.out.println("- Ma lop hoc da ton tai, vui long chon lai");
						} else {
							lh.setClassId(classId);
							break;
						}
					} catch (Exception e) {
						System.out.println("- Ma lop hoc phai la kieu chuoi");
					}
				} while (true);

				do {
					System.out.println("- Ten lop hoc: ");

					try {
						String className = sc.nextLine();
						if (className.length() < 1 || className.equals("")) {
							System.out.println("- Ten lop hoc khong duoc de trong");
						} else {
							lh.setClassName(className);
							break;
						}
					} catch (Exception e) {
						System.out.println("- Ten lop hoc phai la kieu chuoi");
					}
				} while (true);

				DB.store(lh); // luu lop hoc vao database
			}

			DB.commitTransaction();
		} catch (Exception e) {
			DB.rollbackTransaction();
		}

	}

	private void read() {
		List<Class> list = DB.getClass(Class.class);

		System.out.println("+------------ DANH SACH LOP HOC ------------+");
		System.out.println("+--------+-------------+");
		System.out.println("|  ID    | TEN LOP HOC |");
		System.out.println("+--------+-------------+");
		for (Class lh : list) {
			System.out.printf("| %-6s | %-11s |\n", lh.getClassId(), lh.getClassName());
			System.out.println("+--------+-------------+");
		}
	}

	private void update() {
		// TODO Auto-generated method stub

	}

	private void delete() {
		// TODO Auto-generated method stub

	}

	private void search() {
		System.out.println("+------------ TIM KIEM LOP HOC ------------+");
		System.out.println("- Nhap ma lop hoc can tim: ");
		sc.nextLine();
		String classId = sc.nextLine();

		System.out.println("+--------+-------------+");
		System.out.println("|  ID    | TEN LOP HOC |");
		System.out.println("+--------+-------------+");

		query().stream().filter(obj -> {
			return obj.getClassId().compareToIgnoreCase(classId) == 0;
		}).forEach(lh -> {
			System.out.printf("| %-6s | %-11s |\n", lh.getClassId(), lh.getClassName());
			System.out.println("+--------+-------------+");
		});
	}

	public static void clrscr() {
		// Clears Screen in java
		for (int i = 0; i < 50; ++i)
			System.out.println();
	}
}
