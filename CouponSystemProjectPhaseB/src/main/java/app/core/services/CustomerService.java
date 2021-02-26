package app.core.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.ServiceException;
import app.core.repositories.CouponRepositories;
import app.core.repositories.CustomerRepositories;

@Service
@Transactional
@Scope("prototype")
public class CustomerService extends ClientService {

	@Autowired
	private CustomerRepositories customerRepositories;
	@Autowired
	private CouponRepositories couponRepositories;

	private Integer customerID;

	/**
	 * Authenticates login's email and password and returns value.
	 * 
	 * @param email
	 * @param password
	 * @return boolean
	 */
	@Override
	public boolean login(String email, String password) {
		List<Customer> customer = customerRepositories.findByEmailAndPassword(email, password);
		if (customer.isEmpty()) {
			return false;
		} else {
			this.customerID = customer.get(0).getId();
			return true;
		}
	}

	/**
	 * confirms that the coupon object to be purchased by the customer obeys all
	 * business logic requirements and calls DAO methods to communicate with the
	 * data base.
	 *
	 * @throws FacadeException if coupon is out of date, if coupon is out of stock.
	 *                         if coupon not found. if this purchase already exists.
	 *                         if DAOException occurs.
	 */
	public void purchaseCoupon(Coupon coupon) throws ServiceException {

		Optional<Coupon> optional = couponRepositories.findById(coupon.getId());
		if (optional.isEmpty()) {
			throw new ServiceException("CustomerService Error: coupon is not found");
		}
		Coupon dbCoupon = optional.get();
		if (couponRepositories.existsByIdAndCustomersId(customerID ,coupon.getId()))
			throw new ServiceException("CustomerService Error: coupon is already purchased");
		else if (dbCoupon.getAmount() <= 0)
			throw new ServiceException("CustomerService Error: coupon is out of stock");
		else if (dbCoupon.getEndDate().before(new Date()))
			throw new ServiceException("CustomerService Error: coupon is out of date");
		else {
			dbCoupon.addCustomer(getCustomer());
		}

	}

	/**
	 * gets all coupons of this customer.
	 *
	 * @return ArrayList
	 * @throws FacadeException if DAOException occurs.
	 */
	public List<Coupon> getCustomerCoupons() {
		return couponRepositories.getCouponsByCustomersId(this.customerID);
	}

	/**
	 * gets all coupons of this customer by category.
	 *
	 * @return ArrayList
	 * @throws FacadeException if DAOException occurs.
	 */
	public List<Coupon> getCustomerCoupons(Category category) {
		return couponRepositories.getCouponsByCustomersIdAndCategory(this.customerID, category);
	}

	/**
	 * gets all coupons of this customer by max price.
	 *
	 * @return ArrayList
	 * @throws FacadeException if DAOException occurs.
	 */
	public List<Coupon> getCustomerCoupons(double maxPrice) {

		return couponRepositories.getCouponsByCustomersIdAndPriceLessThanEqual(customerID, maxPrice);

	}

	/**
	 * gets details of this customer.
	 *
	 * @return Customer
	 * @throws FacadeException if DAOException occurs.
	 */
	public Customer getCustomer() throws ServiceException {

		Optional<Customer> optional = customerRepositories.findById(this.customerID);
		if (optional.isPresent()) {
			Customer customer = optional.get();
			return customer;
		}
		throw new ServiceException("CustomerService Error: customer is not found");

	}

}
