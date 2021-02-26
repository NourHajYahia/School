package main.test;

import main.core.connectionDB.ConnectionPool;
import main.core.dailyJob.ExpiredCouponsCleaner;
import main.core.exceptions.CouponsSystemException;
import main.core.facade.ClientFacade;
import main.core.loginManager.ClientType;
import main.core.loginManager.LoginManager;
import main.test.facadeTest.AdminFacadeTest;
import main.test.facadeTest.CompanyFacadeTest;
import main.test.facadeTest.CustomerFacadeTest;
import main.test.initiateDB.InitializeDB;

public class Test {

	public void testAll() {

		ExpiredCouponsCleaner expiredCouponsCleaner = null;
		LoginManager login = LoginManager.getInstance();
		try {
			ConnectionPool.getInstance();
			InitializeDB.start();
			expiredCouponsCleaner = new ExpiredCouponsCleaner();

			System.out.println(
					"__________________________________________________Admin_________________________________________________________________________________________________");
			ClientFacade admin = login.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
			AdminFacadeTest adminTest = new AdminFacadeTest(admin);
			adminTest.runTest();
			System.out.println(
					"___________________________________________________________________________________________________________________________________________________");

			System.out.println("Company is loging in ...");
			Thread.sleep(1000);
			
			System.out.println(
					"_________________________________________________Company_______________________________________________________________________________________________");
			ClientFacade company = login.login("aaa@aaa.com", "aaa", ClientType.COMPANY);
			CompanyFacadeTest companyTest = new CompanyFacadeTest(company);
			companyTest.runTest();
			System.out.println(
					"___________________________________________________________________________________________________________________________________________________");

			System.out.println("Customer is loging in ...");
			Thread.sleep(1000);
			
			System.out.println(
					"_________________________________________________Customer________________________________________________________________________________________________");
			ClientFacade customer = login.login("aaa@aaa.com", "aaa", ClientType.CUSTOMER);
			CustomerFacadeTest customerTest = new CustomerFacadeTest(customer);
			customerTest.runTest();
			System.out.println(
					"___________________________________________________________________________________________________________________________________________________");

			//Thread Test//
//			System.out.println("Waiting for daily job to achieve its first check, for thread testing ...");
//			Thread.sleep(1000*60);
			
		} catch (CouponsSystemException e) {
			System.out.println(e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (expiredCouponsCleaner != null)
					expiredCouponsCleaner.stop();
				ConnectionPool.getInstance().closeAllConnections();
			} catch (CouponsSystemException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
