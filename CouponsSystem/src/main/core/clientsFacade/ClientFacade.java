package main.core.clientsFacade;

import main.core.dao.CompaniesDAO;
import main.core.dao.CouponsDAO;
import main.core.dao.CustomersDAO;
import main.core.exceptions.FacadeException;

public abstract class ClientFacade {

	public CompaniesDAO companiesDAO;
	
	public CustomersDAO customersDAO;
	
	public CouponsDAO couponsDAO;
	
	public abstract boolean login(String email, String password) throws FacadeException;
	
}
