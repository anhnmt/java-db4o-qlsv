package controller;

import java.util.List;
import java.util.Scanner;

import com.db4o.ObjectSet;

import model.Class;
import qlsv.DB;

public class ClassController {
	private static Scanner sc = new Scanner(System.in);
	private static DB DB = new DB();

	public ClassController() {
		super();

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

	private List<Class> query() {
		List<Class> list = DB.container.queryByExample(Class.class);
		return list;
	}

	private void create() {
		DB.begin();
		int n;

		System.out.println("+-------------- THEM LOP HOC --------------+");

		try {
			System.out.println("- Nhap so lop hoc can them: ");
			sc.nextLine();
			n = Integer.parseInt(sc.nextLine());

			for (int i = 0; i < n; i++) {
				Class obj = new Class();

				System.out.println("Nhap thong tin lop hoc: ");

				do {
					System.out.println("- Ma lop hoc: ");

					try {
						String classId = sc.nextLine();

						if (classId.length() < 1 || classId.equals("")) {
							System.out.println("- Ma lop hoc khong duoc de trong");
						} else if (query().stream().anyMatch(o -> o.getClassId().compareToIgnoreCase(classId) == 0)) {
							System.out.println("- Ma lop hoc da ton tai, vui long chon lai");
						} else {
							obj.setClassId(classId.toUpperCase());
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
							obj.setClassName(className.toUpperCase());
							break;
						}
					} catch (Exception e) {
						System.out.println("- Ten lop hoc phai la kieu chuoi");
					}
				} while (true);

				DB.store(obj); // luu lop hoc vao database
			}

			DB.commit();
		} catch (Exception e) {
			DB.rollback();
		}

	}

	private void read() {
		System.out.println("+------------ DANH SACH LOP HOC ------------+");
		System.out.println("+--------+-------------+");
		System.out.println("|  ID    | TEN LOP HOC |");
		System.out.println("+--------+-------------+");

		query().stream().sorted((o1, o2) -> o1.getClassId().compareTo(o2.getClassId())).forEach(obj -> {
			System.out.printf("| %-6s | %-11s |\n", obj.getClassId(), obj.getClassName());
			System.out.println("+--------+-------------+");
		});
	}

	private void update() {
		try {
			Class obj = new Class();

			System.out.println("+------------ CAP NHAT LOP HOC ------------+");
			System.out.println("- Nhap ma lop hoc: ");
			sc.nextLine();
			String classId = sc.nextLine();
			obj.setClassId(classId.toUpperCase());

			ObjectSet<?> result = DB.container.queryByExample(obj);

			if (result.size() > 0) {
				Class presult = (Class) result.next();
				System.out.println("- Nhap ten lop hoc can sua: ");
				String className = sc.nextLine();
				presult.setClassName(className.toUpperCase());
				DB.store(presult);
				System.out.println("Sua lop hoc thanh cong");
			} else {
				System.out.println("- Ma lop hoc khong ton tai");
			}

			DB.commit();
		} catch (Exception e) {
			DB.rollback();
		}
	}

	private void delete() {
		try {
			Class obj = new Class();

			System.out.println("+------------ XOA LOP HOC ------------+");
			System.out.println("- Nhap ma lop hoc: ");
			sc.nextLine();
			String classId = sc.nextLine();
			obj.setClassId(classId.toUpperCase());

			ObjectSet<?> result = DB.container.queryByExample(obj);

			if (result.size() > 0) {
				Class presult = (Class) result.next();
				DB.delete(presult);
				System.out.println("Xoa lop hoc thanh cong");
			} else {
				System.out.println("- Ma lop hoc khong ton tai, vui long chon lai");
			}

			DB.commit();
		} catch (Exception e) {
			DB.rollback();
		}
	}

	private void search() {
		System.out.println("+------------ TIM KIEM LOP HOC ------------+");
		System.out.println("- Nhap ma lop hoc can tim: ");
		sc.nextLine();
		String classId = sc.nextLine();

		System.out.println("+--------+-------------+");
		System.out.println("|  ID    | TEN LOP HOC |");
		System.out.println("+--------+-------------+");

		query().stream().filter(o -> {
			return o.getClassId().contains(classId.toUpperCase());
		}).forEach(obj -> {
			System.out.printf("| %-6s | %-11s |\n", obj.getClassId(), obj.getClassName());
			System.out.println("+--------+-------------+");
		});
	}
}
