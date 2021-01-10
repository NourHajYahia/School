package app.core.clientsFacade;

import java.util.ArrayList;

import app.core.beans.Company;
import app.core.beans.Customer;
import app.core.dao.impel.CompaniesDBDAO;
import app.core.dao.impel.CouponsDBDAO;
import app.core.dao.impel.CustomersDBDAO;
import app.core.exceptions.DAOException;
import app.core.exceptions.FacadeException;

public class AdminFacade extends ClientFacade {

	public AdminFacade() throws FacadeException {
		try {
			this.companiesDAO = new CompaniesDBDAO();
			this.customersDAO = new CustomersDBDAO();
			this.couponsDAO = new CouponsDBDAO();
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("AdminFacade Error: initializing AdminFacade failed", e);
		}
	}

	@Override
	public boolean login(String email, String passwoed) {
		if (email.equals("admin@admin.com") && passwoed.equals("admin")) {
			return true;
		} else {
			return false;
		}
	}

	public void addCompany(Company company) throws FacadeException {

		try {
			if (companiesDAO.isCompanyExistByName(company.getName())) {
				throw new FacadeException("AdminFacade Error: adding company failed, company name already exists");
			} else if (companiesDAO.isCompanyExistByEmail(company.getEmail())) {
				throw new FacadeException("AdminFacade Error: adding company failed, company email already exists");
			} else {
				companiesDAO.addCompany(company);
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("AdminFacade Error: adding company failed", e);
		}

	}

	public void updateCompany(Company company) throws FacadeException {

		try {
			Company currCompany = companiesDAO.getCompanyById(company.getId());
			if (currCompany != null) {
				if (currCompany.getName() == company.getName()) {
					companiesDAO.updateCompany(company);
				} else {
					throw new FacadeException(
							"AdminFacade Error: updating company failed, can not update company's name");
				}
			} else {
				throw new FacadeException("AdminFacade Error: updating company failed, did not find required company");
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("AdminFacade Error: updating company failed", e);
		}

	}

	public void deleteCompany(Company company) throws FacadeException {

		try {
			Company currCompany = companiesDAO.getCompanyById(company.getId());
			if (currCompany.equals(company)) {
				companiesDAO.deleteCompany(company.getId());
			} else {
				throw new FacadeException("AdminFacade Error: deleting company failed, did not find required company");
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("AdminFacade Error: deleting company failed", e);
		}

	}

	public ArrayList<Company> getAllCompanies() throws FacadeException {
		try {
			return companiesDAO.getAllCompanies();
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("AdminFacade Error: getting all companies failed", e);
		}
	}

	public Company getCompany(Company company) throws FacadeException {
		try {
			return companiesDAO.getCompanyById(company.getId());
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("AdminFacade Error: getting company failed", e);
		}
	}

	public void addCustomer(Customer customer) throws FacadeException {

		try {
			if (!customersDAO.isCustomerExistsByEmail(customer.getEmail())) {
				customersDAO.addCustomer(customer);
			} else {
				throw new FacadeException("AdminFacade Error: adding customer failed, customer email already exists");
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("AdminFacade Error: adding customer failed", e);
		}

	}

	public void updateCustomer(Customer customer) throws FacadeException {

		try {
			Customer currCustomer = customersDAO.getCustomerById(customer.getId());
			if (currCustomer != null) {
				customersDAO.updateCustomer(customer);
			}else {
				throw new FacadeException("AdminFacade Error: updating customer failed, did not find required company");
			}

		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("AdminFacade Error: updating customer failed", e);
		}

	}

	public void deleteCustomer(int customerID) throws FacadeException {
		try {
			Customer currCustomer = customersDAO.getCustomerById(customerID);
			if (currCustomer != null) {
				customersDAO.deleteCustomer(customerID);
			}else {
				throw new FacadeException("AdminFacade Error: deleting customer failed, did not find required company");
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("AdminFacade Error: delete customer failed", e);
		}
	}

	public ArrayList<Customer> getAllCustomers() throws FacadeException {

		try {
			return customersDAO.getAllCustomers();
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("AdminFacade Error: geting all customer failed", e);
		}

	}

	public Customer getCustomerById(int customerID) throws FacadeException {
		try {
			return customersDAO.getCustomerById(customerID);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("AdminFacade Error: geting customer failed", e);
		}

	}

}
