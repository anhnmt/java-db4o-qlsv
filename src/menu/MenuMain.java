package menu;

import java.io.IOException;
import java.util.Scanner;

public class MenuMain extends Menu {

	public MenuMain() {
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
			action = sc.nextInt();

			switch (action) {
			case 1:
				new MenuStudent().menu();
				break;
			case 2:
				new MenuClass().menu();
				break;
			case 3:
				new MenuSubject().menu();
				break;
			case 4:
				new MenuScore().menu();
				break;
			}
		} while (action != 0);

		System.out.println("Cam on ban da su dung chuong trinh!");
	}
}
