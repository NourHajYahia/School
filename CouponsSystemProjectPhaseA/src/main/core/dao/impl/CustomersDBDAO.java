package main.core.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.core.beans.Customer;
import main.core.connectionDB.ConnectionPool;
import main.core.dao.CustomersDAO;
import main.core.exceptions.ConnectionPoolException;
import main.core.exceptions.DAOException;

public class CustomersDBDAO implements CustomersDAO {

    @Override
    public boolean isCustomerExists(String email, String password) throws DAOException {
        boolean isExist = false;
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
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
            throw new DAOException("DAO Error: isCustomerExists failed,\n" +  e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                e.printStackTrace();
                throw new DAOException("DAO Error: isCustomerExists failed,\n" +  e.getMessage());
            }
        }
        return isExist;
    }

    @Override
    public boolean isCustomerExistsByEmail(String email) throws DAOException {
        boolean isExist = false;
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "select * from customers where email = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                isExist = true;
            }
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
            throw new DAOException("DAO Error: isCustomerExistsByEmail failed,\n" +  e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                e.printStackTrace();
                throw new DAOException("DAO Error: isCustomerExistsByEmail failed,\n" +  e.getMessage());
            }
        }
        return isExist;
    }

    @Override
    public void addCustomer(Customer customer) throws DAOException {
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "insert into customers values(?,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, customer.getId());
            pstmt.setString(2, customer.getFirstName());
            pstmt.setString(3, customer.getLastName());
            pstmt.setString(4, customer.getEmail());
            pstmt.setString(5, customer.getPassword());
            pstmt.executeUpdate();
            ResultSet resKeys = pstmt.getGeneratedKeys();
			resKeys.next();
			customer.setId(resKeys.getInt(1));
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
            throw new DAOException("DAO Error: addCustomer failed,\n" +  e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                e.printStackTrace();
                throw new DAOException("DAO Error: addCustomer failed,\n" +  e.getMessage());
            }
        }
    }

    @Override
    public void updateCustomer(Customer customer) throws DAOException {
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "update customers set first_name=?, last_name=?, email=?, password=? where id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPassword());
            pstmt.setInt(5, customer.getId());
            pstmt.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
            throw new DAOException("DAO Error: updateCustomer failed,\n" +  e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                e.printStackTrace();
                throw new DAOException("DAO Error: updateCustomer failed,\n" +  e.getMessage());
            }
        }
    }

    @Override
    public void deleteCustomer(int customerID) throws DAOException {
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "delete from customers where id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, customerID);
            pstmt.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
            throw new DAOException("DAO Error: deleteCustomer failed,\n" +  e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                e.printStackTrace();
                throw new DAOException("DAO Error: deleteCustomer failed,\n" +  e.getMessage());
            }
        }
    }

    @Override
    public ArrayList<Customer> getAllCustomers() throws DAOException {
        Connection con = null;
        ArrayList<Customer> customers = new ArrayList<Customer>();;
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "select * from customers";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer(rs.getInt("id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPassword(rs.getString("password"));
                customers.add(customer);
            }
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
            throw new DAOException("DAO Error: getAllCustomers failed,\n" +  e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                e.printStackTrace();
                throw new DAOException("DAO Error: getAllCustomers failed,\n" +  e.getMessage());
            }
        }
        return customers;
    }

    @Override
    public Customer getCustomerById(int customerID) throws DAOException {
        Connection con = null;
        Customer customer = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "select * from customers where id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, customerID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                customer = new Customer(customerID);
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPassword(rs.getString("password"));
            }
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
            throw new DAOException("DAO Error: getCustomerById failed,\n" +  e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                e.printStackTrace();
                throw new DAOException("DAO Error: getCustomerById failed,\n" +  e.getMessage());
            }
        }
        return customer;
    }

    @Override
    public Customer getCustomerByEmailAndPassword(String email, String password) throws DAOException {
        Connection con = null;
        Customer customer = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "select * from customers where email = ? and password = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                customer = new Customer(rs.getInt("id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPassword(rs.getString("password"));
            }
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
            throw new DAOException("DAO Error: getCustomerByEmailAndPassword failed,\n" +  e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                e.printStackTrace();
                throw new DAOException("DAO Error: getCustomerByEmailAndPassword failed,\n" +  e.getMessage());
            }
        }
        return customer;
    }

}
