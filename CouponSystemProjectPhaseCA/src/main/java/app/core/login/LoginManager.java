package app.core.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import app.core.exceptions.LoginManagerException;
import app.core.exceptions.ServiceException;
import app.core.services.AdminService;
import app.core.services.ClientService;
import app.core.services.CompanyService;
import app.core.services.CustomerService;

@Component
public class LoginManager {
	
	@Autowired
	private ApplicationContext context;
   
    /**
     * authenticates login by email and password if were true returns a suitable clientFacade.
     * if failed to achieve authentications throws exception. 
     * 
     * @param email
     * @param password
     * @param clientType
     * @return ClientFacade
     * @throws LoginManagerException
     */
    public synchronized ClientService login(String email, String password, ClientType clientType) throws LoginManagerException {
    	ClientService client;
        try {
            switch (clientType) {
                case ADMINISTRATOR:
                    client = context.getBean(AdminService.class);
                    if (client.login(email, password)) {
                    	System.out.println(">>>> Admin is logged");
                        return client;
                    } else {
                        throw new LoginManagerException("LoginManager Error: admin authentication failed");
                    }
                case COMPANY:
                    client = context.getBean(CompanyService.class);
                    if (client.login(email, password)) {
                    	System.out.println(">>>> Company is logged");
                        return client;
                    } else {
                        throw new LoginManagerException("LoginManager Error: company authentication failed");
                    }
                case CUSTOMER:
                    client = context.getBean(CustomerService.class);
                    if (client.login(email, password)) {
                    	System.out.println(">>>> Customer is logged");
                        return client;
                    } else {
                        throw new LoginManagerException("LoginManager Error: customer authentication failed");
                    }
                default:
                    throw new LoginManagerException("LoginManager Error: client type is not valid");
            }
        } catch (ServiceException e) {
            throw new LoginManagerException("LoginManager Error: login failed,\n" + e.getMessage());
        }
    }
}
