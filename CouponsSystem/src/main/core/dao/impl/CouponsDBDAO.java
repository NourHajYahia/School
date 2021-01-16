package main.core.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import main.core.beans.Category;
import main.core.beans.Coupon;
import main.core.dao.CouponsDAO;
import main.core.db.ConnectionPool;
import main.core.exceptions.ConnectionPoolException;
import main.core.exceptions.DAOException;

public class CouponsDBDAO implements CouponsDAO {

	private ConnectionPool connectionPool;

	public CouponsDBDAO() throws DAOException {
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: initializing coupon failed", e);
		}
	}

	@Override
	public void addCoupon(Coupon coupon) throws DAOException {

		Connection con = null;

		try {
			con = connectionPool.getConnection();
			String sql = "insert into coupons values(?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, coupon.getId());
			pstmt.setInt(2, coupon.getCompanyID());
			pstmt.setInt(3, coupon.getCategory().ordinal());
			pstmt.setString(4, coupon.getTitle());
			pstmt.setString(5, coupon.getDescription());
			pstmt.setDate(6, Date.valueOf(coupon.getStartDate()));
			pstmt.setDate(7, Date.valueOf(coupon.getEndDate()));
			pstmt.setInt(8, coupon.getAmount());
			pstmt.setDouble(9, coupon.getPrice());
			pstmt.setString(10, coupon.getImage());
			pstmt.executeUpdate();
			ResultSet resKeys = pstmt.getGeneratedKeys();
			resKeys.next();
			coupon.setId(resKeys.getInt(1));
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: adding coupon failed", e);
		} finally {
			try {
				connectionPool.restoreConnections(con);
			} catch (ConnectionPoolException e) {
				e.printStackTrace();
				throw new DAOException("DAO Error: adding coupon failed", e);
			}
		}

	}

	@Override
	public void updateCoupon(Coupon coupon) throws DAOException {

		Connection con = null;

		try {
			con = connectionPool.getConnection();
			String sql = "update coupons set company_id=?, category_id=?, title=?, description=?, start_date=?, end_date=?, amount=?, price=?, image=? where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, coupon.getCompanyID());
			pstmt.setInt(2, coupon.getCategory().ordinal());

			// adding new category if it not exists.
			// addCategory(coupon.getCategory());

			pstmt.setString(3, coupon.getTitle());
			pstmt.setString(4, coupon.getDescription());
			pstmt.setDate(5, Date.valueOf(coupon.getStartDate()));
			pstmt.setDate(6, Date.valueOf(coupon.getEndDate()));
			pstmt.setInt(7, coupon.getAmount());
			pstmt.setDouble(8, coupon.getPrice());
			pstmt.setString(9, coupon.getImage());
			pstmt.setInt(10, coupon.getId());
			pstmt.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: updating coupon failed", e);
		} finally {
			try {
				connectionPool.restoreConnections(con);
			} catch (ConnectionPoolException e) {
				e.printStackTrace();
				throw new DAOException("DAO Error: updating coupon failed", e);
			}
		}

	}

	@Override
	public void deleteCoupon(int couponID) throws DAOException {

		Connection con = null;

		try {
			con = connectionPool.getConnection();
			String sql = "delete from coupons where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, couponID);
			pstmt.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: deleting coupon failed", e);
		} finally {
			try {
				connectionPool.restoreConnections(con);
			} catch (ConnectionPoolException e) {
				e.printStackTrace();
				throw new DAOException("DAO Error: deleting coupon failed", e);
			}

		}

	}

	@Override
	public ArrayList<Coupon> getAllCoupons() throws DAOException {

		Connection con = null;
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		try {
			con = connectionPool.getConnection();
			String sql = "select * from coupons";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				while (rs.next()) {
					Coupon coupon = new Coupon();
					coupon.setId(rs.getInt("id"));
					coupon.setCompanyID(rs.getInt("company_id"));
					coupon.setCategory(Category.values()[rs.getInt("category_id")]);
					coupon.setTitle(rs.getString("title"));
					coupon.setDescription(rs.getString("description"));
					coupon.setStartDate(LocalDate.parse(rs.getString("start_date")));
					coupon.setEndDate(LocalDate.parse(rs.getString("end_date")));
					coupon.setAmount(rs.getInt("amount"));
					coupon.setPrice(rs.getInt("price"));
					coupon.setImage(rs.getString("image"));
					coupons.add(coupon);
				}
			} else {
				return null;
			}

		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: getting all coupons failed", e);
		} finally {
			try {
				connectionPool.restoreConnections(con);
			} catch (ConnectionPoolException e) {
				e.printStackTrace();
				throw new DAOException("DAO Error: getting all coupons failed", e);
			}
		}

		return coupons;

	}

	@Override
	public ArrayList<Coupon> getAllCouponsByCompanyId(int companyID) throws DAOException {

		Connection con = null;
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		try {
			con = connectionPool.getConnection();
			String sql = "select * from coupons where company_id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, companyID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setCompanyID(rs.getInt("company_id"));
				coupon.setCategory(Category.values()[rs.getInt("category_id")]);
				coupon.setTitle(rs.getString("title"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStartDate(LocalDate.parse(rs.getString("start_date")));
				coupon.setEndDate(LocalDate.parse(rs.getString("end_date")));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getInt("price"));
				coupon.setImage(rs.getString("image"));
				coupons.add(coupon);
			}

		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: getting company coupons failed", e);
		} finally {
			try {
				connectionPool.restoreConnections(con);
			} catch (ConnectionPoolException e) {
				e.printStackTrace();
				throw new DAOException("DAO Error: getting company coupons failed", e);
			}
		}

		return coupons;
	}

	@Override
	public ArrayList<Coupon> getAllCouponsByCompanyIdAndCategory(int companyID, int categoryID) throws DAOException {

		Connection con = null;
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		try {
			con = connectionPool.getConnection();
			String sql = "select * from coupons where company_id = ? and category_id =?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, companyID);
			pstmt.setInt(2, categoryID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setCompanyID(rs.getInt("company_id"));
				coupon.setCategory(Category.values()[rs.getInt("category_id")]);
				coupon.setTitle(rs.getString("title"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStartDate(LocalDate.parse(rs.getString("start_date")));
				coupon.setEndDate(LocalDate.parse(rs.getString("end_date")));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getInt("price"));
				coupon.setImage(rs.getString("image"));
				coupons.add(coupon);
			}

		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: getting company coupons by category failed", e);
		} finally {
			try {
				connectionPool.restoreConnections(con);
			} catch (ConnectionPoolException e) {
				e.printStackTrace();
				throw new DAOException("DAO Error: getting company coupons by category failed", e);
			}
		}

		return coupons;
	}

	@Override
	public ArrayList<Coupon> getAllCouponsByCompanyIdAndPrice(int companyID, double maxPrice) throws DAOException {

		Connection con = null;
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		try {
			con = connectionPool.getConnection();
			String sql = "select * from coupons where company_id = ? and price =?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, companyID);
			pstmt.setDouble(2, maxPrice);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setCompanyID(rs.getInt("company_id"));
				coupon.setCategory(Category.values()[rs.getInt("category_id")]);
				coupon.setTitle(rs.getString("title"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStartDate(LocalDate.parse(rs.getString("start_date")));
				coupon.setEndDate(LocalDate.parse(rs.getString("end_date")));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getInt("price"));
				coupon.setImage(rs.getString("image"));
				coupons.add(coupon);
			}

		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: getting company coupons by max price failed", e);
		} finally {
			try {
				connectionPool.restoreConnections(con);
			} catch (ConnectionPoolException e) {
				e.printStackTrace();
				throw new DAOException("DAO Error: getting company coupons by max price failed", e);
			}
		}

		return coupons;
	}

	@Override
	public Coupon getCouponById(int couponID) throws DAOException {

		Connection con = null;
		Coupon coupon;

		try {
			con = connectionPool.getConnection();
			String sql = "select * from coupons where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, couponID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				coupon = new Coupon(couponID);
				coupon.setCompanyID(rs.getInt("company_id"));
				coupon.setCategory(Category.values()[rs.getInt("category_id")]);
				coupon.setTitle(rs.getString("title"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStartDate(LocalDate.parse(rs.getString("start_date")));
				coupon.setEndDate(LocalDate.parse(rs.getString("end_date")));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getInt("price"));
				coupon.setImage(rs.getString("image"));
			} else {
				return null;
			}

		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: getting coupons failed", e);
		} finally {
			try {
				connectionPool.restoreConnections(con);
			} catch (ConnectionPoolException e) {
				e.printStackTrace();
				throw new DAOException("DAO Error: getting coupons failed", e);
			}
		}

		return coupon;

	}

	@Override
	public void addCouponPurchase(int customerID, int couponID) throws DAOException {

		Connection con = null;

		try {
			con = connectionPool.getConnection();
			String sql = "insert into customers_vs_coupons values(?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, customerID);
			pstmt.setInt(2, couponID);
			pstmt.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: adding coupon purchase failed", e);
		} finally {
			try {
				connectionPool.restoreConnections(con);
			} catch (ConnectionPoolException e) {
				e.printStackTrace();
				throw new DAOException("DAO Error: adding coupon purchase failed", e);
			}
		}

	}

	@Override
	public void deleteCouponPurchase(int couponID) throws DAOException {

		Connection con = null;

		try {
			con = connectionPool.getConnection();
			String sql = "delete from customers_vs_coupons where coupon_id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, couponID);
			pstmt.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: deleting coupon purchase failed", e);
		} finally {
			try {
				connectionPool.restoreConnections(con);
			} catch (ConnectionPoolException e) {
				e.printStackTrace();
				throw new DAOException("DAO Error: deleting coupon purchase failed", e);
			}
		}

	}

	@Override
	public void deleteCouponPurchase(int customerID, int couponID) throws DAOException {

		Connection con = null;

		try {
			con = connectionPool.getConnection();
			String sql = "delete from customers_vs_coupons where cutomer_id=? and coupon_id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, customerID);
			pstmt.setInt(2, couponID);
			pstmt.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: deleting coupon purchase failed", e);
		} finally {
			try {
				connectionPool.restoreConnections(con);
			} catch (ConnectionPoolException e) {
				e.printStackTrace();
				throw new DAOException("DAO Error: deleting coupon purchase failed", e);
			}
		}

	}

	@Override
	public void addCategory(Category category) throws DAOException {

		Connection con = null;

		try {
			con = connectionPool.getConnection();
			String sql = "insert into categories values(?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, category.ordinal());
			pstmt.setString(2, category.toString());
			pstmt.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: adding category failed", e);
		} finally {
			try {
				connectionPool.restoreConnections(con);
			} catch (ConnectionPoolException e) {
				e.printStackTrace();
				throw new DAOException("DAO Error: adding category failed", e);
			}
		}

	}

	@Override
	public boolean isCouponExistByTitleAndCompanyId(String couponTitle, int companyID) throws DAOException {
		boolean isExist = false;
		Connection con = null;

		try {
			con = connectionPool.getConnection();
			String sql = "select * from coupons where title = ? and company_id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, couponTitle);
			pstmt.setInt(2, companyID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				isExist = true;
			}
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: checking existence failed", e);
		} finally {

			try {
				connectionPool.restoreConnections(con);
			} catch (ConnectionPoolException e) {
				e.printStackTrace();
				throw new DAOException("DAO Error: checking existence failed", e);
			}
		}

		return isExist;
	}

	@Override
	public boolean isCatogeryExist(int categoryID) throws DAOException {
		boolean isExist = false;
		Connection con = null;

		try {
			con = connectionPool.getConnection();
			String sql = "select * from categories where id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, categoryID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				isExist = true;
			}
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: checking existence failed", e);
		} finally {

			try {
				connectionPool.restoreConnections(con);
			} catch (ConnectionPoolException e) {
				e.printStackTrace();
				throw new DAOException("DAO Error: checking existence failed", e);
			}
		}

		return isExist;
	}

	@Override
	public boolean isCouponPurchaseExistByCustomer(int customerID, int couponID) throws DAOException {

		boolean isExist = false;
		Connection con = null;

		try {
			con = connectionPool.getConnection();
			String sql = "select * from customers_vs_coupons where customer_id = ? and coupon_id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, couponID);
			pstmt.setInt(1, customerID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				isExist = true;
			}
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: checking existence failed", e);
		} finally {

			try {
				connectionPool.restoreConnections(con);
			} catch (ConnectionPoolException e) {
				e.printStackTrace();
				throw new DAOException("DAO Error: checking existence failed", e);
			}
		}

		return isExist;
	}

	@Override
	public ArrayList<Coupon> getAllCouponsByCustomerId(int customerID) throws DAOException {

		Connection con = null;
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		try {
			con = connectionPool.getConnection();
			String sql = "select * from coupons where id = (select coupon_id from customers_vs_coupons where customer_id = ?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, customerID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setCompanyID(rs.getInt("company_id"));
				coupon.setCategory(Category.values()[rs.getInt("category_id")]);
				coupon.setTitle(rs.getString("title"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStartDate(LocalDate.parse(rs.getString("start_date")));
				coupon.setEndDate(LocalDate.parse(rs.getString("end_date")));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getInt("price"));
				coupon.setImage(rs.getString("image"));
				coupons.add(coupon);
			}

		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: getting customer coupons failed", e);
		} finally {
			try {
				connectionPool.restoreConnections(con);
			} catch (ConnectionPoolException e) {
				e.printStackTrace();
				throw new DAOException("DAO Error: getting customer coupons failed", e);
			}
		}

		return coupons;
	}

	@Override
	public ArrayList<Coupon> getAllCouponsByCustomerIdAndCategory(int customerID, int categoryID) throws DAOException {

		Connection con = null;
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		try {
			con = connectionPool.getConnection();
			String sql = "select * from coupons where id = (select coupon_id from customers_vs_coupons where customer_id = ?) and category_id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, customerID);
			pstmt.setInt(2, categoryID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setCompanyID(rs.getInt("company_id"));
				coupon.setCategory(Category.values()[rs.getInt("category_id")]);
				coupon.setTitle(rs.getString("title"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStartDate(LocalDate.parse(rs.getString("start_date")));
				coupon.setEndDate(LocalDate.parse(rs.getString("end_date")));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getInt("price"));
				coupon.setImage(rs.getString("image"));
				coupons.add(coupon);
			}

		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: getting customer coupons failed", e);
		} finally {
			try {
				connectionPool.restoreConnections(con);
			} catch (ConnectionPoolException e) {
				e.printStackTrace();
				throw new DAOException("DAO Error: getting customer coupons failed", e);
			}
		}

		return coupons;
	}

	@Override
	public ArrayList<Coupon> getAllCouponsByCustomerIdAndMaxPrice(int customerID, double maxPrice) throws DAOException {

		Connection con = null;
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		try {
			con = connectionPool.getConnection();
			String sql = "select * from coupons where id = (select coupon_id from customers_vs_coupons where customer_id = ?) and price <= ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, customerID);
			pstmt.setDouble(2, maxPrice);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setCompanyID(rs.getInt("company_id"));
				coupon.setCategory(Category.values()[rs.getInt("category_id")]);
				coupon.setTitle(rs.getString("title"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStartDate(LocalDate.parse(rs.getString("start_date")));
				coupon.setEndDate(LocalDate.parse(rs.getString("end_date")));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getInt("price"));
				coupon.setImage(rs.getString("image"));
				coupons.add(coupon);
			}

		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: getting customer coupons failed", e);
		} finally {
			try {
				connectionPool.restoreConnections(con);
			} catch (ConnectionPoolException e) {
				e.printStackTrace();
				throw new DAOException("DAO Error: getting customer coupons failed", e);
			}
		}

		return coupons;
	}

}
