package main.core.clientsLoginManager;

import main.core.clientsFacade.AdminFacade;
import main.core.clientsFacade.ClientFacade;
import main.core.exceptions.FacadeException;
import main.core.exceptions.LoginManagerException;

public class LoginManager {

	private static LoginManager instance;

	public LoginManager() {
		super();
	}

	public LoginManager getInstance() {
		if (instance == null) {
			instance = new LoginManager();
			return instance;
		}
		return instance;
	}

	public ClientFacade login(String email, String password, ClientType clientType) throws LoginManagerException {

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
				client = new AdminFacade();
				if (client.login(email, password)) {
					return client;
				} else {
					throw new LoginManagerException("LoginManager Error: company authintication failed");
				}

			case CUSTOMER:
				client = new AdminFacade();
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
