package main.core.facade;

import main.core.dao.CompaniesDAO;
import main.core.dao.CouponsDAO;
import main.core.dao.CustomersDAO;
import main.core.exceptions.FacadeException;

public abstract class ClientFacade {

	protected CompaniesDAO companiesDAO;
	
	protected CustomersDAO customersDAO;
	
	protected CouponsDAO couponsDAO;
	
	public abstract boolean login(String email, String password) throws FacadeException;
	
}
