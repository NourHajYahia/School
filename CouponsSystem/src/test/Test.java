package test;

import java.util.Scanner;

import main.core.db.ConnectionPool;
import main.core.exceptions.CouponSystemExceprion;
import main.core.exceptions.LoginManagerException;
import main.core.facade.ClientFacade;
import main.core.loginManager.ClientType;
import main.core.loginManager.LoginManager;
import main.core.threadJob.ExpiredCouponsClean;
import test.createDB.InitializeDB;
import test.facade.AdminFacadeTest;
import test.facade.FacadeTest;

public class Test {

	private Scanner scan = new Scanner(System.in);
	

	public void testAll() {

 		InitializeDB.start();
		boolean systemOn = true;
		ExpiredCouponsClean expiredCouponsCleaner = null;
		try {
			ConnectionPool.getInstance();
			expiredCouponsCleaner = new ExpiredCouponsClean();
			while (systemOn) {
				logInMenu();
				String input = scan.nextLine();

				FacadeTest facadeTest = null;
				ClientFacade clientFacade = null;
				
				switch (input) {
				case "admin":
				case "1":
					clientFacade = logIn(ClientType.ADMINISTRATOR);
					if (clientFacade != null) {
						facadeTest = new AdminFacadeTest(clientFacade);
					}
					break;

				case "company":
				case "2":
					clientFacade = logIn(ClientType.COMPANY);
					if (clientFacade != null) {
						facadeTest = new AdminFacadeTest(clientFacade);
					}
					break;

				case "customer":
				case "3":
					clientFacade = logIn(ClientType.CUSTOMER);
					if (clientFacade != null) {
						facadeTest = new AdminFacadeTest(clientFacade);
					}
					break;

				case "quit":
				case "q":
					systemOn = false;
					break;

				default:
					System.out.println("Wrong value, please enter one of the choices above");
					break;
				}

				if (facadeTest != null)
					facadeTest.runTest();

				System.out.println(
						"___________________________________________________________________________________________________________________________________________________");
			}
		} catch (CouponSystemExceprion e) {
			System.out.println(e.getMessage());
		}finally {
			expiredCouponsCleaner.stop();
			scan.close();
		}

	}

	private void logInMenu() {
		System.out.println("______________________ LogIn Test __________________________");
		System.out.println(
				"Admin ........................................................................................................................................................ 1 / admin");
		System.out.println(
				"Company client ..................................................................................................................................... 2 / company");
		System.out.println(
				"Customer client .................................................................................................................................... 3 / customer");
		System.out.println(
				"Quit ............................................................................................................................................................ q / quit");
		System.out.print("Please choose your client type: ");
	}

	private ClientFacade logIn(ClientType clientType) {

		System.out.print("Email: ");
		String email = scan.nextLine();
		System.out.print("Password: ");
		String password = scan.nextLine();
		try {
			return LoginManager.getInstance().login(email, password, clientType);
		} catch (LoginManagerException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

}
