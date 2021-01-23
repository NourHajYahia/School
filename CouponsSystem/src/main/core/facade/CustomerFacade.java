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

	public CustomerFacade() throws FacadeException {
		super();
		try {
			this.customersDAO = new CustomersDBDAO();
			this.couponsDAO = new CouponsDBDAO();
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("CustomerFacade Error: initializing Customer failed", e);
		}
	}

	@Override
	public boolean login(String email, String password) throws FacadeException {
		try {
			if (customersDAO.isCustomerExists(email, password)) {
				customerID = customersDAO.getCustomerByEmailAndPassword(email, password).getId();
				return true;
			} else {
				return false;
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("CustomerFacade Error: customer log in failed", e);
		}
	}

	public void purchaceCoupon(Coupon coupon) throws FacadeException {

		try {
			if (!couponsDAO.isCouponPurchaseExistByCustomer(customerID, coupon.getId())) {
				Coupon currCoupon = couponsDAO.getCouponById(coupon.getId());
				if (currCoupon == null) {
					throw new FacadeException("CustomerFacade Error: coupon is not found");
				} else if (currCoupon.getAmount() <= 0) {
					throw new FacadeException("CustomerFacade Error: coupon is out of stock");
				} else if (currCoupon.getEndDate().before(new Date())) {
					throw new FacadeException("CustomerFacade Error: coupon is out of date");
				} else {
					couponsDAO.addCouponPurchase(customerID, coupon.getId());
					currCoupon.setAmount(currCoupon.getAmount() - 1);
					couponsDAO.updateCoupon(currCoupon);
				}
			} else {
				throw new FacadeException("CustomerFacade Error: customer log in failed: coupon is already purchaced");
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("CustomerFacade Error: customer log in failed", e);
		}

	}

	public ArrayList<Coupon> getCustomerCoupons() throws FacadeException {
		try {
			return couponsDAO.getAllCouponsByCustomerId(customerID);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("CompanyFacade Error: getiing customer coupons failed", e);
		}

	}

	public ArrayList<Coupon> getCustomerCoupons(Category category) throws FacadeException {
		try {
			return couponsDAO.getAllCouponsByCustomerIdAndCategory(customerID, category.ordinal());
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("CompanyFacade Error: getiing customer coupons failed", e);
		}

	}

	public ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws FacadeException {
		try {
			return couponsDAO.getAllCouponsByCustomerIdAndMaxPrice(customerID, maxPrice);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("CompanyFacade Error: getiing customer coupons failed", e);
		}

	}

	public Customer getCustomerDetails() throws FacadeException {
		try {
			return customersDAO.getCustomerById(customerID);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("CustomerFacade Error: getiing customer details failed", e);
		}
	}

}
