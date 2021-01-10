package app.core.dao;

import java.util.ArrayList;

import app.core.beans.Company;
import app.core.exceptions.DAOException;

public interface CompaniesDAO {

	boolean isCompanyExists(String email, String password) throws DAOException;

	public boolean isCompanyExistByEmail(String companyEmail) throws DAOException;

	public boolean isCompanyExistByName(String companyName) throws DAOException;

	public Company getCompanyByEmailAndPassword(String email, String password) throws DAOException;

	void addCompany(Company company) throws DAOException;

	void updateCompany(Company company) throws DAOException;

	void deleteCompany(int companyID) throws DAOException;

	ArrayList<Company> getAllCompanies() throws DAOException;

	Company getCompanyById(int companyID) throws DAOException;

}
