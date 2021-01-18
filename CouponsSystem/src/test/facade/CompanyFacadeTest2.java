package test.facade;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import main.core.Facade.CompanyFacade;
import main.core.beans.Category;
import main.core.beans.Coupon;
import main.core.db.ConnectionPool;
import main.core.exceptions.ConnectionPoolException;
import main.core.exceptions.FacadeException;

public class CompanyFacadeTest2 {
	
	static CompanyFacade facade;

	public static void main(String[] args) {
		
		restoreCouponTableToDefault();
		try {
			facade = new CompanyFacade();
			
			
			
			System.out.println("\n========== Company Login Failed By Password ==========");
			loginCompanySuccess("admin@admin.com", "admin");
			System.out.println("=========================================");
			
			System.out.println("\n========== Company Login Success ==========");
			loginCompanySuccess("ddd@ddd.com", "ddd");
			System.out.println("=========================================");
			
			System.out.println("\n========== Company getting and viewing all company id = 2 coupons Success Empty table ==========");
			viewAllCompanyCoupons();
			System.out.println("=========================================");
			
			System.out.println("\n========== Company id = 2 Adding coupon coupon Success ==========");
			addCoupon(getTestCoupons().get(0));
			System.out.println("=========================================");
			
			System.out.println("\n========== Company id = 2 Adding coupon coupon Failed the same title ==========");
			addCoupon(getTestCoupons().get(1));
			System.out.println("=========================================");
			
			System.out.println("\n========== Company id = 2 Adding coupon coupon Success ==========");
			addCoupon(getTestCoupons().get(2));
			System.out.println("=========================================");
			
			System.out.println("\n========== Company id = 2 getting and viewing all coupons Success ==========");
			viewAllCompanyCoupons();
			System.out.println("=========================================");
			
			System.out.println("\n========== Company Login with another company Success ==========");
			loginCompanySuccess("eee1@eee1.com", "eee1");
			System.out.println("=========================================");
			
			System.out.println("\n========== Company getting and viewing all company id = 3 coupons Success ==========");
			viewAllCompanyCoupons();
			System.out.println("=========================================");
			
			System.out.println("\n========== Company id = 3 Adding coupon id = 3 coupon Success ==========");
			addCoupon(getTestCoupons().get(0));
			System.out.println("=========================================");
			
			System.out.println("\n========== Company id = 3  Adding coupon coupon Failed the same title ==========");
			addCoupon(getTestCoupons().get(1));
			System.out.println("=========================================");
			
			System.out.println("\n========== Company id = 3 Adding coupon coupon Success ==========");
			addCoupon(getTestCoupons().get(3));
			System.out.println("=========================================");
			
			System.out.println("\n========== Company id = 3 getting and viewing all coupons Success ==========");
			viewAllCompanyCoupons();
			System.out.println("=========================================");
			
			System.out.println("\n========== Company id = 3 update coupon Failed to update id not exist ==========");
			updateCoupon(getTestCoupons().get(4));
			System.out.println("=========================================");
			
			System.out.println("\n========== Company id = 3 update coupon Failed to update company id not exist ==========");
			updateCoupon(getTestCoupons().get(5));
			System.out.println("=========================================");

			System.out.println("\n========== Company id = 3 update coupon Failed to update company title is not allowed  ==========");
			updateCoupon(getTestCoupons().get(6));
			System.out.println("=========================================");
			
			System.out.println("\n========== Company id = 3 update coupon success  ==========");
			updateCoupon(getTestCoupons().get(7));
			System.out.println("=========================================");
			
			
		} catch (FacadeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// TODO: handle finally clause
		}

	}
	
	private static void restoreCouponTableToDefault() {

		Connection con = null;
		ConnectionPool connectionPool = null;

		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.getConnection();

			String sqlDelete = "delete from coupons";
			Statement pstmt1 = con.createStatement();
			pstmt1.executeUpdate(sqlDelete);
			System.out.println(sqlDelete);

			String sqlResetIncrement = "ALTER TABLE coupons AUTO_INCREMENT = 1";
			Statement pstmt2 = con.createStatement();
			pstmt2.executeUpdate(sqlResetIncrement);
			System.out.println(sqlResetIncrement);

		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connectionPool.restoreConnections(con);
			} catch (ConnectionPoolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static ArrayList<Coupon> getTestCoupons() {
		
		ArrayList<Coupon> initialCoupons = new ArrayList<Coupon>();
		
		initialCoupons.add(new Coupon(Category.ELECTRICITY, "aaa", "aaa aaa", LocalDate.now(), LocalDate.of(2025, 10, 10), 5, 100.0, "aaa"));
		initialCoupons.add(new Coupon(Category.FOOD, "aaa", "bbb bbb", LocalDate.now(), LocalDate.of(2026, 10, 10), 3, 200.0, "bbb"));
		initialCoupons.add(new Coupon(Category.ELECTRICITY, "bbb", "bbb bbb", LocalDate.now(), LocalDate.of(2026, 10, 10), 3, 200.0, "bbb"));
		initialCoupons.add(new Coupon(Category.FOOD, "ccc", "ccc ccc", LocalDate.now(), LocalDate.of(2026, 05, 05), 3, 200.0, "ccc"));
		initialCoupons.add(new Coupon(5, 3, Category.FOOD, "ccc", "ccc ccc", LocalDate.now(), LocalDate.of(2026, 05, 05), 3, 200.0, "ccc"));
		initialCoupons.add(new Coupon(4, 5, Category.FOOD, "ccc", "ccc ccc", LocalDate.now(), LocalDate.of(2026, 05, 05), 3, 200.0, "ccc"));
		initialCoupons.add(new Coupon(4, 3, Category.FOOD, "ccc", "ggg ggg", LocalDate.now(), LocalDate.of(2026, 06, 06), 4, 150.0, "ggg"));
		initialCoupons.add(new Coupon(4, 3, Category.FOOD, "ggg", "ggg ggg", LocalDate.now(), LocalDate.of(2026, 06, 06), 4, 150.0, "ggg"));
		return initialCoupons;
	}
	
	private static void loginCompanySuccess(String email, String password) {
		try {
			if(facade.login(email, password)) {
				System.out.println("company facade login:\nemail: " + email + "\npassword: " + password + "\nTest: login success: company id is: " + facade.getCompanyID());
			} else {
				System.out.println("admin facade login:\nemail: " + email + "\npassword: " + password + "\nTest: login failed: email or password are not valid");
			}
		} catch (FacadeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void addCoupon(Coupon coupon) {
		System.out.println("company facade addCoupon: ");
		System.out.println("coupon for add is: " + coupon);
		try {
			facade.addCoupon(coupon);
			System.out.println("object coupon after addition: " + coupon);
			System.out.println("Database company coupons after addition: ");
			ArrayList<Coupon> coupons = facade.getCompanyCoupons();
			for (Coupon couponPrint : coupons) {
				System.out.println(couponPrint);
			}
			System.out.println("Test: success");
		} catch (FacadeException e) {
			System.out.println("Test failed: " + e.getMessage());
		}
	}
	
	private static void viewAllCompanyCoupons() {

		try {
			ArrayList<Coupon> coupons = facade.getCompanyCoupons();
			if (!coupons.isEmpty()) {
				for (Coupon coupon : coupons) {
					System.out.println(coupon);
				}
			} else {
				System.out.println("companies table is empty");
				System.out.println("Test: success");
			}

		} catch (FacadeException e) {
			System.err.println("Test failed: " + e.getMessage());
		}

	}
	
	private static void updateCoupon(Coupon coupon) {
		System.out.println("company facade addCoupon: ");
		System.out.println("coupon for update is: " + coupon);
		try {
			facade.updateCoupon(coupon);
			System.out.println("object coupon after addition: " + coupon);
			System.out.println("Database company coupons after addition: ");
			ArrayList<Coupon> coupons = facade.getCompanyCoupons();
			for (Coupon couponPrint : coupons) {
				System.out.println(couponPrint);
			}
			System.out.println("Test: success");
		} catch (FacadeException e) {
			System.out.println("Test failed: " + e.getMessage());
		}
	}
}
