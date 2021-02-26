package app.core.test;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.LoginManagerException;
import app.core.exceptions.ServiceException;
import app.core.login.ClientType;
import app.core.login.LoginManager;
import app.core.services.CustomerService;

@Component
public class CustomerServiceTest {

	@Autowired
	private LoginManager loginManager;
	private CustomerService service;

	public void runTest(String email, String password) throws LoginManagerException {

		this.service =  (CustomerService) loginManager.login(email, password, ClientType.CUSTOMER);

		Coupon coupon;

		System.out.println();
		System.out.println(
				"____________________________________________ customerFacadeTest _______________________________________________");
		System.out.println();

		// viewing this customer details
		System.out.println(
				"________________________________________ viewCustomerDetailsTest _______________________________________________");
		viewCustomerDetails();
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

		// viewing all this customer coupons within database coupons table
		System.out.println(
				"________________________________________ getCustomerCouponsTest _______________________________________________");
		viewAllCustomerCouponsTest();
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

		// purchasing coupons for current customer
		System.out.println(
				"________________________________________ purchaceCouponTest _______________________________________________");
		System.out.println("\n1. purchacing first legal coupon:-");
		coupon = new Coupon(1);
		purchaceCouponTest(coupon);
		System.out.println();

		System.out.println("\n2. purchacing the same coupon coupon:-");
		coupon = new Coupon(1);
		purchaceCouponTest(coupon);

		System.out.println("\n3. purchacing second legal coupon:-");
		coupon = new Coupon(2);
		purchaceCouponTest(coupon);

		System.out.println("\n4. purchacing out of stack coupon:-");
		coupon = new Coupon(4);
		purchaceCouponTest(coupon);
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

		// viewing all this customer coupons within database coupons table
		System.out.println(
				"________________________________________ getCustomerCouponsTest _______________________________________________");
		viewAllCustomerCouponsTest();
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

		// viewing all this customer coupons from food category within database coupons
		// table
		System.out.println(
				"________________________________________ getcustomerCouponsTest Category = FOOD _______________________________________________");
		viewAllCustomerCouponsTest(Category.FOOD);
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

		// viewing all this customer coupons up to maxPrice = 160 within database
		// coupons
		// table
		System.out.println(
				"________________________________________ getcustomerCouponsTest MaxPrice = 160  _______________________________________________");
		viewAllCustomerCouponsTest(160);
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

	}

	private void purchaceCouponTest(Coupon coupon) {
		System.out.println("-- purchacing: " + coupon);
		try {
			service.purchaseCoupon(coupon);
			System.out.println("Coupon purchaced");
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	private void viewAllCustomerCouponsTest() {
		ArrayList<Coupon> coupons = (ArrayList<Coupon>) service.getCustomerCoupons();
		System.out.println();
		if (!coupons.isEmpty()) {
			for (Coupon coupon : coupons) {
				System.out.println("\t" + coupon);
			}
		} else {
			System.out.println("-- Empty");
		}
	}

	private void viewAllCustomerCouponsTest(Category category) {
		ArrayList<Coupon> coupons = (ArrayList<Coupon>) service.getCustomerCoupons(category);
		System.out.println();
		if (!coupons.isEmpty()) {
			for (Coupon coupon : coupons) {
				System.out.println("\t" + coupon);
			}
		} else {
			System.out.println("-- Empty");
		}
	}

	private void viewAllCustomerCouponsTest(int maxPrice) {
		ArrayList<Coupon> coupons = (ArrayList<Coupon>) service.getCustomerCoupons(maxPrice);
		System.out.println();
		if (!coupons.isEmpty()) {
			for (Coupon coupon : coupons) {
				System.out.println("\t" + coupon);
			}
		} else {
			System.out.println("-- Empty");
		}
	}

	private void viewCustomerDetails() {
		System.out.println("-- Customer details: ");
		try {
			Customer customer = service.getCustomer();
			System.out.println("Id: " + customer.getId());
			System.out.println("First Name: " + customer.getFirstName());
			System.out.println("Last Name: " + customer.getLastName());
			System.out.println("Email: " + customer.getEmail());
			System.out.println("Password: " + customer.getPassword());
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}
}
