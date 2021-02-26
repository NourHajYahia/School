package main.core.facade;

import java.util.ArrayList;
import java.util.Date;

import main.core.beans.Category;
import main.core.beans.Coupon;
import main.core.beans.Customer;
import main.core.dao.impl.CouponsDBDAO;
import main.core.dao.impl.CustomersDBDAO;
import main.core.exceptions.DAOException;
import main.core.exceptions.FacadeException;

public class CustomerFacade extends ClientFacade {

    private int customerID;

    public int getCustomerID() {
        return customerID;
    }

    public CustomerFacade() {
        this.customersDAO = new CustomersDBDAO();
        this.couponsDAO = new CouponsDBDAO();
    }

    /**
     * Authenticates login's email and password and returns value.
     * @param email
     * @param password
     * @return boolean
     */
    @Override
    public boolean login(String email, String password) throws FacadeException {
        try {
            Customer customer = customersDAO.getCustomerByEmailAndPassword(email, password);
            if (customer != null) {
                customerID = customer.getId();
                return true;
            } else {
                return false;
            }
        } catch (DAOException e) {
            throw new FacadeException("CustomerFacade Error: log in failed,\n" + e.getMessage());
        }
    }

    /**
     * confirms that the coupon object to be purchased by the customer obeys all business logic requirements and
     * calls DAO methods to communicate with the data base.
     *
     * @throws FacadeException
     * if coupon is out of date,
     * if coupon is out of stock.
     * if coupon not found.
     * if this purchase already exists.
     * if DAOException occurs.
     */
    public void purchaseCoupon(Coupon coupon) throws FacadeException {
        try {
            if (!couponsDAO.isCouponPurchaseExistByCustomer(customerID, coupon.getId())) {
                Coupon currCoupon = couponsDAO.getCouponById(coupon.getId());
                if (currCoupon == null) throw new FacadeException("CustomerFacade Error: coupon is not found");
                else if (currCoupon.getAmount() <= 0)
                    throw new FacadeException("CustomerFacade Error: coupon is out of stock");
                else if (currCoupon.getEndDate().before(new Date()))
                    throw new FacadeException("CustomerFacade Error: coupon is out of date");
                else {
                    couponsDAO.addCouponPurchase(customerID, coupon.getId());
                    currCoupon.setAmount(currCoupon.getAmount() - 1);
                    couponsDAO.updateCoupon(currCoupon);
                }
            } else
                throw new FacadeException("CustomerFacade Error: customer log in failed: coupon is already purchased");
        } catch (DAOException e) {
            throw new FacadeException("CustomerFacade Error: purchase coupon failed,\n" + e.getMessage());
        }
    }

    /**
     * gets all coupons of this customer.
     *
     * @return ArrayList
     * @throws FacadeException
     * if DAOException occurs.
     */
    public ArrayList<Coupon> getCustomerCoupons() throws FacadeException {
        try {
            return couponsDAO.getAllCouponsByCustomerId(customerID);
        } catch (DAOException e) {
            throw new FacadeException("CustomerFacade Error: getting coupon failed,\n" + e.getMessage());
        }
    }

    /**
     * gets all coupons of this customer by category.
     *
     * @return ArrayList
     * @throws FacadeException
     * if DAOException occurs.
     */
    public ArrayList<Coupon> getCustomerCoupons(Category category) throws FacadeException {
        try {
            return couponsDAO.getAllCouponsByCustomerIdAndCategory(customerID, category.ordinal());
        } catch (DAOException e) {
            throw new FacadeException("CustomerFacade Error: getting coupon failed,\n" + e.getMessage());
        }
    }

    /**
     * gets all coupons of this customer by max price.
     *
     * @return ArrayList
     * @throws FacadeException
     * if DAOException occurs.
     */
    public ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws FacadeException {
        try {
            return couponsDAO.getAllCouponsByCustomerIdAndMaxPrice(customerID, maxPrice);
        } catch (DAOException e) {
            throw new FacadeException("CustomerFacade Error: getting coupon failed,\n" + e.getMessage());
        }
    }

    /**
     * gets details of this customer.
     *
     * @return Customer
     * @throws FacadeException
     * if DAOException occurs.
     */
    public Customer getCustomerDetails() throws FacadeException {
        try {
            return customersDAO.getCustomerById(customerID);
        } catch (DAOException e) {
            throw new FacadeException("CustomerFacade Error: getting Customer Details failed,\n" + e.getMessage());
        }
    }
}
