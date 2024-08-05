package bitcamp.myapp;

import bitcamp.command.AnsiColors;
import bitcamp.context.ApplicationContext;
import bitcamp.listener.ApplicationListener;
import bitcamp.myapp.command.PlayMultiGameCommand;
import bitcamp.myapp.command.PlaySingleGameCommand;
import bitcamp.myapp.listener.InitApplicationListener;
import bitcamp.util.Prompt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientApp {
	public static String SERVER_ADDRESS = "";
	public static final int MIN_NUMBER = 1;
	public static final int MAX_NUMBER = 3;
	public static int SERVER_PORT = 8888;
	public  static Scanner scanner = new Scanner(System.in);

	List<ApplicationListener> listeners = new ArrayList<>();
	ApplicationContext appCtx = new ApplicationContext();

	public static void main(String[] args) {

		ClientApp app = new ClientApp();

		app.addApplicationListener(new InitApplicationListener());

		app.execute();
	}

	// 애플리케이션 리스너를 리스트에 추가하는 메서드
	private void addApplicationListener(ApplicationListener listener) {
		listeners.add(listener);
	}
	// 애플리케이션 리스너를 리스트에서 제거하는 메서드
	private void removeApplicationListener(ApplicationListener listener) {
		listeners.remove(listener);
	}

	void execute(){

		try{
			// 애플리케이션이 시작될 때 리스너에게 알린다.
			for (ApplicationListener listener : listeners) {
				try {
					listener.onStart(appCtx);
				} catch (Exception e) {
					System.out.println(AnsiColors.RED + "리스너 실행 중 오류 발생!" + AnsiColors.RESET);
				}
			}

			printBanner();

			System.out.println("1. 혼자서 게임하기");
			System.out.println("2. 멀티게임하기");

			String choice = scanner.nextLine().trim();
			switch (choice) {
				case "1":
					PlaySingleGameCommand.execute();
					break;
				case "2":
					PlayMultiGameCommand.playMultiplayer();
					break;
				default:
					System.out.println("Invalid choice.");
					break;
			}
		}catch (Exception ex){
			System.out.println(AnsiColors.RED + "실행 오류!" + AnsiColors.RESET);
			ex.printStackTrace();
		}

		System.out.println(AnsiColors.GREEN + "종료합니다." + AnsiColors.RESET);

		Prompt.close();

		// 애플리케이션이 종료될 때 리스너에게 알린다.
		for (ApplicationListener listener : listeners) {
			try {
				listener.onShutdown(appCtx);
			} catch (Exception e) {
				System.out.println(AnsiColors.RED + "리스너 실행 중 오류 발생!" + AnsiColors.RESET);
			}
		}
	}

	private void printBanner() {
		String banner = AnsiColors.CYAN +
				"==========================================\n" +
				"              베스킨라빈스31 게임          \n" +
				"==========================================\n" + AnsiColors.RESET;
		System.out.print(banner);
	}
}
