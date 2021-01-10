package app.core.dao;

import java.util.ArrayList;

import app.core.beans.Customer;
import app.core.exceptions.DAOException;

public interface CustomersDAO {

	boolean isCustomerExists(String email, String password) throws DAOException;

	public boolean isCustomerExistsByEmail(String email) throws DAOException;

	void addCustomer(Customer customer) throws DAOException;

	void updateCustomer(Customer customer) throws DAOException;

	void deleteCustomer(int customerID) throws DAOException;

	ArrayList<Customer> getAllCustomers() throws DAOException;

	Customer getCustomerById(int customerID) throws DAOException;

}
