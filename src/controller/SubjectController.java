package controller;

import java.util.List;
import java.util.Scanner;

import com.db4o.ObjectSet;

import model.Subject;
import qlsv.DB;

public class SubjectController {
	private static Scanner sc = new Scanner(System.in);
	private static DB DB = new DB();

	public SubjectController() {
		int action;

		do {
			System.out.println("+-------+-----------------------+");
			System.out.println("|  STT  | QUAN LY MON HOC       |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   1   | Them mon hoc          |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   2   | In danh sach          |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   3   | Sua mon hoc           |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   4   | Xoa mon hoc           |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   5   | Tim kiem mon hoc      |");
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

	private List<Subject> query() {
		List<Subject> list = DB.container.queryByExample(Subject.class);
		return list;
	}

	private void create() {
		DB.begin();
		int n;

		System.out.println("+-------------- THEM MON HOC --------------+");

		try {
			System.out.println("- Nhap so mon hoc can them: ");
			sc.nextLine();
			n = Integer.parseInt(sc.nextLine());

			for (int i = 0; i < n; i++) {
				Subject obj = new Subject();

				System.out.println("Nhap thong tin mon hoc: ");

				do {
					System.out.println("- Ma mon hoc: ");

					try {
						String subjectId = sc.nextLine();

						if (subjectId.length() < 1 || subjectId.equals("")) {
							System.out.println("- Ma mon hoc khong duoc de trong");
						} else if (query().stream()
								.anyMatch(o -> o.getSubjectId().compareToIgnoreCase(subjectId) == 0)) {
							System.out.println("- Ma mon hoc da ton tai, vui long chon lai");
						} else {
							obj.setSubjectId(subjectId.toUpperCase());
							break;
						}
					} catch (Exception e) {
						System.out.println("- Ma mon hoc phai la kieu chuoi");
					}
				} while (true);

				do {
					System.out.println("- Ten mon hoc: ");

					try {
						String subjectName = sc.nextLine();
						if (subjectName.length() < 1 || subjectName.equals("")) {
							System.out.println("- Ten mon hoc khong duoc de trong");
						} else {
							obj.setSubjectName(subjectName.toUpperCase());
							break;
						}
					} catch (Exception e) {
						System.out.println("- Ten mon hoc phai la kieu chuoi");
					}
				} while (true);

				do {
					System.out.println("- So don vi hoc trinh: ");

					try {
						int subjectUnit = Integer.parseInt(sc.nextLine());
						if (subjectUnit < 1) {
							System.out.println("- So don vi hoc trinh khong duoc nho hon 1");
						} else {
							obj.setSubjectUnit(subjectUnit);
							break;
						}
					} catch (Exception e) {
						System.out.println("- So don vi hoc trinh phai la kieu so");
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
		System.out.println("+------------ DANH SACH MON HOC ------------+");
		System.out.println("+--------+-------------+------+");
		System.out.println("|  ID    | TEN MON HOC | DVHT |");
		System.out.println("+--------+-------------+------+");

		query().stream().sorted((o1, o2) -> o1.getSubjectId().compareTo(o2.getSubjectId())).forEach(obj -> {
			System.out.printf("| %-6s | %-11s | %-4d |\n", obj.getSubjectId(), obj.getSubjectName(),
					obj.getSubjectUnit());
			System.out.println("+--------+-------------+------+");
		});
	}

	private void update() {
		try {
			Subject obj = new Subject();

			System.out.println("+------------ CAP NHAT MON HOC ------------+");
			System.out.println("- Nhap ma mon hoc: ");
			sc.nextLine();
			String subjectId = sc.nextLine();
			obj.setSubjectId(subjectId.toUpperCase());

			ObjectSet<?> result = DB.container.queryByExample(obj);

			if (result.size() > 0) {
				Subject presult = (Subject) result.next();
				System.out.println("- Nhap ten mon hoc can sua: ");
				String subjectName = sc.nextLine();
				presult.setSubjectName(subjectName.toUpperCase());
				System.out.println("- Nhap so don vi hoc trinh: ");
				int subjectUnit = Integer.parseInt(sc.nextLine());
				presult.setSubjectUnit(subjectUnit);

				DB.store(presult);
				System.out.println("- Sua mon hoc thanh cong");
			} else {
				System.out.println("- Ma mon hoc khong ton tai");
			}

			DB.commit();
		} catch (Exception e) {
			DB.rollback();
		}
	}

	private void delete() {
		try {
			Subject obj = new Subject();

			System.out.println("+------------ XOA MON HOC ------------+");
			System.out.println("- Nhap ma mon hoc: ");
			sc.nextLine();
			String subjectId = sc.nextLine();
			obj.setSubjectId(subjectId.toUpperCase());

			ObjectSet<?> result = DB.container.queryByExample(obj);

			if (result.size() > 0) {
				Subject presult = (Subject) result.next();
				DB.delete(presult);

				System.out.println("- Xoa mon hoc thanh cong");
			} else {
				System.out.println("- Ma mon hoc khong ton tai, vui long chon lai");
			}

			DB.commit();
		} catch (Exception e) {
			DB.rollback();
		}
	}

	private void search() {
		System.out.println("+------------ TIM KIEM MON HOC ------------+");
		System.out.println("- Nhap ma mon hoc can tim: ");
		sc.nextLine();
		String subjectId = sc.nextLine();

		System.out.println("+--------+-------------+------+");
		System.out.println("|  ID    | TEN MON HOC | DVHT |");
		System.out.println("+--------+-------------+------+");

		query().stream().filter(o -> {
			return o.getSubjectId().contains(subjectId.toUpperCase());
		}).forEach(obj -> {
			System.out.printf("| %-6s | %-11s | %-4d |\n", obj.getSubjectId(), obj.getSubjectName(),
					obj.getSubjectUnit());
			System.out.println("+--------+-------------+------+");
		});
	}
}
