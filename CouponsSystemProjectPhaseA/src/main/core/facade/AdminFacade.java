package main.core.facade;

import java.util.ArrayList;

import main.core.beans.Company;
import main.core.beans.Customer;
import main.core.dao.impl.CompaniesDBDAO;
import main.core.dao.impl.CouponsDBDAO;
import main.core.dao.impl.CustomersDBDAO;
import main.core.exceptions.DAOException;
import main.core.exceptions.FacadeException;

public class AdminFacade extends ClientFacade {

    public AdminFacade() {
        this.companiesDAO = new CompaniesDBDAO();
        this.customersDAO = new CustomersDBDAO();
        this.couponsDAO = new CouponsDBDAO();
    }

    
    /**
     * return true if log in email and password valid, otherwise false.
     * @return boolean
     * @param email- admin@admin.com
     * @param passowrd - admin
     */
    @Override
    public boolean login(String email, String password) {
        return email.equals("admin@admin.com") && password.equals("admin");
    }

    /**
     * confirms that the company object to be added to companies table obeys all business logic requirements and
     * calls DAO methods to communicate with the data base.
     *
     * @param company
     * @throws FacadeException
     * if company name is already exists,
     * if company email already exists,
     * if DAOException occurs.
     */
    public void addCompany(Company company) throws FacadeException {
        try {
            if (companiesDAO.isCompanyExistByName(company.getName()))
                throw new FacadeException("AdminFacade Error: adding company failed, company name already exists");
            else if (companiesDAO.isCompanyExistByEmail(company.getEmail()))
                throw new FacadeException("AdminFacade Error: adding company failed, company email already exists");
            else companiesDAO.addCompany(company);
        } catch (DAOException e) {
            throw new FacadeException("AdminFacade Error: adding company failed,\n" + e.getMessage());
        }
    }

    /**
     * confirms that the company object to be updated to companies table obeys all business logic requirements and
     * calls DAO methods to communicate with the data base.
     *
     * @param company
     * @throws FacadeException
     * if failed to find required company id,
     * if trying to update company's name,
     * if company's email already exists,
     * if DAOException occurs.
     */
    public void updateCompany(Company company) throws FacadeException {
        try {
            Company currCompany = companiesDAO.getCompanyById(company.getId());
            if (currCompany == null)
                throw new FacadeException("AdminFacade Error: updating company failed, did not find required company id");
            else if (!currCompany.getName().equals(company.getName()))
                throw new FacadeException("AdminFacade Error: updating company failed, can not update company's name");
            else if (companiesDAO.isCompanyExistByEmail(company.getEmail()) && !currCompany.getEmail().equals(company.getEmail()))
                throw new FacadeException("AdminFacade Error: updating company failed, company email already exists");
            else companiesDAO.updateCompany(company);
        } catch (DAOException e) {
            throw new FacadeException("AdminFacade Error: updating company failed,\n" + e.getMessage());
        }
    }
     
    /**
     * confirms that the company object to be deleted from companies table obeys all business logic requirements and
     * calls DAO methods to communicate with the data base.
     *
     * @param companyID
     * @throws FacadeException
     * if company id does not exist,
     * if DAOException occurs.
     */
    public void deleteCompany(int companyID) throws FacadeException {
        try {
            Company currCompany = companiesDAO.getCompanyById(companyID);
            if (currCompany != null) {
                couponsDAO.deleteCompanyCustomersPurchaseCoupons(companyID);
                couponsDAO.deleteCompanyCoupons(companyID);
                companiesDAO.deleteCompany(companyID);
            } else {
                throw new FacadeException("AdminFacade Error: deleting company failed, did not find required company id");
            }
        } catch (DAOException e) {
            throw new FacadeException("AdminFacade Error: deleting company failed,\n" + e.getMessage());
        }
    }

    /**
     * getting all companies from data base.
     *
     * @return ArrayList
     * @throws FacadeException
     */
    public ArrayList<Company> getAllCompanies() throws FacadeException {
        ArrayList<Company> companies;
        try {
            companies = companiesDAO.getAllCompanies();
        } catch (DAOException e) {
            throw new FacadeException("AdminFacade Error: getting all companies from data base failed,\n" + e.getMessage());
        }
        return companies;
    }

    /**
     * getting specified company by id from database.
     *
     * @param companyID
     * @return Company
     * @throws FacadeException
     */
    public Company getCompany(int companyID) throws FacadeException {
        try {
            Company currCompany = companiesDAO.getCompanyById(companyID);
            if (currCompany != null) {
                return currCompany;
            } else {
                throw new FacadeException("AdminFacade Error: getting company failed, did not find required company");
            }
        } catch (DAOException e) {
            throw new FacadeException("AdminFacade Error: getting company from data base failed,\n" + e.getMessage());
        }
    }

    /**
     * confirms that the customer object to be added to customer table obeys all business logic requirements and
     * calls DAO methods to communicate with the data base.
     *
     * @param customer
     * @throws FacadeException
     * if email already exists or DAOException occurs.
     */
    public void addCustomer(Customer customer) throws FacadeException {
        try {
            if (customersDAO.isCustomerExistsByEmail(customer.getEmail())) {
                throw new FacadeException("AdminFacade Error: adding customer failed, customer email already exists");
            } else {
                customersDAO.addCustomer(customer);
            }
        } catch (DAOException e) {
            e.printStackTrace();
            throw new FacadeException("AdminFacade Error: adding customer failed,\n" + e.getMessage());
        }
    }

    /**
     * confirms that the customer object to be deleted from customers table obeys all business logic requirements and
     * calls DAO methods to communicate with the data base.
     *
     * @param customer
     * @throws FacadeException
     * if customer id does not exists or email already exists.
     */
    public void updateCustomer(Customer customer) throws FacadeException {
        try {
            Customer currCustomer = customersDAO.getCustomerById(customer.getId());
            if (currCustomer == null) {
                throw new FacadeException("AdminFacade Error: updating customer failed, did not find required customer id");
            } else if (customersDAO.isCustomerExistsByEmail(customer.getEmail()) && !currCustomer.getEmail().equals(customer.getEmail())) {
                throw new FacadeException("AdminFacade Error: updating customer failed, customer email already exists");
            } else {
                customersDAO.updateCustomer(customer);
            }
        } catch (DAOException e) {
            e.printStackTrace();
            throw new FacadeException("AdminFacade Error: update customer failed,\n" + e.getMessage());
        }
    }

    /**
     * confirms that the company object to be deleted from companies table obeys all business logic requirements and
     * calls DAO methods to communicate with the data base.
     *
     * @param customerID
     * @throws FacadeException
     * if did not find the required customer id or any of DAOException.
     */
    public void deleteCustomer ( int customerID) throws FacadeException {
        try {
            Customer currCustomer = customersDAO.getCustomerById(customerID);
            if (currCustomer != null) {
                couponsDAO.deleteCustomerCoupons(customerID);
                customersDAO.deleteCustomer(customerID);
            } else {
                throw new FacadeException("AdminFacade Error: deleting customer failed, did not find required customer id");
            }
        } catch (DAOException e) {
            e.printStackTrace();
            throw new FacadeException("AdminFacade Error: deleting customer failed,\n" + e.getMessage());
        }
    }

    /**
     *
     * @return
     * @throws FacadeException
     * if DAOException occurs.
     */
    public ArrayList<Customer> getAllCustomers () throws FacadeException {

        try {
            return customersDAO.getAllCustomers();
        } catch (DAOException e) {
            e.printStackTrace();
            throw new FacadeException("AdminFacade Error: getting all customers failed,\n" + e.getMessage());
        }

    }

    /**
     * getting customer by id from customers table
     *
     * @param customerID
     * @return Customer
     * @throws FacadeException
     * if did not find the required id or any DAOException.
     */
    public Customer getCustomerById ( int customerID) throws FacadeException {
        try {
            Customer currCustomer = customersDAO.getCustomerById(customerID);
            if (currCustomer != null) {
                return currCustomer;
            } else {
                throw new FacadeException("AdminFacade Error: getting customer failed, did not find required customer id");
            }
        } catch (DAOException e) {
            e.printStackTrace();
            throw new FacadeException("AdminFacade Error: getting customer failed,\n" + e.getMessage());
        }
    }
}
