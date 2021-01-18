package main.core.Facade;

import java.util.ArrayList;

import main.core.beans.Company;
import main.core.beans.Coupon;
import main.core.beans.Customer;
import main.core.dao.impl.CompaniesDBDAO;
import main.core.dao.impl.CouponsDBDAO;
import main.core.dao.impl.CustomersDBDAO;
import main.core.exceptions.DAOException;
import main.core.exceptions.FacadeException;

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
	public boolean login(String email, String password) {
		if (email.equals("admin@admin.com") && password.equals("admin")) {
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
			throw new FacadeException("AdminFacade Error: adding company failed", e);
		}

	}

	public void updateCompany(Company company) throws FacadeException {

		try {
			Company currCompany = companiesDAO.getCompanyById(company.getId());
			if (currCompany == null) {
				throw new FacadeException("AdminFacade Error: updating company failed, did not find required company id");
			} else if (!currCompany.getName().equals(company.getName())) {
				throw new FacadeException("AdminFacade Error: updating company failed, can not update company's name");
			} else if (companiesDAO.isCompanyExistByEmail(company.getEmail()) && !currCompany.getEmail().equals(company.getEmail())) {
				throw new FacadeException("AdminFacade Error: update company failed, company email already exists");
			} else {
				companiesDAO.updateCompany(company);
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("AdminFacade Error: updating company failed", e);
		}

	}

	public void deleteCompany(int companyID) throws FacadeException {

		try {
			Company currCompany = companiesDAO.getCompanyById(companyID);
			if (currCompany != null) {
				ArrayList<Coupon> coupons = couponsDAO.getAllCouponsByCompanyId(companyID);
				if (!coupons.isEmpty()) {
					for (Coupon coupon : coupons) {
						couponsDAO.deleteCouponPurchase(coupon.getId());
						couponsDAO.deleteCoupon(coupon.getId());
					}
				}
				companiesDAO.deleteCompany(companyID);
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

	public Company getCompany(int companyID) throws FacadeException {
		try {
			Company currCompany = companiesDAO.getCompanyById(companyID);
			if (currCompany != null) {
				return currCompany;
			} else {
				throw new FacadeException("AdminFacade Error: getting company failed, did not find required company");
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("AdminFacade Error: getting company failed", e);
		}
	}

	public void addCustomer(Customer customer) throws FacadeException {

		try {
			if (customersDAO.isCustomerExistsByEmail(customer.getEmail())) {
				throw new FacadeException("AdminFacade Error: adding customer failed, customer email already exists");
			} else {
				customersDAO.addCustomer(customer);
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("AdminFacade Error: adding customer failed", e);
		}

	}

	public void updateCustomer(Customer customer) throws FacadeException {

		try {
			Customer currCustomer = customersDAO.getCustomerById(customer.getId());
			if (currCustomer == null) {
				throw new FacadeException("AdminFacade Error: updating customer failed, did not find required company");
			} else if (customersDAO.isCustomerExistsByEmail(customer.getEmail()) && !currCustomer.getEmail().equals(customer.getEmail())) {
				throw new FacadeException("AdminFacade Error: updating customer failed, customer email already exists");
			} else {
				customersDAO.updateCustomer(customer);
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
				ArrayList<Coupon> coupons = couponsDAO.getAllCouponsByCustomerId(customerID);
				if (!coupons.isEmpty()) {
					for (Coupon coupon : coupons) {
						couponsDAO.deleteCouponPurchase(customerID, coupon.getId());
					}
				}
				customersDAO.deleteCustomer(customerID);
			} else {
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
			Customer currCustomer = customersDAO.getCustomerById(customerID);
			if (currCustomer != null) {
				return currCustomer;
			} else {
				throw new FacadeException("AdminFacade Error: getting customer failed, did not find required customer");
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("AdminFacade Error: getting customer failed", e);
		}

	}

}
