package main.core.dao.impel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.core.beans.Company;
import main.core.dao.CompaniesDAO;
import main.core.db.ConnectionPool;
import main.core.exceptions.ConnectionPoolException;
import main.core.exceptions.DAOException;

public class CompaniesDBDAO implements CompaniesDAO {

	private ConnectionPool connectionPool;

	public CompaniesDBDAO() throws DAOException {
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: initializing companies failed", e);
		}
	}

	@Override
	public boolean isCompanyExistByEmailAndPassword(String email, String password) throws DAOException {

		boolean isExist = false;
		Connection con = null;

		try {
			con = connectionPool.getConnection();
			String sql = "select * from companies where email = ? AND password = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				isExist = true;
			}
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
			throw new DAOException(
					"DAO Error: checking existence failed: failed to aquire connection from connection pool", e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: checking existence failed: failed to aquire company from SQL", e);
		} finally {
			connectionPool.restoreConnection(con);
		}

		return isExist;

	}

	@Override
	public boolean isCompanyExistByEmail(String companyEmail) throws DAOException {
		boolean isExist = false;
		Connection con = null;

		try {
			con = connectionPool.getConnection();
			String sql = "select * from companies where email = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, companyEmail);
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
	public boolean isCompanyExistByName(String companyName) throws DAOException {
		boolean isExist = false;
		Connection con = null;

		try {
			con = connectionPool.getConnection();
			String sql = "select * from companies where name = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, companyName);
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
	public void addCompany(Company company) throws DAOException {

		Connection con = null;

		try {
			con = connectionPool.getConnection();
			String sql = "insert into companies values(?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "default");
			pstmt.setString(2, company.getName());
			pstmt.setString(3, company.getEmail());
			pstmt.setString(4, company.getPassword());
			pstmt.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: adding company failed", e);
		} finally {

			connectionPool.restoreConnection(con);
		}

	}

	@Override
	public void updateCompany(Company company) throws DAOException {

		Connection con = null;

		try {
			con = connectionPool.getConnection();
			String sql = "update companies set email=?, password=? where id =?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, company.getEmail());
			pstmt.setString(2, company.getPassword());
			pstmt.setInt(3, company.getId());
			pstmt.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: updating company failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}

	}

	@Override
	public void deleteCompany(int companyID) throws DAOException {

		Connection con = null;

		try {
			con = connectionPool.getConnection();
			String sql = "delete from companies where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, companyID);
			pstmt.executeUpdate();

		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: deleting company failed", e);
		} finally {

			connectionPool.restoreConnection(con);
		}

	}

	@Override
	public ArrayList<Company> getAllCompanies() throws DAOException {

		Connection con = null;
		ArrayList<Company> companies = new ArrayList<Company>();
		try {
			con = connectionPool.getConnection();
			String sql = "select * from companies";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				while (rs.next()) {
					Company company = new Company();
					company.setId(rs.getInt("id"));
					company.setName(rs.getString("name"));
					company.setEmail(rs.getString("email"));
					company.setPassword(rs.getString("password"));
					companies.add(company);
				}
			} else {
				return null;
			}

		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: getting all companies failed", e);
		} finally {

			connectionPool.restoreConnection(con);
		}

		return companies;
	}

	@Override
	public Company getCompanyById(int companyID) throws DAOException {

		Connection con = null;
		Company company;

		try {
			con = connectionPool.getConnection();
			String sql = "select * from companies where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, companyID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				company = new Company(companyID);
				company.setName(rs.getString("name"));
				company.setEmail(rs.getString("email"));
				company.setPassword(rs.getString("password"));
			} else {
				return null;
			}

		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: getting company failed", e);
		} finally {
			connectionPool.restoreConnection(con);

		}

		return company;

	}

	@Override
	public Company getCompanyByEmailAndPassword(String email, String password) throws DAOException {

		Connection con = null;
		Company company;

		try {
			con = connectionPool.getConnection();
			String sql = "select id from companies where email = ? AND password = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				company = new Company();
				company.setId(rs.getInt("id"));
				company.setName(rs.getString("name"));
				company.setEmail(rs.getString("email"));
				company.setPassword(rs.getString("password"));
			} else {
				return null;
			}

		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: getting company failed", e);
		} finally {

			connectionPool.restoreConnection(con);
		}

		return company;
	}

	

}
