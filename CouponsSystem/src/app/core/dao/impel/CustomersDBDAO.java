package app.core.dao.impel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import app.core.beans.Customer;
import app.core.dao.CustomersDAO;
import app.core.db.ConnectionPool;
import app.core.exceptions.ConnectionPoolException;
import app.core.exceptions.DAOException;

public class CustomersDBDAO implements CustomersDAO {

	private ConnectionPool connectionPool;

	public CustomersDBDAO() throws DAOException {
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: initializing customers failed", e);
		}
	}

	@Override
	public boolean isCustomerExists(String email, String password) throws DAOException {
		boolean isExist = false;
		Connection con = null;

		try {
			con = connectionPool.getConnection();
			String sql = "select * from customers where email = ? AND password = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				isExist = true;
			}
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: checking existence failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}

		return isExist;
	}

	@Override
	public boolean isCustomerExistsByEmail(String email) throws DAOException {
		boolean isExist = false;
		Connection con = null;

		try {
			con = connectionPool.getConnection();
			String sql = "select * from customers where email = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				isExist = true;
			}
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: checking existence failed", e);
		} finally {
			connectionPool.restoreConnection(con);			
		}

		return isExist;
	}

	@Override
	public void addCustomer(Customer customer) throws DAOException {

		Connection con = null;

		try {
			con = connectionPool.getConnection();
			String sql = "insert into customers values(?,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, customer.getId());
			pstmt.setString(2, customer.getFirstName());
			pstmt.setString(3, customer.getLastName());
			pstmt.setString(4, customer.getEmail());
			pstmt.setString(5, customer.getPassword());
			pstmt.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: adding company failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}

	}

	@Override
	public void updateCustomer(Customer customer) throws DAOException {
		Connection con = null;

		try {
			con = connectionPool.getConnection();
			String sql = "update customers set firstname=?, lastname=?, email=?, password=? where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, customer.getFirstName());
			pstmt.setString(2, customer.getLastName());
			pstmt.setString(3, customer.getEmail());
			pstmt.setString(4, customer.getPassword());
			pstmt.setInt(5, customer.getId());
			pstmt.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: updating company failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}

	}

	@Override
	public void deleteCustomer(int customerID) throws DAOException {

		Connection con = null;

		try {
			con = connectionPool.getConnection();
			String sql = "delete from customers where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, customerID);
			pstmt.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: deleting company failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}

	}

	@Override
	public ArrayList<Customer> getAllCustomers() throws DAOException {

		Connection con = null;
		ArrayList<Customer> customers = new ArrayList<Customer>();
		try {
			con = connectionPool.getConnection();
			String sql = "select * from customers";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				while (rs.next()) {
					Customer customer = new Customer();
					customer.setId(rs.getInt("id"));
					customer.setFirstName(rs.getString("firstname"));
					customer.setLastName(rs.getString("lastname"));
					customer.setEmail(rs.getString("email"));
					customer.setPassword(rs.getString("password"));
					customers.add(customer);
				}
			}

		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: getting all companies failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}

		return customers;

	}

	@Override
	public Customer getCustomerById(int customerID) throws DAOException {

		Connection con = null;
		Customer customer;

		try {
			con = connectionPool.getConnection();
			String sql = "select * from customers where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, customerID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				customer = new Customer(customerID);
				customer.setFirstName(rs.getString("firstname"));
				customer.setLastName(rs.getString("lastname"));
				customer.setEmail(rs.getString("email"));
				customer.setPassword(rs.getString("password"));
			} else {
				return null;
			}
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: getting company failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}

		return customer;

	}
	
	@Override
	public Customer getCustomerByEmail(String email) throws DAOException {

		Connection con = null;
		Customer customer;

		try {
			con = connectionPool.getConnection();
			String sql = "select * from customers where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				customer = new Customer();
				customer.setFirstName(rs.getString("firstname"));
				customer.setLastName(rs.getString("lastname"));
				customer.setEmail(rs.getString("email"));
				customer.setPassword(rs.getString("password"));
			} else {
				return null;
			}
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: getting company failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}

		return customer;

	}

}
