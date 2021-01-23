package main.core.loginManager;

import main.core.exceptions.FacadeException;
import main.core.exceptions.LoginManagerException;
import main.core.facade.AdminFacade;
import main.core.facade.ClientFacade;
import main.core.facade.CompanyFacade;
import main.core.facade.CustomerFacade;

public class LoginManager {

	private static LoginManager instance;

	private LoginManager() {
	}

	public static synchronized LoginManager getInstance() {
		if (instance == null) {
			instance = new LoginManager();
			return instance;
		}
		return instance;
	}

	public synchronized ClientFacade login(String email, String password, ClientType clientType) throws LoginManagerException {

		ClientFacade client;
		try {
			switch (clientType) {
			case ADMINISTRATOR:
				client = new AdminFacade();
				if (client.login(email, password)) {
					return client;
				} else {
					throw new LoginManagerException("LoginManager Error: admin authintication failed");
				}

			case COMPANY:
				client = new CompanyFacade();
				if (client.login(email, password)) {
					return client;
				} else {
					throw new LoginManagerException("LoginManager Error: company authintication failed");
				}

			case CUSTOMER:
				client = new CustomerFacade();
				if (client.login(email, password)) {
					return client;
				} else {
					throw new LoginManagerException("LoginManager Error: customer authintication failed");
				}

			default:
				throw new LoginManagerException("LoginManager Error: client type is not valid");

			}
		} catch (FacadeException e) {
			e.printStackTrace();
			throw new LoginManagerException("LoginManager Error: login failed: Facade Error ", e);
		}

	}

}
