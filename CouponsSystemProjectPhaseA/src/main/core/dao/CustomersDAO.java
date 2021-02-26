package main.core.dao;

import main.core.beans.Customer;
import main.core.exceptions.DAOException;

import java.util.ArrayList;

public interface CustomersDAO {

    /**
     * checking the data base table customers for a customer with specified email.
     *
     * @param email
     * @param password
     * @return boolean
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    boolean isCustomerExists(String email, String password) throws DAOException;

    /**
     * checking the data base table customers for a customer with specified email and password.
     *
     * @param email
     * @return boolean
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    boolean isCustomerExistsByEmail(String email) throws DAOException;

    /**
     * adding a customer to the data base table customers.
     *
     * @param customer
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    void addCustomer(Customer customer) throws DAOException;

    /**
     * updating a customer in the data base table customers.
     *
     * @param customer
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    void updateCustomer(Customer customer) throws DAOException;

    /**
     * deleting a customer from data base table customers.
     *
     * @param customerID
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    void deleteCustomer(int customerID) throws DAOException;

    /**
     * asking the data base to fetch all the customers in the data base.
     *
     * @return
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    ArrayList<Customer> getAllCustomers() throws DAOException;

    /**
     * asking the data base to fetch a specified customer from customers table by id.
     *
     * @param customerID
     * @return
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    Customer getCustomerById(int customerID) throws DAOException;

    /**
     * asking the data base to fetch a specified customer from customers table by email and password.
     *
     * @param email
     * @param password
     * @return
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    Customer getCustomerByEmailAndPassword(String email, String password) throws DAOException;
}
