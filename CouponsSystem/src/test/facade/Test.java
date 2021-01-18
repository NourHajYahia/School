package test.facade;

import main.core.Facade.AdminFacade;
import main.core.LoginManager.ClientType;
import main.core.LoginManager.LoginManager;
import main.core.exceptions.LoginManagerException;

public class Test {
	public static void main(String[] args) {
		
		testAll();
		
	}

	public static void testAll() {
		
		LoginManager login = LoginManager.getInstance();
		try {
			AdminFacade admin = (AdminFacade) login.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
			AdminFacadeTest adminTest = new AdminFacadeTest(admin);
			adminTest.testAll();
		} catch (LoginManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}

