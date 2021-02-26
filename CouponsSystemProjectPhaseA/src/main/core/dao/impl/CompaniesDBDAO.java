package main.core.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.core.beans.Company;
import main.core.connectionDB.ConnectionPool;
import main.core.dao.CompaniesDAO;
import main.core.exceptions.ConnectionPoolException;
import main.core.exceptions.DAOException;

public class CompaniesDBDAO implements CompaniesDAO {

    @Override
    public boolean isCompanyExistByEmailAndPassword(String email, String password) throws DAOException {
        boolean isExist = false;
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "select * from companies where email = ? AND password = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                isExist = true;
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: isCompanyExistByEmailAndPassword failed,\n" + e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: isCompanyExistByEmailAndPassword failed,\n" + e.getMessage());
            }
        }
        return isExist;
    }

    @Override
    public boolean isCompanyExistByEmail(String companyEmail) throws DAOException {
        boolean isExist = false;
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "select * from companies where email = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, companyEmail);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                isExist = true;
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: isCompanyExistByEmail failed,\n" + e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: isCompanyExistByEmail failed,\n" + e.getMessage());
            }
        }
        return isExist;
    }

    @Override
    public boolean isCompanyExistByName(String companyName) throws DAOException {
        boolean isExist = false;
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "select * from companies where name = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, companyName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                isExist = true;
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: isCompanyExistByName failed,\n" + e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: isCompanyExistByName failed,\n" + e.getMessage());
            }
        }
        return isExist;
    }

    @Override
    public void addCompany(Company company) throws DAOException {
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "insert into companies (name, email, password) values(?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, company.getName());
            pstmt.setString(2, company.getEmail());
            pstmt.setString(3, company.getPassword());
            pstmt.executeUpdate();
            ResultSet resKeys = pstmt.getGeneratedKeys();
			resKeys.next();
			company.setId(resKeys.getInt(1));
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: addCompany failed,\n" + e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: addCompany failed,\n" + e.getMessage());
            }
        }
    }

    @Override
    public void updateCompany(Company company) throws DAOException {
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "update companies set email=?, password=? where id =?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, company.getEmail());
            pstmt.setString(2, company.getPassword());
            pstmt.setInt(3, company.getId());
            pstmt.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: updateCompany failed,\n" + e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: updateCompany failed,\n" + e.getMessage());
            }
        }

    }

    @Override
    public void deleteCompany(int companyID) throws DAOException {
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "delete from companies where id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, companyID);
            pstmt.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: deleteCompany failed,\n" + e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: deleteCompany failed,\n" + e.getMessage());
            }
        }
    }

    @Override
    public ArrayList<Company> getAllCompanies() throws DAOException {
        Connection con = null;
        ArrayList<Company> companies = new ArrayList<Company>();
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "select * from companies";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Company company = new Company(rs.getInt("id"));
                company.setName(rs.getString("name"));
                company.setEmail(rs.getString("email"));
                company.setPassword(rs.getString("password"));
                companies.add(company);
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: getAllCompanies failed,\n" + e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: getAllCompanies failed,\n" + e.getMessage());
            }
        }
        return companies;
    }

    @Override
    public Company getCompanyById(int companyID) throws DAOException {
        Connection con = null;
        Company company = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "select * from companies where id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, companyID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                company = new Company(companyID);
                company.setName(rs.getString("name"));
                company.setEmail(rs.getString("email"));
                company.setPassword(rs.getString("password"));
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: getCompanyById failed,\n" + e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: getCompanyById failed,\n" + e.getMessage());
            }
        }
        return company;
    }

    @Override
    public Company getCompanyByEmailAndPassword(String email, String password) throws DAOException {
        Connection con = null;
        Company company = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "select * from companies where email = ? AND password = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                company = new Company(rs.getInt("id"));
                company.setName(rs.getString("name"));
                company.setEmail(rs.getString("email"));
                company.setPassword(rs.getString("password"));
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: getCompanyByEmailAndPassword failed,\n" + e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: getCompanyByEmailAndPassword failed,\n" + e.getMessage());
            }
        }
        return company;
    }
}
