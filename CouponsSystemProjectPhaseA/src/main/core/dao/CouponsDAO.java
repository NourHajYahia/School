package main.core.dao;

import java.util.ArrayList;

import main.core.beans.Category;
import main.core.beans.Coupon;
import main.core.exceptions.DAOException;

public interface CouponsDAO {

    /**
     * adding a coupon to the data base table coupon.
     *
     * @param coupon
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the insertion
     */
    void addCoupon(Coupon coupon) throws DAOException;

    /**
     * deleting a coupon from the data base table coupons.
     *
     * @param couponID
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    public void deleteCoupon(int couponID) throws DAOException;

    /**
     * updating a coupon in the data base table coupons.
     *
     * @param coupon
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    void updateCoupon(Coupon coupon) throws DAOException;

    /**
     * asking the data base to fetch all expired coupons from coupons table.
     *
     * @return ArrayList
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    ArrayList<Coupon> getAllExpiredCoupons() throws DAOException;

    /**
     * asking the data base to fetch all coupons of specified company from coupons table.
     *
     * @param companyID
     * @return ArrayList
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    ArrayList<Coupon> getAllCouponsByCompanyId(int companyID) throws DAOException;

    /**
     * asking the data base to fetch all coupons of specified company and category from coupons table.
     *
     * @param companyID
     * @param categoryID
     * @return
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    ArrayList<Coupon> getAllCouponsByCompanyIdAndCategory(int companyID, int categoryID) throws DAOException;

    /**
     * asking the data base to fetch all coupons of specified company and maximum price from coupons table.
     *
     * @param companyID
     * @param maxPrice
     * @return
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    ArrayList<Coupon> getAllCouponsByCompanyIdAndPrice(int companyID, double maxPrice) throws DAOException;


    /**
     * asking the data base to fetch a coupon by id from coupons table.
     *
     * @param couponID
     * @return
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    Coupon getCouponById(int couponID) throws DAOException;


    /**
     * adding a coupon purchase of a customer to the data base table coupons_vs_customers.
     *
     * @param customerID
     * @param couponID
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    void addCouponPurchase(int customerID, int couponID) throws DAOException;


    /**
     * deleting a coupon purchase from the data table coupons_vs_customers.
     *
     * @param couponID
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    void deleteCouponPurchase(int couponID) throws DAOException;


    /**
     * deleting a coupon purchase from the data table coupons_vs_customers.
     *
     * @param customerID
     * @param couponID
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    void deleteCouponPurchase(int customerID, int couponID) throws DAOException;


    /**
     * adding category to data base table categories..
     *
     * @param category
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    void addCategory(Category category) throws DAOException;

    /**
     * checking the data base table coupons for coupon with the same title in the same company, if one exist then
     * returns true, otherwise false.
     *
     * @param couponTitle
     * @param companyID
     * @return boolean
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    boolean isCouponExistByTitleAndCompanyId(String couponTitle, int companyID) throws DAOException;


    /**
     *  checking the data base table category for category by id, if exist then
     *  returns true, otherwise false.
     *
     * @param categoryID
     * @return boolean
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    boolean isCatogeryExist(int categoryID) throws DAOException;


    /**
     * checking the data base table coupons_vs_customer for coupon purchase by customer, if exist then
     * returns true, otherwise false.
     *
     * @param customerID
     * @param couponID
     * @return boolean
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    boolean isCouponPurchaseExistByCustomer(int customerID, int couponID) throws DAOException;


    /**
     * asking the data base to fetch all coupons of specified customer from coupons table.
     *
     * @param customerID
     * @return ArrayList
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    ArrayList<Coupon> getAllCouponsByCustomerId(int customerID) throws DAOException;


    /**
     * asking the data base to fetch all coupons of specified customer and category from coupons table.
     *
     * @param customerID
     * @param categoryID
     * @return
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    ArrayList<Coupon> getAllCouponsByCustomerIdAndCategory(int customerID, int categoryID) throws DAOException;


    /**
     * asking the data base to fetch all coupons of specified customer and max price from coupons table.
     *
     * @param customerID
     * @param maxPrice
     * @return
     * @throws DAOException
     *  - if ConnectionPool failed to supply connection or to close it
     *  - if SQL failed to to execute the deletion
     */
    ArrayList<Coupon> getAllCouponsByCustomerIdAndMaxPrice(int customerID, double maxPrice) throws DAOException;

	void deleteCompanyCustomersPurchaseCoupons(int companyID) throws DAOException;

	void deleteCustomerCoupons(int customerID) throws DAOException;

	void deleteCompanyCoupons(int companyID) throws DAOException;
}