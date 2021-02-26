package main.test;

import java.util.Scanner;

import main.core.connectionDB.ConnectionPool;
import main.core.dailyJob.ExpiredCouponsCleaner;
import main.core.exceptions.CouponsSystemException;
import main.core.facade.ClientFacade;
import main.core.loginManager.ClientType;
import main.core.loginManager.LoginManager;
import main.test.facadeTest.AdminFacadeTest;
import main.test.facadeTest.CompanyFacadeTest;
import main.test.facadeTest.CustomerFacadeTest;
import main.test.facadeTest.FacadeTest;
import main.test.initiateDB.InitializeDB;

public class Test1 {

    private final Scanner scan = new Scanner(System.in);

    public void testAll() {
    	
    		
    	

    	ExpiredCouponsCleaner expiredCouponsCleaner = null;
        try {
            ConnectionPool.getInstance();
            InitializeDB.start();
            boolean systemOn = true;
            expiredCouponsCleaner = new ExpiredCouponsCleaner();
            System.out.println("________________________________________________________________________");
            System.out.println("My suggession for the test is: ");
            System.out.println("-- first choose admin, email: admin@admin.com, password: admin");
            System.out.println("-- second choose company, email: aaa@aaa.com, password: aaa");
            System.out.println("-- third choose customer, email: aaa@aaa.com, password: aaa");
            System.out.println("-- lastly enjoy your combinations");
            while (systemOn) {
                logInMenu();
                String input = scan.nextLine();

                FacadeTest facadeTest = null;
                ClientFacade clientFacade;
                String email;
                String password;
                switch (input) {
                case "admin":
				case "1":
					System.out.print("Email: ");
			        email = scan.nextLine();
			        System.out.print("Password: ");
			        password = scan.nextLine();
					clientFacade = LoginManager.getInstance().login(email, password, ClientType.ADMINISTRATOR);
					if (clientFacade != null) {
						facadeTest = new AdminFacadeTest(clientFacade);
					}
					break;

				case "company":
				case "2":
					System.out.print("Email: ");
			        email = scan.nextLine();
			        System.out.print("Password: ");
			        password = scan.nextLine();
					clientFacade = LoginManager.getInstance().login(email, password, ClientType.COMPANY);
					if (clientFacade != null) {
						facadeTest = new CompanyFacadeTest(clientFacade);
					}
					break;

				case "customer":
				case "3":
					System.out.print("Email: ");
			        email = scan.nextLine();
			        System.out.print("Password: ");
			        password = scan.nextLine();
					clientFacade = LoginManager.getInstance().login(email, password, ClientType.CUSTOMER);
					if (clientFacade != null) {
						facadeTest = new CustomerFacadeTest(clientFacade);
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
        } catch (CouponsSystemException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if(expiredCouponsCleaner != null)
                    expiredCouponsCleaner.stop();
                scan.close();
                ConnectionPool.getInstance().closeAllConnections();
            } catch (CouponsSystemException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }

    }

    private void logInMenu() {
        System.out.println("__________________________________________________________________ LogIn Test ______________________________________________________________________");
        System.out.println(
                "Admin ............................................................................................................................................... 1 / admin");
        System.out.println(
                "Company client ...................................................................................................................................... 2 / company");
        System.out.println(
                "Customer client ..................................................................................................................................... 3 / customer");
        System.out.println(
                "Quit ................................................................................................................................................ q / quit");
        System.out.print("Please choose your client type: ");
    }
}
