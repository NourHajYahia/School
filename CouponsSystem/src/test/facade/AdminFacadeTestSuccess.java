package test.facade;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.core.beans.Company;
import main.core.beans.Customer;
import main.core.clientsFacade.AdminFacade;
import main.core.db.ConnectionPool;
import main.core.exceptions.ConnectionPoolException;
import main.core.exceptions.FacadeException;

public class AdminFacadeTestSuccess {

	static AdminFacade facade;
	static Company customer;

	public static void main(String[] args) {

		try {
			facade = new AdminFacade();
			restoreCompaniesTableToDefault();
			restoreCustomersTableToDefault();
			System.out.println("========== Admin Login Success ==========");
			loginAdminSuccess("admin@admin.com", "admin");
			System.out.println("=========================================");

			System.out.println("\n========== Admin Login Failed By Email ==========");
			loginAdminSuccess("admin@aaaa.com", "admin");
			System.out.println("=========================================");

			System.out.println("\n========== Admin Login Failed By Password ==========");
			loginAdminSuccess("admin@admin.com", "aaaa");
			System.out.println("=========================================");

			System.out.println("\n========== Admin getting and viewing all Companies Success Empty table ==========");
			viewAllCompanies();
			System.out.println("=========================================");

			System.out.println("\n========== Admin getting and viewing all customers Success Empty table ==========");
			viewAllCustomers();
			System.out.println("=========================================");

			System.out.println("\n========== Admin Adding Company Success ==========");
			addCompany(getTestCompanies().get(0));
			System.out.println("=========================================");

			System.out.println("\n========== Admin Adding Company failed by name ==========");
			addCompany(getTestCompanies().get(1));
			System.out.println("=========================================");

			System.out.println("\n========== Admin Adding Company failed by email ==========");
			addCompany(getTestCompanies().get(2));
			System.out.println("=========================================");

			System.out.println("\n========== Admin Adding Different Company Success ==========");
			addCompany(getTestCompanies().get(3));
			System.out.println("=========================================");

			System.out.println("\n========== Admin Adding Different Company with the same password Success ==========");
			addCompany(getTestCompanies().get(4));
			System.out.println("=========================================");

			System.out.println("\n========== Admin getting and viewing all Companies Success ==========");
			viewAllCompanies();
			System.out.println("=========================================");

			System.out.println("\n========== Admin Update Company Password OR Email Success ==========");
			updateCompany(getTestCompanies().get(5));
			System.out.println("=========================================");

			System.out.println("\n========== Admin Update Company Name Fail ==========");
			updateCompany(getTestCompanies().get(6));
			System.out.println("=========================================");

			System.out.println("\n========== Admin Update Company With Existed Email Fail  ==========");
			updateCompany(getTestCompanies().get(7));
			System.out.println("=========================================");
			
			System.out.println("\n========== Admin Update Company With UnExisted Id Fail ==========");
			updateCompany(getTestCompanies().get(8));
			System.out.println("=========================================");

			System.out.println("\n========== Admin delete Company By Id Success ==========");
			deleteCompany(1);
			System.out.println("=========================================");

			System.out.println("\n========== Admin delete Company With UnExisted ID Fail ==========");
			deleteCompany(4);
			System.out.println("=========================================");

			System.out.println("\n========== Admin Adding Customer Success ==========");
			addCustomer(getTestCustomers().get(0));
			System.out.println("=========================================");

			System.out.println("\n========== Admin Adding Customer With The Same Email Fail ==========");
			addCustomer(getTestCustomers().get(1));
			System.out.println("=========================================");

			System.out.println("\n========== Admin Adding Different Customer Success ==========");
			addCustomer(getTestCustomers().get(2));
			System.out.println("=========================================");
			
			System.out.println("\n========== Admin Adding Different Customer Success ==========");
			addCustomer(getTestCustomers().get(3));
			System.out.println("=========================================");
			
			System.out.println("\n========== Admin Update customer success ==========");
			updateCustomrs(getTestCustomers().get(4));
			System.out.println("=========================================");
			
			System.out.println("\n========== Admin Update customer with different index failed ==========");
			updateCustomrs(getTestCustomers().get(4));
			System.out.println("=========================================");
			
			System.out.println("\n========== Admin Update customer With Existed Email Fail ==========");
			updateCustomrs(getTestCustomers().get(5));
			System.out.println("=========================================");
			
			System.out.println("\n========== Admin Update customer With UnExist Id Fail ==========");
			updateCustomrs(getTestCustomers().get(6));
			System.out.println("=========================================");
			
			System.out.println("\n========== Admin delete customer By Id Success ==========");
			deleteCustomer(2);
			System.out.println("=========================================");

			System.out.println("\n========== Admin delete customer With UnExisted Id Fail ==========");
			deleteCustomer(5);
			System.out.println("=========================================");

			System.out.println("\n========== Admin getting and viewing all customers Success ==========");
			viewAllCustomers();
			System.out.println("=========================================");

			System.out.println("\n========== Admin getting and viewing all Companies Success ==========");
			viewAllCompanies();
			System.out.println("=========================================");
			
			System.out.println("\n========== Admin getting and viewing company Success ==========");
			viewCompanyById(3);
			System.out.println("=========================================");

			System.out.println("\n========== Admin getting and viewing all customer Success ==========");
			viewCustomerById(3);
			System.out.println("=========================================");
			
			System.out.println("\n========== Admin getting and viewing company Failed Not Found ==========");
			viewCompanyById(1);
			System.out.println("=========================================");

			System.out.println("\n========== Admin getting and viewing all customer Failed Not Found  ==========");
			viewCustomerById(2);
			System.out.println("=========================================");

		} catch (FacadeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ConnectionPool.getInstance().closeAllConnections();
			} catch (ConnectionPoolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static void restoreCompaniesTableToDefault() {

		Connection con = null;
		ConnectionPool connectionPool = null;

		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.getConnection();

			String sqlDelete = "delete from companies";
			Statement pstmt1 = con.createStatement();
			pstmt1.executeUpdate(sqlDelete);
			System.out.println(sqlDelete);

			String sqlResetIncrement = "ALTER TABLE companies AUTO_INCREMENT = 1";
			Statement pstmt2 = con.createStatement();
			pstmt2.executeUpdate(sqlResetIncrement);
			System.out.println(sqlResetIncrement);

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

	private static void restoreCustomersTableToDefault() {

		Connection con = null;
		ConnectionPool connectionPool = null;

		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.getConnection();

			String sqlDelete = "delete from customers";
			Statement pstmt1 = con.createStatement();
			pstmt1.executeUpdate(sqlDelete);
			System.out.println(sqlDelete);

			String sqlResetIncrement = "ALTER TABLE customers AUTO_INCREMENT = 1";
			Statement pstmt2 = con.createStatement();
			pstmt2.executeUpdate(sqlResetIncrement);
			System.out.println(sqlResetIncrement);

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

	private static ArrayList<Company> getTestCompanies() {

		ArrayList<Company> initialCompanies = new ArrayList<Company>();

		initialCompanies.add(new Company(0, "aaa", "aaa@aaa.com", "aaa"));
		initialCompanies.add(new Company(1, "bbb", "aaa@aaa.com", "aaa"));
		initialCompanies.add(new Company(1, "aaa", "bbb@bbb.com", "aaa"));
		initialCompanies.add(new Company(0, "ddd", "ddd@ddd.com", "ddd"));
		initialCompanies.add(new Company(0, "eee", "eee@eee.com", "ddd"));
		initialCompanies.add(new Company(3, "eee", "eee1@eee1.com", "eee1"));
		initialCompanies.add(new Company(3, "ggg", "eee1@eee1.com", "eee1"));
		initialCompanies.add(new Company(1, "aaa", "eee1@eee1.com", "eee1"));
		initialCompanies.add(new Company(6, "aaa", "eee3@eee3.com", "eee1"));
		return initialCompanies;
	}

	private static ArrayList<Customer> getTestCustomers() {

		ArrayList<Customer> initialCustomers = new ArrayList<Customer>();

		initialCustomers.add(new Customer(0, "aaa", "aaa", "aaa@aaa.com", "aaa"));
		initialCustomers.add(new Customer(0, "bbb", "bbb", "aaa@aaa.com", "bbb"));
		initialCustomers.add(new Customer(0, "bbb", "bbb", "bbb@bbb.com", "bbb"));
		initialCustomers.add(new Customer(0, "ccc", "ccc", "ccc@ccc.com", "ccc"));
		initialCustomers.add(new Customer(2, "nnn", "nnn", "nnn@nnn.com", "nnn"));
		initialCustomers.add(new Customer(1, "nnn", "nnn", "nnn@nnn.com", "nnn"));
		initialCustomers.add(new Customer(1, "aaa", "aaa", "nnn@nnn.com", "aaa"));
		initialCustomers.add(new Customer(7, "aaa", "aaa", "nnn@nnn.com", "aaa"));

		return initialCustomers;
	}

	private static void loginAdminSuccess(String email, String password) {
		if (facade.login(email, password)) {
			System.out.println("admin facade login:\nemail: " + email + "\npassword: " + password + "\nTest: success");
		} else {
			System.out.println("admin facade login:\nemail: " + email + "\npassword: " + password + "\nTest: failed!");
		}
	}

	private static void addCompany(Company company) {
		System.out.println("admin facade addCompany: ");
		System.out.println("Company for add is: " + company);
		try {
			facade.addCompany(company);
			System.out.println("object company after addition: " + company);
			System.out.println("Database company by id after addition: " + facade.getCompany(company.getId()));
			System.out.println("Test: success");
		} catch (FacadeException e) {
			System.out.println("Test failed: " + e.getMessage());
		}
	}
	
	private static void addCustomer(Customer customer) {
		System.out.println("admin facade addCustomer: ");
		System.out.println("Customer for add is: " + customer);
		try {
			facade.addCustomer(customer);
			System.out.println("object company after addition: " + customer);
			System.out.println("Database company by id after addition: " + facade.getCustomerById(customer.getId()));
			System.out.println("Test: success");
		} catch (FacadeException e) {
			System.err.println("Test failed: " + e.getMessage());
		}
	}


	private static void viewAllCompanies() {

		try {
			ArrayList<Company> companies = facade.getAllCompanies();
			if (!companies.isEmpty()) {
				for (Company company : companies) {
					System.out.println(company);
				}
			} else {
				System.out.println("companies table is empty");
				System.out.println("Test: success");
			}

		} catch (FacadeException e) {
			System.err.println("Test failed: " + e.getMessage());
		}

	}

	private static void viewAllCustomers() {

		try {
			ArrayList<Customer> customers = facade.getAllCustomers();
			if (!customers.isEmpty()) {
				for (Customer customer : customers) {
					System.out.println(customer);
				}
			} else {
				System.out.println("customers table is empty");
				System.out.println("Test: success");
			}

		} catch (FacadeException e) {
			System.err.println("Test failed: " + e.getMessage());
		}

	}

	private static void viewCompanyById(int companyID) {
		try {
			System.out.println("Company id for view is: " + companyID);
			System.out.println("Company required from Database: " + facade.getCompany(companyID)); 
			System.out.println("Test: success");
		} catch (FacadeException e) {
			System.err.println("Test failed: " + e.getMessage());
		}
	}
	
	private static void viewCustomerById(int CustomerID) {
		try {
			System.out.println("Customer id for view is: " + CustomerID);
			System.out.println("Customer required from Database: " + facade.getCustomerById(CustomerID)); 
			System.out.println("Test: success");
		} catch (FacadeException e) {
			System.err.println("Test failed: " + e.getMessage());
		}
	}

	private static void updateCompany(Company company) {
		System.out.println("admin facade update company:");
		System.out.println("company for update is: " + company);
		try {
			facade.updateCompany(company);
			System.out.println("object company after updating: " + company);
			System.out.println("Database company by id after updating: " + facade.getCompany(company.getId()));
			System.out.println("Test: success");
		} catch (FacadeException e) {
			System.err.println("Test failed: " + e.getMessage());
		}
	}

	private static void updateCustomrs(Customer customer) {
		System.out.println("admin facade update customer:");
		System.out.println("customer for update is: " + customer);
		try {
			facade.updateCustomer(customer);
			System.out.println("object customer after updating: " + customer);
			System.out.println("Database customer by id after updating: " + facade.getCustomerById(customer.getId()));
			System.out.println("Test: success");
		} catch (FacadeException e) {
			System.err.println("Test failed: " + e.getMessage());
		}
	}

	private static void deleteCompany(int companyID) {
		System.out.println("admin facade delete company:");

		try {
			System.out.println("company Id for delete is: " + companyID);
			facade.deleteCompany(companyID);
			System.out.println("Database companies after deletion: ");
			viewAllCompanies();
			System.out.println("Test: success");
		} catch (FacadeException e) {
			System.out.println("Test failed: " + e.getMessage() + " id = " + companyID);
		}

	}
	
	private static void deleteCustomer(int CustomerID) {
		System.out.println("admin facade delete company:");

		try {
			System.out.println("Customer Id for delete is: " + CustomerID);
			facade.deleteCustomer(CustomerID);
			System.out.println("Database companies after deletion: ");
			viewAllCustomers();
			System.out.println("Test: success");
		} catch (FacadeException e) {
			System.out.println("Test failed: " + e.getMessage() + " id = " + CustomerID);
		}

	}
	
}
