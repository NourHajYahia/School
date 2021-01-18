package test.facade;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.core.Facade.CompanyFacade;
import main.core.beans.Coupon;
import main.core.db.ConnectionPool;
import main.core.exceptions.ConnectionPoolException;
import main.core.exceptions.FacadeException;

public class CompanyFacadeTest {

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

	static CompanyFacade facade;

	public static void main(String[] args) {

		restoreCouponTableToDefault();
		System.out.println();
		System.out.println("____________________ adminFacadeTest __________________________");
		System.out.println();
		try {
			facade = new CompanyFacade();
			// viewing all companies within database companies table
			System.out.println("___________________ getCompanyCouponsTest __________________________");
			viewAllCompanyCouponsTest();
			System.out.println();
			// ------------------------------------------------------------------------------------------------------

		} catch (FacadeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// TODO: handle finally clause
		}

	}
	
	private static void viewAllCompanyCouponsTest() {
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

}
