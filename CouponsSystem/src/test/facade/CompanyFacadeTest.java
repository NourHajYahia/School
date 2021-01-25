package test.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import main.core.beans.Category;
import main.core.beans.Company;
import main.core.beans.Coupon;
import main.core.exceptions.FacadeException;
import main.core.facade.ClientFacade;
import main.core.facade.CompanyFacade;

public class CompanyFacadeTest extends FacadeTest {

	private CompanyFacade facade;

	public CompanyFacadeTest(ClientFacade facade) {
		this.facade = (CompanyFacade) facade;
	}


	@Override
	public void runTest() {

		Coupon coupon;

		System.out.println();
		System.out.println("____________________________________________ companyFacadeTest _______________________________________________");
		System.out.println();

		// viewing all this company details
		System.out.println("________________________________________ viewCompanyDetailsTest _______________________________________________");
		viewCompanyDetails();
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

		// viewing all this company coupons within database coupons table
		System.out.println("________________________________________ getCompanyCouponsTest _______________________________________________");
		viewAllCompanyCouponsTest();
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

		// Testing addCoupon for this function requirements
		System.out.println("________________________________________ addCouponTest _______________________________________________");
		System.out.println("\n1. adding first legit coupon:-");
		
		coupon = new Coupon(Category.ELECTRICITY, "aaa", "aaa aaa",new Date(System.currentTimeMillis()) , new Date(System.currentTimeMillis() + 1000*60) , 5,
				100.0, "aaa");
		addCouponTest(coupon);

		System.out.println("\n2. adding second legit coupon:-");
		coupon = new Coupon(Category.FOOD, "bbb", "bbb bbb", new Date(System.currentTimeMillis()) , new Date(System.currentTimeMillis() + 1000*60*2), 3, 200.0,
				"bbb");
		addCouponTest(coupon);

		System.out.println("\n3. adding third legit coupon:-");
		coupon = new Coupon(Category.FOOD, "ccc", "ccc ccc", new Date(System.currentTimeMillis()) , new Date(System.currentTimeMillis() + 1000*60*60), 3, 50, "ccc");
		addCouponTest(coupon);
		
		System.out.println("\n4. adding fourth legit coupon:-");
		coupon = new Coupon(Category.VACATION, "ddd", "ddd ddd", new Date(System.currentTimeMillis()) , new Date(System.currentTimeMillis() + 1000*60*60*24), 0, 50, "ddd");
		addCouponTest(coupon);

		System.out.println("\n3. adding unlegit coupon with existed title in the same company:-");
		coupon = new Coupon(Category.FOOD, "bbb", "ccc ccc", new Date(), new GregorianCalendar(2026, 10, 5).getTime(), 2, 150.0,
				"ccc");
		addCouponTest(coupon);
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

		// viewing all this company coupons within database coupons table
		System.out.println("________________________________________ getCompanyCouponsTest _______________________________________________");
		viewAllCompanyCouponsTest();
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

		// Testing updateCoupon for this company function requirements
		System.out.println("________________________________________ updateCouponTest _______________________________________________");
		System.out.println("\n1. updating coupon:-");
		coupon = new Coupon(3, 0, Category.ELECTRICITY, "ccc1", "ccc ccc1", new Date(System.currentTimeMillis()) , new Date(System.currentTimeMillis() + 1000*60*60*24*2),
				7, 150, "ccc1");
		
		updateCouponTest(coupon);

		System.out.println("\n2. updating coupon with existed title:-");
		coupon = new Coupon(3, 0, Category.ELECTRICITY, "bbb", "ccc ccc1", new Date(System.currentTimeMillis()) , new Date(System.currentTimeMillis() + 1000*60*60*24*2),
				7, 150, "ccc1");
		updateCouponTest(coupon);

		System.out.println("\n3. updating coupon with unexisted id:-");
		coupon = new Coupon(5, 0, Category.ELECTRICITY, "ccc1", "ccc ccc1", new Date(System.currentTimeMillis()) , new Date(System.currentTimeMillis() + 1000*60*60*24*2),
				7, 150, "ccc1");
		updateCouponTest(coupon);
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

		// viewing all this company coupons within database coupons table
		System.out.println("________________________________________ getCompanyCouponsTest _______________________________________________");
		viewAllCompanyCouponsTest();
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

		// viewing all this company coupons from food category within database coupons
		// table
		System.out.println("________________________________________ getCompanyCouponsTest Category = FOOD _______________________________________________");
		viewAllCompanyCouponsTest(Category.FOOD);
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

		// viewing all this company coupons up to maxPrice = 160 within database coupons
		// table
		System.out.println("________________________________________ getCompanyCouponsTest MaxPrice = 160  _______________________________________________");
		viewAllCompanyCouponsTest(160);
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

		// Testing addCompany function requirements
		System.out.println("________________________________________ deleteCouponTest _______________________________________________");
		System.out.println("\n1. deleting coupon:-");
		deleteCouponTest(3);

		System.out.println("\n1. deleting unexisted coupon:-");
		deleteCouponTest(3);
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

		// viewing all this company coupons within database coupons table
		System.out.println("________________________________________ getCompanyCouponsTest _______________________________________________");
		viewAllCompanyCouponsTest();
		System.out.println();
		// ------------------------------------------------------------------------------------------------------

	}

	private void viewAllCompanyCouponsTest() {
		try {
			ArrayList<Coupon> coupons = facade.getCompanyCoupons();
			System.out.println();
			if (!coupons.isEmpty()) {
				for (Coupon coupon : coupons) {
					System.out.println("\t" + coupon);
				}
			} else {
				System.out.println("-- Empty");
			}
		} catch (FacadeException e) {
			System.err.println(e.getMessage());
		}

	}

	private void viewAllCompanyCouponsTest(Category category) {
		try {
			ArrayList<Coupon> coupons = facade.getCompanyCoupons(category);
			System.out.println();
			if (!coupons.isEmpty()) {
				for (Coupon coupon : coupons) {
					System.out.println("\t" + coupon);
				}
			} else {
				System.out.println("-- Empty");
			}
		} catch (FacadeException e) {
			System.err.println(e.getMessage());
		}

	}

	private void viewAllCompanyCouponsTest(double maxPrice) {
		try {
			ArrayList<Coupon> coupons = facade.getCompanyCoupons(maxPrice);
			System.out.println();
			if (!coupons.isEmpty()) {
				for (Coupon coupon : coupons) {
					System.out.println("\t" + coupon);
				}
			} else {
				System.out.println("-- Empty");
			}
		} catch (FacadeException e) {
			System.err.println(e.getMessage());
		}

	}

	private void viewCompanyDetails() {
		System.out.println("-- Company details: ");
		try {
			Company company = facade.getCompanyDetails();
			System.out.println("Id: " + company.getId());
			System.out.println("Name: " + company.getName());
			System.out.println("Email: " + company.getEmail());
			System.out.println("Password: " + company.getPassword());
		} catch (FacadeException e) {
			System.out.println(e.getMessage());
		}

	}

	private void addCouponTest(Coupon coupon) {
		System.out.println("-- Adding: " + coupon);
		try {
			facade.addCoupon(coupon);
			System.out.println("-- After addition: " + coupon);
			System.out.println("-- Company added");
		} catch (FacadeException e) {
			System.out.println(e.getMessage());
		}
	}

	private void updateCouponTest(Coupon coupon) {
		System.out.println("-- Adding: " + coupon);
		try {
			facade.updateCoupon(coupon);
			System.out.println("-- After addition: " + coupon);
			System.out.println("-- Coupon added");
		} catch (FacadeException e) {
			System.out.println(e.getMessage());
		}
	}

	private void deleteCouponTest(int couponID) {
		System.out.println("-- Coupon Id for delete is: " + couponID);
		try {
			facade.deleteCoupon(couponID);
			System.out.println("-- Coupon added");
		} catch (FacadeException e) {
			System.out.println(e.getMessage());
		}
	}

}
