package app.core.services;

import app.core.exceptions.ServiceException;

public abstract class ClientService {
	
	/**
     * authenticates client log in if email and password are valid returns
     * true else false.
     *
     * @param email
     * @param password
     * @return boolean
     */
    public abstract boolean login(String email, String password) throws ServiceException;

}
