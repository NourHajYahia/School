package app.core.dao;

import java.util.ArrayList;

import app.core.beans.Category;
import app.core.beans.Coupon;
import app.core.exceptions.DAOException;

public interface CouponsDAO {
	
	boolean isCouponExistByTitleAndCompanyId(String couponTitle, int companyID) throws DAOException;

	void addCoupon(Coupon coupon) throws DAOException;

	void updateCoupon(Coupon coupon) throws DAOException;

	void deleteCoupon(int couponID) throws DAOException;
	
	ArrayList<Coupon> getAllCouponsByCompanyId(int companyID) throws DAOException;

	ArrayList<Coupon> getAllCoupons() throws DAOException;

	Coupon getCouponById(int couponID) throws DAOException;

	void addCouponPurchase(int customerID, int couponID) throws DAOException;

	void deleteCouponPurchase(int customerID, int couponID) throws DAOException;

	void addCategory(Category category) throws DAOException;

	boolean isCatogeryExist(int categoryID) throws DAOException;

	ArrayList<Coupon> getAllCouponsByCompanyIdAndCategory(int companyID, int categoryID) throws DAOException;

	ArrayList<Coupon> getAllCouponsByCompanyIdAndPrice(int companyID, int maxPrice) throws DAOException;
	

}
