package app.core.dao.impel;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import app.core.beans.Category;
import app.core.beans.Coupon;
import app.core.dao.CouponsDAO;
import app.core.db.ConnectionPool;
import app.core.exceptions.ConnectionPoolException;
import app.core.exceptions.DAOException;

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
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, coupon.getId());
			pstmt.setInt(2, coupon.getCompanyID());
			pstmt.setInt(3, coupon.getCategory().ordinal());

			// adding new category if it not exists.
			addCategory(coupon.getCategory());

			pstmt.setString(4, coupon.getTitle());
			pstmt.setString(5, coupon.getDescription());
			pstmt.setDate(6, Date.valueOf(coupon.getStartDate()));
			pstmt.setDate(7, Date.valueOf(coupon.getEndDate()));
			pstmt.setInt(8, coupon.getAmount());
			pstmt.setDouble(9, coupon.getPrice());
			pstmt.setString(10, coupon.getImage());
			pstmt.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: adding coupon failed", e);
		} finally {
			connectionPool.restoreConnection(con);
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
			connectionPool.restoreConnection(con);
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
			connectionPool.restoreConnection(con);

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
			connectionPool.restoreConnection(con);
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
			connectionPool.restoreConnection(con);
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
			connectionPool.restoreConnection(con);
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
			connectionPool.restoreConnection(con);
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
			connectionPool.restoreConnection(con);
		}

	}

}
