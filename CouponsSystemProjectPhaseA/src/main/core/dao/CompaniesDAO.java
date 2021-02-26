package main.core.dao;

import java.util.ArrayList;

import main.core.beans.Company;
import main.core.exceptions.DAOException;

public interface CompaniesDAO {


    /**
     *  checking the data base table companies for a company with specified email and password.
     *
     * @param email
     * @param password
     * @return boolean
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    boolean isCompanyExistByEmailAndPassword(String email, String password) throws DAOException;

    /**
     * checking the data base table companies for a company with specified email.
     *
     * @param companyEmail
     * @return boolean
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    boolean isCompanyExistByEmail(String companyEmail) throws DAOException;

    /**
     * checking the data base table companies for a company with specified name.
     *
     * @param companyName
     * @return boolean
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    boolean isCompanyExistByName(String companyName) throws DAOException;

    /**
     * adding a company to the data base table companies.
     *
     * @param company
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    void addCompany(Company company) throws DAOException;

    /**
     * updating a company in the data base table companies.
     *
     * @param company
     * @throws DAOException
     * if ConnectionPool failed to supply connection or to close,
     * SQL failed to to execute the deletion
     */
    void updateCompany(Company company) throws DAOException;

    /**
     * deleting a company from data base table companies.
     *
     * @param companyID
     * @throws DAOException
     */
    void deleteCompany(int companyID) throws DAOException;

    /**
     * asking the data base to fetch all the companies in the data base.
     *
     * @returnArrayList
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    ArrayList<Company> getAllCompanies() throws DAOException;

    /**
     * asking the data base to fetch a specified company from companies table by id.
     *
     * @param companyID
     * @returnArrayList
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    Company getCompanyById(int companyID) throws DAOException;

    /**
     * asking the data base to fetch a specified company from companies table by email and password.
     *
     * @param email
     * @param password
     * @returnArrayList
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    Company getCompanyByEmailAndPassword(String email, String password) throws DAOException;

}
