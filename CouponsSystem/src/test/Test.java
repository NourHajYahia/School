package test;

import main.core.cleanExpired.ExpiredCouponsClean;
import main.core.exceptions.CouponSystemExceprion;
import main.core.exceptions.LoginManagerException;
import main.core.facade.ClientFacade;
import main.core.loginManager.ClientType;
import main.core.loginManager.LoginManager;
import test.facade.AdminFacadeTest;
import test.facade.CompanyFacadeTest;
import test.facade.CustomerFacadeTest;

public class Test {
	
	public static void testAll() {

		LoginManager login = LoginManager.getInstance();
		ExpiredCouponsClean expiredCouponsCleaner = null;

		
		try {

			expiredCouponsCleaner = new ExpiredCouponsClean();
			System.out.println("___________________________________________________________________________________________________________________________________________________");
			ClientFacade admin = login.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
			AdminFacadeTest adminTest = new AdminFacadeTest(admin);
			adminTest.runTest();
			System.out.println("___________________________________________________________________________________________________________________________________________________");

			System.out.println("___________________________________________________________________________________________________________________________________________________");
			ClientFacade company = login.login("aaa@aaa.com", "aaa", ClientType.COMPANY);
			CompanyFacadeTest companyTest = new CompanyFacadeTest(company);
			companyTest.runTest();
			System.out.println("___________________________________________________________________________________________________________________________________________________");

			System.out.println("___________________________________________________________________________________________________________________________________________________");
			ClientFacade customer = login.login("aaa@aaa.com", "aaa", ClientType.CUSTOMER);
			CustomerFacadeTest customerTest = new CustomerFacadeTest(customer);
			customerTest.runTest();
			System.out.println("___________________________________________________________________________________________________________________________________________________");
			
			
		} catch (LoginManagerException e) {
			System.out.println(e.getMessage());
		} catch (CouponSystemExceprion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			expiredCouponsCleaner.stop();
		}
	}
}
