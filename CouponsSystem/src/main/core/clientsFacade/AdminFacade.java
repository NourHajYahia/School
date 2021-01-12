package main.core.clientsFacade;

import java.util.ArrayList;

import main.core.beans.Company;
import main.core.beans.Coupon;
import main.core.beans.Customer;
import main.core.dao.impel.CompaniesDBDAO;
import main.core.dao.impel.CouponsDBDAO;
import main.core.dao.impel.CustomersDBDAO;
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
			e.printStackTrace();
			throw new FacadeException("AdminFacade Error: adding company failed", e);
		}

	}

	public void updateCompany(Company company) throws FacadeException {

		try {
			Company currCompany = companiesDAO.getCompanyById(company.getId());
			if (currCompany != null) {
				if (currCompany.getName().equals(company.getName())) {
					companiesDAO.updateCompany(company);
				} else {
					throw new FacadeException(
							"AdminFacade Error: updating company failed, can not update company's name");
				}
			} else {
				throw new FacadeException("AdminFacade Error: updating company failed, did not find required company id");
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("AdminFacade Error: updating company failed", e);
		}

	}

	public void deleteCompany(Company company) throws FacadeException {

		try {
			Company currCompany = companiesDAO.getCompanyById(company.getId());
			if (currCompany != null && company.equals(currCompany)) {
				ArrayList<Coupon> coupons =  couponsDAO.getAllCouponsByCompanyId(company.getId());
				for (Coupon coupon : coupons) {
					couponsDAO.deleteCouponPurchase(coupon.getId());
					couponsDAO.deleteCoupon(coupon.getId());
				}
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

	public Company getCompany(int companyID) throws FacadeException {
		try {
			Company currCompany = companiesDAO.getCompanyById(companyID);
			if (currCompany != null) {
				return currCompany;
			}else {
				throw new FacadeException("AdminFacade Error: getting company failed, did not find required company");
			}
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
			if (currCustomer != null && customer.equals(currCustomer)) {
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
				ArrayList<Coupon> coupons = couponsDAO.getAllCouponsByCustomerId(customerID);
				for (Coupon coupon : coupons) {
					couponsDAO.deleteCouponPurchase(customerID, coupon.getId());
				}
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
