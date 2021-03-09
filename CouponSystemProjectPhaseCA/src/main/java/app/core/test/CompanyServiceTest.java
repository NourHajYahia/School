package app.core.test;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.exceptions.LoginManagerException;
import app.core.exceptions.ServiceException;
import app.core.login.ClientType;
import app.core.login.LoginManager;
import app.core.services.CompanyService;

@Component
public class CompanyServiceTest {

	@Autowired
	private LoginManager loginManager;
	private CompanyService service;

	public void runTest(String email, String password) throws LoginManagerException {

		this.service = (CompanyService) loginManager.login(email, password, ClientType.COMPANY);

		Coupon coupon;

		System.out.println();
		System.out.println(
				"____________________________________________ companyFacadeTest _______________________________________________");
		System.out.println();

		// viewing all this company details
		System.out.println(
				"________________________________________ viewCompanyDetailsTest _______________________________________________");
		viewCompanyDetails();
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

		// viewing all this company coupons within database coupons table
		System.out.println(
				"________________________________________ getCompanyCouponsTest _______________________________________________");
		viewAllCompanyCouponsTest();
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

		// Testing addCoupon for this function requirements
		System.out.println(
				"________________________________________ addCouponTest _______________________________________________");
		System.out.println("\n1. adding first legit coupon:-");

		coupon = new Coupon(Category.ELECTRICITY, "aaa", "aaa aaa", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis() + 1000 * 30), 5, 100.0, "aaa");
		addCouponTest(coupon);

		System.out.println("\n2. adding second legit coupon:-");
		coupon = new Coupon(Category.FOOD, "bbb", "bbb bbb", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis() + 1000 * 60 * 2), 3, 200.0, "bbb");
		addCouponTest(coupon);

		System.out.println("\n3. adding third legit coupon:-");
		coupon = new Coupon(Category.FOOD, "ccc", "ccc ccc", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis() + 1000 * 60 * 60), 3, 50.0, "ccc");
		addCouponTest(coupon);

		System.out.println("\n4. adding fourth legit coupon:-");
		coupon = new Coupon(Category.VACATION, "ddd", "ddd ddd", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24), 0, 50.0, "ddd");
		addCouponTest(coupon);

		System.out.println("\n5. adding unlegit coupon with existed title in the same company:-");
		coupon = new Coupon(Category.FOOD, "bbb", "ccc ccc", new Date(),
				new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24), 2, 150.0, "ccc");
		addCouponTest(coupon);

		System.out.println("\n6. adding unlegit coupon with out of date:-");
		coupon = new Coupon(Category.FOOD, "bbb", "fff fff", new Date(),
				new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24), 2, 150.0, "ccc");
		addCouponTest(coupon);
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

		// viewing all this company coupons within database coupons table
		System.out.println(
				"________________________________________ getCompanyCouponsTest _______________________________________________");
		viewAllCompanyCouponsTest();
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

		// Testing updateCoupon for this company function requirements
		System.out.println(
				"________________________________________ updateCouponTest _______________________________________________");
		System.out.println("\n1. updating coupon:-");
		coupon = new Coupon(3, Category.ELECTRICITY, "ccc1", "ccc ccc1", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2), 7, 150.0, "ccc1");

		updateCouponTest(coupon);

		System.out.println("\n2. updating coupon with existed title:-");
		coupon = new Coupon(3, Category.ELECTRICITY, "bbb", "ccc ccc1", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2), 7, 150.0, "ccc1");
		updateCouponTest(coupon);

		System.out.println("\n3. updating coupon with unexisted id:-");
		coupon = new Coupon(5, Category.ELECTRICITY, "ccc1", "ccc ccc1", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2), 7, 150.0, "ccc1");
		updateCouponTest(coupon);
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

		// viewing all this company coupons within database coupons table
		System.out.println(
				"________________________________________ getCompanyCouponsTest _______________________________________________");
		viewAllCompanyCouponsTest();
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

		// viewing all this company coupons from food category within database coupons
		// table
		System.out.println(
				"________________________________________ getCompanyCouponsTest Category = FOOD _______________________________________________");
		viewAllCompanyCouponsTest(Category.FOOD);
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

		// viewing all this company coupons up to maxPrice = 160 within database coupons
		// table
		System.out.println(
				"________________________________________ getCompanyCouponsTest MaxPrice = 160  _______________________________________________");
		viewAllCompanyCouponsTest(160);
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

		// Testing addCompany function requirements
		System.out.println(
				"________________________________________ deleteCouponTest _______________________________________________");
		System.out.println("\n1. deleting coupon:-");
		deleteCouponTest(3);

		System.out.println("\n1. deleting unexisted coupon:-");
		deleteCouponTest(3);
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

		// viewing all this company coupons within database coupons table
		System.out.println(
				"________________________________________ getCompanyCouponsTest _______________________________________________");
		viewAllCompanyCouponsTest();
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

	}

	private void viewAllCompanyCouponsTest() {
		ArrayList<Coupon> coupons = (ArrayList<Coupon>) service.getCompanyCoupons();
		System.out.println();
		if (!coupons.isEmpty()) {
			for (Coupon coupon : coupons) {
				System.out.println("\t" + coupon);
			}
		} else {
			System.out.println("-- Empty");
		}

	}

	private void viewAllCompanyCouponsTest(Category category) {
		ArrayList<Coupon> coupons = (ArrayList<Coupon>) service.getCompanyCoupons(category);
		System.out.println();
		if (!coupons.isEmpty()) {
			for (Coupon coupon : coupons) {
				System.out.println("\t" + coupon);
			}
		} else {
			System.out.println("-- Empty");
		}

	}

	private void viewAllCompanyCouponsTest(double maxPrice) {
		ArrayList<Coupon> coupons = (ArrayList<Coupon>) service.getCompanyCoupons(maxPrice);
		System.out.println();
		if (!coupons.isEmpty()) {
			for (Coupon coupon : coupons) {
				System.out.println("\t" + coupon);
			}
		} else {
			System.out.println("-- Empty");
		}

	}

	private void viewCompanyDetails() {
		System.out.println("-- Company details: ");
		try {
			Company company = service.getCompany();
			System.out.println("Id: " + company.getId());
			System.out.println("Name: " + company.getName());
			System.out.println("Email: " + company.getEmail());
			System.out.println("Password: " + company.getPassword());
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	private void addCouponTest(Coupon coupon) {
		System.out.println("-- Adding: " + coupon);
		try {
			service.addCoupon(coupon);
			System.out.println("-- After addition: " + coupon);
			System.out.println("-- Coupon added");
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	private void updateCouponTest(Coupon coupon) {
		System.out.println("-- Adding: " + coupon);
		try {
			service.updateCoupon(coupon);
			System.out.println("-- After addition: " + coupon);
			System.out.println("-- Coupon added");
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	private void deleteCouponTest(int couponID) {
		System.out.println("-- Coupon Id for delete is: " + couponID);
		try {
			service.deleteCoupon(couponID);
			System.out.println("-- Coupon added");
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
