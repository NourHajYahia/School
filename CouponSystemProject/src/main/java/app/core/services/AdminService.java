package app.core.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exceptions.ServiceException;
import app.core.repositories.CompanyRepositories;
import app.core.repositories.CouponRepositories;
import app.core.repositories.CustomerRepositories;

@Service
@Transactional
public class AdminService extends ClientService {

	@Autowired
	CompanyRepositories companyRepositories;

	@Autowired
	CustomerRepositories customerRepositories;

	@Autowired
	CouponRepositories couponRepositories;
	
	
	
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

	/*
	 * confirms that the company object to be added to companies table obeys all
	 * business logic requirements and calls DAO methods to communicate with the
	 * data base.
	 *
	 * @param company
	 * 
	 * @throws ServiceException if company name is already exists, if company email
	 * already exists, if DAOException occurs.
	 */
	public void addCompany(Company company) throws ServiceException {
		if (!companyRepositories.findByName(company.getName()).isEmpty())
			throw new ServiceException("AdminFacade Error: adding company failed, company name already exists");
		else if (!companyRepositories.findByEmail(company.getEmail()).isEmpty())
			throw new ServiceException("AdminFacade Error: adding company failed, company email already exists");
		else
			companyRepositories.save(company);
	}

	/**
	 * confirms that the company object to be added to companies table obeys all
	 * business logic requirements and calls DAO methods to communicate with the
	 * data base.
	 *
	 * @param company
	 * @throws ServiceException if company name is already exists, if company email
	 *                          already exists, if DAOException occurs.
	 */
	public void updateCompany(Company company) throws ServiceException {
		Optional<Company> optional = companyRepositories.findById(company.getId());
		if (optional.isEmpty())
			throw new ServiceException("AdminFacade Error: updating company failed, did not find required company id");
		else if (!optional.get().getName().equalsIgnoreCase(company.getName()))
			throw new ServiceException("AdminFacade Error: updating company failed, can not update company's name");
		else if (!companyRepositories.findByIdIsNotAndEmail(company.getId(), company.getEmail()).isEmpty())
			throw new ServiceException("AdminFacade Error: updating company failed, company email already exists");
		else
			companyRepositories.save(company);
	}

	/**
	 * confirms that the company object to be deleted from companies table obeys all
	 * business logic requirements and calls DAO methods to communicate with the
	 * data base.
	 *
	 * @param companyID
	 * @throws ServiceException if company id does not exist, if DAOException
	 *                          occurs.
	 */
	public void deleteCompany(int companyID) throws ServiceException {

		Optional<Company> optional = companyRepositories.findById(companyID);
		if (optional.isPresent()) {
			companyRepositories.delete(optional.get());
		} else {
			throw new ServiceException("AdminFacade Error: deleting company failed, did not find required company id");
		}
	}

	/**
	 * getting all companies from data base.
	 *
	 */
	public List<Company> getAllCompanies() {
		return companyRepositories.findAll();
	}

	/**
	 * getting specified company by id from database.
	 *
	 * @param companyID
	 * @return Company
	 * @throws ServiceException
	 */
	public Company getCompany(int companyID) throws ServiceException {
		Optional<Company> optional = companyRepositories.findById(companyID);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new ServiceException("AdminFacade Error: getting company failed, did not find required company");
		}
	}

	/**
	 * confirms that the customer object to be added to customer table obeys all
	 * business logic requirements and calls DAO methods to communicate with the
	 * data base.
	 *
	 * @param customer
	 * @throws ServiceException if email already exists or DAOException occurs.
	 */
	public void addCustomer(Customer customer) throws ServiceException {
		if (!customerRepositories.findByEmail(customer.getEmail()).isEmpty()) {
            throw new ServiceException("AdminFacade Error: adding customer failed, customer email already exists");
        } else {
            customerRepositories.save(customer);
        }
	}
	
	/**
     * confirms that the customer object to be deleted from customers table obeys all business logic requirements and
     * calls DAO methods to communicate with the data base.
     *
     * @param customer
     * @throws ServiceException
     * if customer id does not exists or email already exists.
     */
	public void updateCustomer(Customer customer) throws ServiceException {
		Optional<Customer> optional = customerRepositories.findById(customer.getId());
		if (optional.isEmpty()) {
			throw new ServiceException("AdminFacade Error: updating customer failed, did not find required customer id");
		} else if (!customerRepositories.findByIdIsNotAndEmail(customer.getId(), customer.getEmail()).isEmpty()) {
			throw new ServiceException("AdminFacade Error: updating customer failed, customer email already exists");
		} else {
			customerRepositories.save(customer);
		}
	}
	
	 /**
     * confirms that the company object to be deleted from companies table obeys all business logic requirements and
     * calls DAO methods to communicate with the data base.
     *
     * @param customerID
     * @throws ServiceException
     * if did not find the required customer id or any of DAOException.
     */
	public void deleteCustomer ( int customerID) throws ServiceException {
		Optional<Customer> optional = customerRepositories.findById(customerID);
		if (optional.isPresent()) {
			customerRepositories.delete(optional.get());
		} else {
			throw new ServiceException("AdminFacade Error: deleting company failed, did not find required company id");
		}
	}
	

	/**
	 * getting all customers from data base.
	 *
	 */
	public List<Customer> getAllCustomers() {
		return customerRepositories.findAll();
	}
	
	/**
     * getting customer by id from customers table
     *
     * @param customerID
     * @return Customer
     * @throws ServiceException
     * if did not find the required id or any DAOException.
     */
    public Customer getCustomerById ( int customerID) throws ServiceException {
    	Optional<Customer> optional = customerRepositories.findById(customerID);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new ServiceException("AdminFacade Error: getting company failed, did not find required company");
		}
    }

}
