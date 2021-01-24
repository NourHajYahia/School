package test;

import java.time.LocalDateTime;
import java.util.Scanner;

import main.core.exceptions.CouponSystemExceprion;
import main.core.exceptions.LoginManagerException;
import main.core.facade.ClientFacade;
import main.core.loginManager.ClientType;
import main.core.loginManager.LoginManager;
import main.threadJob.ExpiredCouponsClean;
import test.facade.AdminFacadeTest;
import test.facade.CompanyFacadeTest;
import test.facade.CustomerFacadeTest;
import test.facade.FacadeTest;

public class Test {
	
	private Scanner scan = new Scanner(System.in);
	private LoginManager login = LoginManager.getInstance();
	
	public void testAll() {
		boolean systemOn = true;
		
		ExpiredCouponsClean expiredCouponsCleaner = null;

		try  {
			System.out.println(LocalDateTime.now() );
			expiredCouponsCleaner = new ExpiredCouponsClean();
			FacadeTest facadeTest = null;	

			while (systemOn) {
				logInMenu();
				String input = scan.nextLine();
				
				switch (input) {
				
				case "admin":
				case "1":
					facadeTest = new AdminFacadeTest(logIn(ClientType.ADMINISTRATOR));
					break;

				case "company":
				case "2":
					facadeTest = new CompanyFacadeTest(logIn(ClientType.COMPANY));
					break;

				case "customer":
				case "3":
					facadeTest = new CustomerFacadeTest(logIn(ClientType.CUSTOMER));
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
			}
			System.out.println(
					"___________________________________________________________________________________________________________________________________________________");

		} catch (LoginManagerException e) {
			System.out.println(e.getMessage());
		} catch (CouponSystemExceprion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			expiredCouponsCleaner.stop();
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
	
	private ClientFacade logIn(ClientType clientType) throws LoginManagerException {
		
		System.out.print("Email: ");
		String email = scan.nextLine();
		System.out.print("Password: ");
		String password = scan.nextLine();
		
		return login.login(email, password, clientType);
	}
	

}
