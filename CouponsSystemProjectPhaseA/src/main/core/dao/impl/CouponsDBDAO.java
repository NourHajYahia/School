package main.core.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import main.core.beans.Category;
import main.core.beans.Coupon;
import main.core.connectionDB.ConnectionPool;
import main.core.dao.CouponsDAO;
import main.core.exceptions.ConnectionPoolException;
import main.core.exceptions.DAOException;

public class CouponsDBDAO implements CouponsDAO {

    @Override
    public void addCoupon(Coupon coupon) throws DAOException {
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "insert into coupons (company_id, category_id, title, description, start_date, end_date, amount, price, image) values(?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, coupon.getCompanyID());
            pstmt.setInt(2, coupon.getCategory().ordinal());
            pstmt.setString(3, coupon.getTitle());
            pstmt.setString(4, coupon.getDescription());
            pstmt.setTimestamp(5, new Timestamp(coupon.getStartDate().getTime()));
            pstmt.setTimestamp(6, new Timestamp(coupon.getEndDate().getTime()));
            pstmt.setInt(7, coupon.getAmount());
            pstmt.setDouble(8, coupon.getPrice());
            pstmt.setString(9, coupon.getImage());
            pstmt.executeUpdate();
            ResultSet resKeys = pstmt.getGeneratedKeys();
			resKeys.next();
			coupon.setId(resKeys.getInt(1));
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: addCoupon failed,\n" + e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: addCoupon failed,\n" + e.getMessage());
            }
        }
    }

    @Override
    public void deleteCoupon(int couponID) throws DAOException {
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "delete from coupons where id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, couponID);
            pstmt.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: deleteCoupon failed,\n" + e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: deleteCoupon failed,\n" + e.getMessage());
            }
        }
    }

    @Override
    public void updateCoupon(Coupon coupon) throws DAOException {
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "update coupons set company_id=?, category_id=?, title=?, description=?, start_date=?, end_date=?, amount=?, price=?, image=? where id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, coupon.getCompanyID());
            pstmt.setInt(2, coupon.getCategory().ordinal());
            pstmt.setString(3, coupon.getTitle());
            pstmt.setString(4, coupon.getDescription());
            pstmt.setTimestamp(5, new Timestamp(coupon.getStartDate().getTime()));
            pstmt.setTimestamp(6, new Timestamp(coupon.getEndDate().getTime()));
            pstmt.setInt(7, coupon.getAmount());
            pstmt.setDouble(8, coupon.getPrice());
            pstmt.setString(9, coupon.getImage());
            pstmt.setInt(10, coupon.getId());
            pstmt.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: updating coupon failed", e);
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: updating coupon failed", e);
            }
        }
    }

    @Override
    public ArrayList<Coupon> getAllExpiredCoupons() throws DAOException {
        Connection con = null;
        ArrayList<Coupon> coupons = new ArrayList<>();
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "select * from coupons where end_date < ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Coupon coupon = new Coupon(rs.getInt("id"));
                coupon.setCompanyID(rs.getInt("company_id"));
                coupon.setCategory(Category.values()[rs.getInt("category_id")]);
                coupon.setTitle(rs.getString("title"));
                coupon.setDescription(rs.getString("description"));
                coupon.setStartDate(rs.getTimestamp("start_date"));
                coupon.setEndDate(rs.getTimestamp("end_date"));
                coupon.setAmount(rs.getInt("amount"));
                coupon.setPrice(rs.getInt("price"));
                coupon.setImage(rs.getString("image"));
                coupons.add(coupon);
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: getAllCoupons failed,\n" + e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: getAllCoupons failed,\n" + e.getMessage());
            }
        }
        return coupons;
    }

    @Override
    public ArrayList<Coupon> getAllCouponsByCompanyId(int companyID) throws DAOException {
        Connection con = null;
        ArrayList<Coupon> coupons = new ArrayList<>();
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "select * from coupons where company_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, companyID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Coupon coupon = new Coupon(rs.getInt("id"));
                coupon.setCompanyID(rs.getInt("company_id"));
                coupon.setCategory(Category.values()[rs.getInt("category_id")]);
                coupon.setTitle(rs.getString("title"));
                coupon.setDescription(rs.getString("description"));
                coupon.setStartDate(rs.getTimestamp("start_date"));
                coupon.setEndDate(rs.getTimestamp("end_date"));
                coupon.setAmount(rs.getInt("amount"));
                coupon.setPrice(rs.getInt("price"));
                coupon.setImage(rs.getString("image"));
                coupons.add(coupon);
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: getAllCouponsByCompanyId failed,\n" + e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: getAllCouponsByCompanyId failed,\n" + e.getMessage());
            }
        }
        return coupons;
    }

    @Override
    public ArrayList<Coupon> getAllCouponsByCompanyIdAndCategory(int companyID, int categoryID) throws DAOException {
        Connection con = null;
        ArrayList<Coupon> coupons = new ArrayList<>();
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "select * from coupons where company_id = ? and category_id =?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, companyID);
            pstmt.setInt(2, categoryID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Coupon coupon = new Coupon(rs.getInt("id"));
                coupon.setCompanyID(rs.getInt("company_id"));
                coupon.setCategory(Category.values()[rs.getInt("category_id")]);
                coupon.setTitle(rs.getString("title"));
                coupon.setDescription(rs.getString("description"));
                coupon.setStartDate(rs.getTimestamp("start_date"));
                coupon.setEndDate(rs.getTimestamp("end_date"));
                coupon.setAmount(rs.getInt("amount"));
                coupon.setPrice(rs.getInt("price"));
                coupon.setImage(rs.getString("image"));
                coupons.add(coupon);
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: getAllCouponsByCompanyIdAndCategory failed,\n" + e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: getAllCouponsByCompanyIdAndCategory failed,\n" + e.getMessage());
            }
        }
        return coupons;
    }

    @Override
    public ArrayList<Coupon> getAllCouponsByCompanyIdAndPrice(int companyID, double maxPrice) throws DAOException {
        Connection con = null;
        ArrayList<Coupon> coupons = new ArrayList<>();
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "select * from coupons where company_id = ? and price <= ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, companyID);
            pstmt.setDouble(2, maxPrice);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Coupon coupon = new Coupon(rs.getInt("id"));
                coupon.setCompanyID(rs.getInt("company_id"));
                coupon.setCategory(Category.values()[rs.getInt("category_id")]);
                coupon.setTitle(rs.getString("title"));
                coupon.setDescription(rs.getString("description"));
                coupon.setStartDate(rs.getTimestamp("start_date"));
                coupon.setEndDate(rs.getTimestamp("end_date"));
                coupon.setAmount(rs.getInt("amount"));
                coupon.setPrice(rs.getInt("price"));
                coupon.setImage(rs.getString("image"));
                coupons.add(coupon);
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: getAllCouponsByCompanyIdAndPrice failed,\n" + e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: getAllCouponsByCompanyIdAndPrice failed,\n" + e.getMessage());
            }
        }
        return coupons;
    }

    @Override
    public Coupon getCouponById(int couponID) throws DAOException {
        Connection con = null;
        Coupon coupon = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
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
                coupon.setStartDate(rs.getTimestamp("start_date"));
                coupon.setEndDate(rs.getTimestamp("end_date"));
                coupon.setAmount(rs.getInt("amount"));
                coupon.setPrice(rs.getInt("price"));
                coupon.setImage(rs.getString("image"));
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: getCouponById failed,\n" + e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: getCouponById failed,\n" + e.getMessage());
            }
        }
        return coupon;
    }

    @Override
    public void addCouponPurchase(int customerID, int couponID) throws DAOException {
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "insert into customers_vs_coupons values(?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, customerID);
            pstmt.setInt(2, couponID);
            pstmt.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: addCouponPurchase failed,\n" + e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: addCouponPurchase failed,\n" + e.getMessage());
            }
        }
    }

    @Override
    public void deleteCouponPurchase(int couponID) throws DAOException {
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "delete from customers_vs_coupons where coupon_id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, couponID);
            pstmt.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: deleteCouponPurchase failed,\n" + e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: deleteCouponPurchase failed,\n" + e.getMessage());
            }
        }
    }

    @Override
    public void deleteCouponPurchase(int customerID, int couponID) throws DAOException {
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "delete from customers_vs_coupons where customer_id=? and coupon_id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, customerID);
            pstmt.setInt(2, couponID);
            pstmt.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: deleteCouponPurchase failed,\n" + e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: deleteCouponPurchase failed,\n" + e.getMessage());
            }
        }
    }

    @Override
    public void addCategory(Category category) throws DAOException {
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "insert into categories values(?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, category.ordinal());
            pstmt.setString(2, category.toString());
            pstmt.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: addCategory failed,\n" + e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: addCategory failed,\n" + e.getMessage());
            }
        }

    }

    @Override
    public boolean isCouponExistByTitleAndCompanyId(String couponTitle, int companyID) throws DAOException {
        boolean isExist = false;
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "select * from coupons where title = ? and company_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, couponTitle);
            pstmt.setInt(2, companyID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                isExist = true;
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: isCouponExistByTitleAndCompanyId failed,\n" + e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: isCouponExistByTitleAndCompanyId failed,\n" + e.getMessage());
            }
        }
        return isExist;
    }


    @Override
    public boolean isCatogeryExist(int categoryID) throws DAOException {
        boolean isExist = false;
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "select * from categories where id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, categoryID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                isExist = true;
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: isCatogeryExist failed,\n" + e.getMessage());
        } finally {

            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: isCatogeryExist failed,\n" + e.getMessage());
            }
        }
        return isExist;
    }

    @Override
    public boolean isCouponPurchaseExistByCustomer(int customerID, int couponID) throws DAOException {
        boolean isExist = false;
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "select * from customers_vs_coupons where customer_id = ? and coupon_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, couponID);
            pstmt.setInt(2, customerID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                isExist = true;
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: isCouponPurchaseExistByCustomer failed,\n" + e.getMessage());
        } finally {

            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: isCouponPurchaseExistByCustomer failed,\n" + e.getMessage());
            }
        }
        return isExist;
    }

    @Override
    public ArrayList<Coupon> getAllCouponsByCustomerId(int customerID) throws DAOException {
        Connection con = null;
        ArrayList<Coupon> coupons = new ArrayList<>();
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "select * from coupons where id in (select coupon_id from customers_vs_coupons where customer_id = ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, customerID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Coupon coupon = new Coupon(rs.getInt("id"));
                coupon.setCompanyID(rs.getInt("company_id"));
                coupon.setCategory(Category.values()[rs.getInt("category_id")]);
                coupon.setTitle(rs.getString("title"));
                coupon.setDescription(rs.getString("description"));
                coupon.setStartDate(rs.getTimestamp("start_date"));
                coupon.setEndDate(rs.getTimestamp("end_date"));
                coupon.setAmount(rs.getInt("amount"));
                coupon.setPrice(rs.getInt("price"));
                coupon.setImage(rs.getString("image"));
                coupons.add(coupon);
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: getAllCouponsByCustomerId failed,\n" + e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: getAllCouponsByCustomerId failed,\n" + e.getMessage());
            }
        }
        return coupons;
    }

    @Override
    public ArrayList<Coupon> getAllCouponsByCustomerIdAndCategory(int customerID, int categoryID) throws DAOException {
        Connection con = null;
        ArrayList<Coupon> coupons = new ArrayList<>();
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "select * from coupons where id in (select coupon_id from customers_vs_coupons where customer_id = ?) and category_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, customerID);
            pstmt.setInt(2, categoryID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Coupon coupon = new Coupon(rs.getInt("id"));
                coupon.setCompanyID(rs.getInt("company_id"));
                coupon.setCategory(Category.values()[rs.getInt("category_id")]);
                coupon.setTitle(rs.getString("title"));
                coupon.setDescription(rs.getString("description"));
                coupon.setStartDate(rs.getTimestamp("start_date"));
                coupon.setEndDate(rs.getTimestamp("end_date"));
                coupon.setAmount(rs.getInt("amount"));
                coupon.setPrice(rs.getInt("price"));
                coupon.setImage(rs.getString("image"));
                coupons.add(coupon);
            }
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
            throw new DAOException("DAO Error: getAllCouponsByCustomerIdAndCategory failed,\n" + e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: getAllCouponsByCustomerIdAndCategory failed,\n" + e.getMessage());
            }
        }
        return coupons;
    }

    @Override
    public ArrayList<Coupon> getAllCouponsByCustomerIdAndMaxPrice(int customerID, double maxPrice) throws DAOException {
        Connection con = null;
        ArrayList<Coupon> coupons = new ArrayList<>();
        try {
            con = ConnectionPool.getInstance().getConnection();
            String sql = "select * from coupons where id in (select coupon_id from customers_vs_coupons where customer_id = ?) and price <= ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, customerID);
            pstmt.setDouble(2, maxPrice);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Coupon coupon = new Coupon(rs.getInt("id"));
                coupon.setCompanyID(rs.getInt("company_id"));
                coupon.setCategory(Category.values()[rs.getInt("category_id")]);
                coupon.setTitle(rs.getString("title"));
                coupon.setDescription(rs.getString("description"));
                coupon.setStartDate(rs.getTimestamp("start_date"));
                coupon.setEndDate(rs.getTimestamp("end_date"));
                coupon.setAmount(rs.getInt("amount"));
                coupon.setPrice(rs.getInt("price"));
                coupon.setImage(rs.getString("image"));
                coupons.add(coupon);
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("DAO Error: getAllCouponsByCustomerIdAndMaxPrice failed,\n" + e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().restoreConnections(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("DAO Error: getAllCouponsByCustomerIdAndMaxPrice failed,\n" + e.getMessage());
            }
        }
        return coupons;
    }
    
	@Override
	public void deleteCompanyCoupons(int companyID) throws DAOException {

		Connection con = null;

		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "delete from coupons where company_id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, companyID);
			pstmt.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: deleteCompanyCoupons failed,\n" + e.getMessage());		
			} finally {
			try {
				ConnectionPool.getInstance().restoreConnections(con);
			} catch (ConnectionPoolException e) {
				e.printStackTrace();
				throw new DAOException("DAO Error: deleteCompanyCoupons failed,\n" + e.getMessage());			}
		}

	}
	
	@Override
	public void deleteCustomerCoupons(int customerID) throws DAOException {

		Connection con = null;

		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "delete from customers_vs_coupons where CUSTOMER_ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, customerID);
			pstmt.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: deleteCustomerCoupons failed,\n" + e.getMessage());		
		} finally {
			try {
				ConnectionPool.getInstance().restoreConnections(con);
			} catch (ConnectionPoolException e) {
				e.printStackTrace();
				throw new DAOException("DAO Error: deleteCustomerCoupons failed,\n" + e.getMessage());		
			}
		}

	}
	

	@Override
	public void deleteCompanyCustomersPurchaseCoupons(int companyID) throws DAOException {

		Connection con = null;

		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "delete from customers_vs_coupons where COUPON_ID in (select ID from coupons where COMPANY_ID = ?);";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, companyID);
			pstmt.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
			throw new DAOException("DAO Error: deleteCompanyCustomersPurchaseCoupons failed,\n" + e.getMessage());		
		} finally {
			try {
				ConnectionPool.getInstance().restoreConnections(con);
			} catch (ConnectionPoolException e) {
				e.printStackTrace();
				throw new DAOException("DAO Error: deleteCompanyCustomersPurchaseCoupons failed,\n" + e.getMessage());		
			}
		}

	}
}