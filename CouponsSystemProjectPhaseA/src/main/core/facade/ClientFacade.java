package main.core.facade;

import main.core.dao.CompaniesDAO;
import main.core.dao.CouponsDAO;
import main.core.dao.CustomersDAO;
import main.core.exceptions.FacadeException;

public abstract class ClientFacade {

    protected CompaniesDAO companiesDAO;

    protected CustomersDAO customersDAO;

    protected CouponsDAO couponsDAO;

    /**
     * authenticates client log in if email and password are valid returns
     * true else false.
     *
     * @param email
     * @param password
     * @return boolean
     */
    public abstract boolean login(String email, String password) throws FacadeException;

}
