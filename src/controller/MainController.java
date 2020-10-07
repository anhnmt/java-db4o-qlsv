package controller;

import java.util.Scanner;

public class MainController {

	public MainController() {
		super();

		Scanner sc = new Scanner(System.in);
		int action;

		do {
			System.out.println("+-------+-----------------------+");
			System.out.println("|  STT  | MENU CHUONG TRINH     |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   1   | Quan ly sinh vien     |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   2   | Quan ly lop hoc       |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   3   | Quan ly mon hoc       |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   4   | Quan ly bang diem     |");
			System.out.println("+-------+-----------------------+");
			System.out.println("|   0   | Thoat chuong trinh    |");
			System.out.println("+-------+-----------------------+");
			System.out.print("- Vui long chon 1-2-3-4-0: ");
			action = Integer.parseInt(sc.nextLine());

			switch (action) {
			case 1:
				new StudentController();
				break;
			case 2:
				new ClassController();
				break;
			case 3:
				new SubjectController();
				break;
			case 4:
				new ScoreController();
				break;
			}
		} while (action != 0);

		System.out.println("Cam on ban da su dung chuong trinh!");
		sc.close();
	}
}
