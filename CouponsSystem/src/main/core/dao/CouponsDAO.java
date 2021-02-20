package main.core.dao;

import java.util.ArrayList;

import main.core.beans.Category;
import main.core.beans.Coupon;
import main.core.exceptions.DAOException;

public interface CouponsDAO {

	boolean isCouponExistByTitleAndCompanyId(String couponTitle, int companyID) throws DAOException;

	void addCoupon(Coupon coupon) throws DAOException;

	void updateCoupon(Coupon coupon) throws DAOException;

	void deleteCoupon(int couponID) throws DAOException;

	ArrayList<Coupon> getAllExpiredCoupons() throws DAOException;

	Coupon getCouponById(int couponID) throws DAOException;

	boolean isCouponPurchaseExistByCustomer(int customerID, int couponID) throws DAOException;

	void addCouponPurchase(int customerID, int couponID) throws DAOException;

	void deleteCouponPurchase(int customerID, int couponID) throws DAOException;

	void addCategory(Category category) throws DAOException;

	boolean isCatogeryExist(int categoryID) throws DAOException;

	ArrayList<Coupon> getAllCouponsByCompanyId(int companyID) throws DAOException;

	ArrayList<Coupon> getAllCouponsByCompanyIdAndCategory(int companyID, int categoryID) throws DAOException;

	ArrayList<Coupon> getAllCouponsByCompanyIdAndPrice(int companyID, double maxPrice) throws DAOException;
	
	ArrayList<Coupon> getAllCouponsByCustomerId(int customerID) throws DAOException;

	ArrayList<Coupon> getAllCouponsByCustomerIdAndCategory(int customerID, int categoryID) throws DAOException;

	ArrayList<Coupon> getAllCouponsByCustomerIdAndMaxPrice(int customerID, double maxPrice) throws DAOException;

	void deleteCouponPurchase(int couponID) throws DAOException;

	void deleteCompanyCoupons(int companyID) throws DAOException;

	void deleteCustomerCoupons(int CustomerID) throws DAOException;
	

}
