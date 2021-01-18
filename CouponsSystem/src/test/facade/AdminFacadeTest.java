package test.facade;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.core.Facade.AdminFacade;
import main.core.beans.Company;
import main.core.beans.Customer;
import main.core.db.ConnectionPool;
import main.core.exceptions.ConnectionPoolException;
import main.core.exceptions.FacadeException;

public class AdminFacadeTest {

	private AdminFacade facade;
	
	public void testAll() {
		Company company;
		Customer customer;
		int customerID;
		int companyID;
		restoreCompaniesTableToDefault();
		restoreCustomersTableToDefault();
		System.out.println();
		System.out.println("____________________ adminFacadeTest __________________________");
		System.out.println();

		try {
			facade = new AdminFacade();
			// viewing all companies within database companies table
			System.out.println("___________________ getAllCompaniesTest __________________________");
			viewAllCompaniesTest();
			System.out.println();
			// ------------------------------------------------------------------------------------------------------

			// Testing addCompany function requirements
			System.out.println("___________________ addCompanyTest __________________________");
			System.out.println("\n1. Adding first legit company:-");
			company = new Company(0, "aaa", "aaa@aaa.com", "aaa");
			addCustomerTest(company);

			System.out.println("\n2. Adding unlegit company with existing name:-");
			company = new Company(0, "aaa", "bbb@bbb.com", "bbb");
			addCustomerTest(company);

			System.out.println("\n3. Adding unlegit company with existing email:-");
			company = new Company(0, "bbb", "aaa@aaa.com", "bbb");
			addCustomerTest(company);

			System.out.println("\n4. Adding second legit company:-");
			company = new Company(0, "bbb", "bbb@bbb.com", "bbb");
			addCustomerTest(company);

			System.out.println("\n5. Adding third legit company:-");
			company = new Company(0, "ccc", "ccc1@ccc1.com", "bbb");
			addCustomerTest(company);
			System.out.println();
			// -------------------------------------------------------------------------------------------------------

			// viewing all companies within database companies table
			System.out.println("___________________ getAllCompaniesTest __________________________");
			viewAllCompaniesTest();
			System.out.println();
			// ------------------------------------------------------------------------------------------------------

			// Testing updateCompany function requirements
			System.out.println("___________________ updateCompanyTest __________________________");
			System.out.println("\n1. updating company password:-");
			company = new Company(3, "ccc", "ccc1@ccc1.com", "ccc");
			updateCompanyTest(company);

			System.out.println("\n2. updating company email:-");
			company = new Company(3, "ccc", "ccc@ccc.com", "ccc");
			updateCompanyTest(company);

			System.out.println("\n3. updatin company with existing email:-");
			company = new Company(1, "aaa", "bbb@bbb.com", "aaa");
			updateCompanyTest(company);

			System.out.println("\n4. updatin company with existing name:-");
			company = new Company(1, "bbb", "aaa@aaa.com", "aaa");
			updateCompanyTest(company);

			System.out.println("\n5. updatin company with unexisting id:-");
			company = new Company(5, "aaa", "aaa@aaa.com", "ddd");
			updateCompanyTest(company);
			System.out.println();
			// -------------------------------------------------------------------------------------------------------

			// viewing all companies within database companies table
			System.out.println("___________________ getAllCompaniesTest __________________________");
			viewAllCompaniesTest();
			System.out.println();
			// ------------------------------------------------------------------------------------------------------

			// Testing deleteCompany function requirements
			System.out.println("___________________ deleteCompanyTest __________________________");
			System.out.println("\n1. deleting company by id:-");
			companyID = 3;
			deleteCompanyTest(companyID);

			System.out.println("\n2. deleting company with unexisting id:-");
			deleteCompanyTest(companyID);
			// -------------------------------------------------------------------------------------------------------

			// viewing all companies within database companies table
			System.out.println("___________________ getAllCompaniesTest __________________________");
			viewAllCompaniesTest();
			System.out.println();
			// ------------------------------------------------------------------------------------------------------

			// Testing getCompanyById function requirements
			System.out.println("___________________ getCompanyByIdTest __________________________");
			System.out.println("\n1. viewing company by id:-");
			companyID = 1;
			getCompanyByIdTest(companyID);

			System.out.println("\n2. viewing company by unexisted id:-");
			companyID = 3;
			getCompanyByIdTest(companyID);
			System.out.println();
			// ------------------------------------------------------------------------------------------------------

			// viewing all customers within database customers table
			System.out.println("___________________ getAllCustomersTest __________________________");
			viewAllCustomersTest();
			System.out.println();
			// ------------------------------------------------------------------------------------------------------

			// Testing addCustomer function requirements
			System.out.println("___________________ addCustomerTest __________________________");
			System.out.println("\n1. Adding first legit customer:-");
			customer = new Customer(0, "aaa", "aaa", "aaa@aaa.com", "aaa");
			addCustomerTest(customer);

			System.out.println("\n2. Adding unlegit customer with existing email:-");
			customer = new Customer(0, "bbb", "bbb", "aaa@aaa.com", "bbb");
			addCustomerTest(customer);

			System.out.println("\n4. Adding second legit customer:-");
			customer = new Customer(0, "aaa", "aaa", "bbb@bbb.com", "aaa");
			addCustomerTest(customer);

			System.out.println("\n5. Adding third legit customer:-");
			customer = new Customer(0, "ccc", "ccc", "ccc@ccc.com", "ccc");
			addCustomerTest(customer);
			System.out.println();
			// -------------------------------------------------------------------------------------------------------

			// viewing all customers within database customers table
			System.out.println("___________________ getAllCustomersTest __________________________");
			viewAllCustomersTest();
			System.out.println();
			// ------------------------------------------------------------------------------------------------------

			// Testing updateCustomer function requirements
			System.out.println("___________________ updateCustomerTest __________________________");
			System.out.println("\n1. update legit customer:-");
			customer = new Customer(2, "bbb", "bbb", "bbb1@bbb1.com", "bbb");
			updateCustomrsTest(customer);

			System.out.println("\n2. update unlegit customer with existing email:-");
			customer = new Customer(2, "bbb", "bbb", "aaa@aaa.com", "bbb");
			updateCustomrsTest(customer);

			System.out.println("\n3. update unlegit customer with existing unexisted id:-");
			customer = new Customer(4, "aaa", "aaa", "ddd@ddd.com", "aaa");
			updateCustomrsTest(customer);
			// -------------------------------------------------------------------------------------------------------

			// viewing all customers within database customers table
			System.out.println("___________________ getAllCustomersTest __________________________");
			viewAllCustomersTest();
			System.out.println();
			// ------------------------------------------------------------------------------------------------------

			// Testing deleteCustomer function requirements
			System.out.println("___________________ deleteCustomerTest __________________________");
			System.out.println("\n1. deleting custemer by id:-");
			customerID = 2;
			deleteCustomerTest(customerID);

			System.out.println("\n2. deleting custemer with unexisting id:-");
			deleteCustomerTest(customerID);
			// -------------------------------------------------------------------------------------------------------

			// viewing all customers within database customers table
			System.out.println("___________________ getAllCustomersTest __________________________");
			viewAllCustomersTest();
			System.out.println();
			// ------------------------------------------------------------------------------------------------------

			// Testing getCustomerById function requirements
			System.out.println("___________________ getCustomerByIdTest __________________________");
			System.out.println("\n1. viewing custemer by id:-");
			customerID = 1;
			getCustomerByIdTest(customerID);

			System.out.println("\n2. viewing custemer by unexisted id:-");
			customerID = 2;
			getCustomerByIdTest(customerID);
			System.out.println();
			// ------------------------------------------------------------------------------------------------------

		} catch (FacadeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public AdminFacadeTest(AdminFacade facade) {
		super();
		this.facade = facade;
	}

	private void restoreCompaniesTableToDefault() {
		Connection con = null;
		ConnectionPool connectionPool = null;
		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.getConnection();
			String sqlDelete = "delete from companies";
			Statement pstmt1 = con.createStatement();
			pstmt1.executeUpdate(sqlDelete);

			String sqlResetIncrement = "ALTER TABLE companies AUTO_INCREMENT = 1";
			Statement pstmt2 = con.createStatement();
			pstmt2.executeUpdate(sqlResetIncrement);
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connectionPool.restoreConnections(con);
			} catch (ConnectionPoolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void restoreCustomersTableToDefault() {
		Connection con = null;
		ConnectionPool connectionPool = null;
		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.getConnection();
			String sqlDelete = "delete from customers";
			Statement pstmt1 = con.createStatement();
			pstmt1.executeUpdate(sqlDelete);

			String sqlResetIncrement = "ALTER TABLE customers AUTO_INCREMENT = 1";
			Statement pstmt2 = con.createStatement();
			pstmt2.executeUpdate(sqlResetIncrement);
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connectionPool.restoreConnections(con);
			} catch (ConnectionPoolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void viewAllCompaniesTest() {
		try {
			ArrayList<Company> companies = facade.getAllCompanies();
			System.out.println();
			if (!companies.isEmpty()) {
				for (Company company : companies) {
					System.out.println("\t" + company);
				}
			} else {
				System.out.println("-- Empty");
			}
		} catch (FacadeException e) {
			System.out.println(e.getMessage());
		}
	}

	private void viewAllCustomersTest() {

		try {
			ArrayList<Customer> customers = facade.getAllCustomers();
			System.out.println();
			if (!customers.isEmpty()) {
				for (Customer customer : customers) {
					System.out.println("\t" + customer);
				}
			} else {
				System.out.println("-- Empty");
			}

		} catch (FacadeException e) {
			System.err.println(e.getMessage());
		}

	}

	private void addCustomerTest(Company company) {
		System.out.println("-- Adding: " + company);
		try {
			facade.addCompany(company);
			System.out.println("-- After addition: " + company);
			System.out.println("-- Company added");
		} catch (FacadeException e) {
			System.out.println(e.getMessage());
		}
	}

	private void addCustomerTest(Customer customer) {
		System.out.println("-- Adding: " + customer);
		try {
			facade.addCustomer(customer);
			System.out.println("-- After addition: " + customer);
			System.out.println("-- Customer added");
		} catch (FacadeException e) {
			System.out.println(e.getMessage());
		}
	}

	private void updateCompanyTest(Company company) {
		System.out.println("-- Updating: " + company);
		try {
			facade.updateCompany(company);
			System.out.println("-- Company updated");
		} catch (FacadeException e) {
			System.out.println(e.getMessage());
		}
	}

	private void updateCustomrsTest(Customer customer) {
		System.out.println("-- Updating: " + customer);
		try {
			facade.updateCustomer(customer);
			System.out.println("-- Customer updated");
		} catch (FacadeException e) {
			System.out.println(e.getMessage());
		}
	}

	private void deleteCompanyTest(int companyID) {
		System.out.println("-- Company Id for delete is: " + companyID);
		try {
			facade.deleteCompany(companyID);
			System.out.println("-- Company deleted");
		} catch (FacadeException e) {
			System.out.println(e.getMessage());
		}

	}

	private void deleteCustomerTest(int CustomerID) {
		System.out.println("-- Company Id for delete is: " + CustomerID);
		try {
			facade.deleteCustomer(CustomerID);
			System.out.println("-- Customer deleted");
		} catch (FacadeException e) {
			System.out.println(e.getMessage());
		}

	}

	private void getCompanyByIdTest(int companyID) {
		try {
			System.out.println("-- Company required Id is: " + companyID);
			System.out.println("-- Company required from Database: " + facade.getCompany(companyID));
			System.out.println("-- Company required");
		} catch (FacadeException e) {
			System.out.println(e.getMessage());
		}
	}

	private void getCustomerByIdTest(int CustomerID) {
		try {
			System.out.println("-- Customer required Id is: " + CustomerID);
			System.out.println("-- Customer required from Database: " + facade.getCustomerById(CustomerID));
			System.out.println("-- Customer required");
		} catch (FacadeException e) {
			System.out.println(e.getMessage());
		}
	}

}
