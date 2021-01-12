package test.facade;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.core.beans.Company;
import main.core.clientsFacade.AdminFacade;
import main.core.db.ConnectionPool;
import main.core.exceptions.ConnectionPoolException;
import main.core.exceptions.FacadeException;

public class AdminFacadeTestSuccess {

	static AdminFacade facade;
	static Company company;

	public static void main(String[] args) {

		try {
			facade = new AdminFacade();
			dropAndCreateCompanyTable();
			System.out.println("========== Admin Login Success ==========");
			loginAdminSuccess();
			System.out.println("=========================================");
			
			System.out.println("\n========== Admin Login Failed By Email ==========");
			loginAdminFailedEmail();
			System.out.println("=========================================");
			
			System.out.println("\n========== Admin Login Failed By Password ==========");
			loginAdminFailedPassword();
			System.out.println("=========================================");
			
			System.out.println("\n========== Admin getting and viewing all Companies Success Empty table ==========");
			viewAllCompanies();
			System.out.println("=========================================");
			
			System.out.println("\n========== Admin Adding Company Success ==========");
			addCompany(getTestCompanies().get(0));
			System.out.println("=========================================");
			
			System.out.println("\n========== Admin Adding Company failed by name ==========");
			addCompanyWithTheSameName(getTestCompanies().get(1));
			System.out.println("=========================================");
			
			System.out.println("\n========== Admin Adding Company failed by email ==========");
			addCompanyWithTheSameEmail(getTestCompanies().get(2));
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
		} catch (FacadeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void dropAndCreateCompanyTable() {

		Connection con = null;
		ConnectionPool connectionPool = null;

		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.getConnection();

			String sqlDrop = "drop table companies";
			Statement pstmt = con.createStatement();
			pstmt.executeUpdate(sqlDrop);
			System.out.println(sqlDrop);

			connectionPool.restoreConnection(con);

			} catch (ConnectionPoolException | SQLException e) {
				e.printStackTrace();
			}

		try {
			connectionPool = ConnectionPool.getInstance();
			String sqlCreate = "create table COMPANIES ("
					+ "ID int not null primary key auto_increment,"
					+ "NAME varchar(255) not null unique," 
					+ "EMAIL varchar(255) not null unique,"
					+ "PASSWORD varchar(255)" + ")";
			Statement pstmt = con.createStatement();
			pstmt.executeUpdate(sqlCreate);
			System.out.println(sqlCreate);
		} catch (SQLException | ConnectionPoolException e) {
			e.printStackTrace();
		}
	}

	private static ArrayList<Company> getTestCompanies() {

		ArrayList<Company> initialCompanies = new ArrayList<Company>();

		initialCompanies.add(new Company(0, "aaa", "aaa@aaa.com", "aaa"));
		initialCompanies.add(new Company(0, "bbb", "aaa@aaa.com", "aaa"));
		initialCompanies.add(new Company(0, "aaa", "bbb@bbb.com", "aaa"));
		initialCompanies.add(new Company(0, "ddd", "ddd@ddd.com", "ddd"));
		initialCompanies.add(new Company(0, "eee", "eee@eee.com", "ddd"));
		initialCompanies.add(new Company(0, "fff", "fff@fff.com", "fff"));
		initialCompanies.add(new Company(0, "ggg", "ggg@ggg.com", "ggg"));
		initialCompanies.add(new Company(0, "hhh", "hhh@hhh.com", "hhh"));

		return initialCompanies;
	}

	private static void loginAdminSuccess() {
		String email = "admin@admin.com";
		String password = "admin";

		if (facade.login(email, password)) {
			System.out.println("admin facade login:\nemail: " + email + "\npassword: " + password + "\nTest: success");
		} else {
			System.out.println("admin facade login:\nemail: " + email + "\npassword: " + password + "\nTest: failed!");
		}
	}

	private static void loginAdminFailedEmail() {
		String email = "admin@aaa.com";
		String password = "admin";

		if (facade.login(email, password)) {
			System.out.println("admin facade login:\nemail: " + email + "\npassword: " + password + "\nTest: success");
		} else {
			System.out.println("admin facade login:\nemail: " + email + "\npassword: " + password + "\nTest: failed!");
		}
	}

	private static void loginAdminFailedPassword() {
		String email = "admin@admin.com";
		String password = "aaa";

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
			System.out.println("Company after addition updated id: " + company);
			System.out.println("Test: success");
		} catch (FacadeException e) {
			System.out.println("Test failed: " + e.getMessage());
		}
	}

	private static void addCompanyWithTheSameName(Company company) {
		System.out.println("admin facade addCompany: ");
		System.out.println("Company for add is: " + company);
		try {
			facade.addCompany(company);
			System.out.println("Test: success");
		} catch (FacadeException e) {
			System.out.println("Test failed: " + e.getMessage());
		}
	}

	private static void addCompanyWithTheSameEmail(Company company) {
		System.out.println("admin facade addCompany: ");
		System.out.println("Company for add is: " + company);
		try {
			facade.addCompany(company);
			System.out.println("Test: success");
		} catch (FacadeException e) {
			System.out.println("Test failed: " + e.getMessage());
		}
	}
	
	private static void viewAllCompanies() {
		
		try {
			ArrayList<Company> companies = facade.getAllCompanies();
			if (companies != null) {
				for (Company company : companies) {
					System.out.println(company);
				}
			}else {
				System.out.println("Company table is empty");
			}
			
		} catch (FacadeException e) {
			System.out.println("Test failed: " + e.getMessage());
		}
		
	}

}
