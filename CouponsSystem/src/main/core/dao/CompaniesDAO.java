package main.core.dao;

import java.util.ArrayList;

import main.core.beans.Company;
import main.core.exceptions.DAOException;

public interface CompaniesDAO {

	boolean isCompanyExistByEmailAndPassword(String email, String password) throws DAOException;

	boolean isCompanyExistByEmail(String companyEmail) throws DAOException;

	boolean isCompanyExistByName(String companyName) throws DAOException;

	int getCompanyByEmailAndPassword(String email, String password) throws DAOException;

	void addCompany(Company company) throws DAOException;

	void updateCompany(Company company) throws DAOException;

	void deleteCompany(int companyID) throws DAOException;

	ArrayList<Company> getAllCompanies() throws DAOException;

	Company getCompanyById(int companyID) throws DAOException;

}
