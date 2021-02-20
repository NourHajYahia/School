package test.createDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.core.beans.Category;
import main.core.db.ConnectionPool;
import main.core.exceptions.ConnectionPoolException;

public class InitializeDB {

	private static Connection con = null;

	public static void start() {

		try {

			con = ConnectionPool.getInstance().getConnection();
			ResultSet rs;
			ArrayList<String> tablesSQL = startTablesArray();

			rs = con.getMetaData().getTables(con.getCatalog(), null, "%", null);
			while (rs.next()) {
				String table = rs.getString(3);
				String sql = "drop table " + table;
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
			}

			for (String sql : tablesSQL) {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
			}
			
			initializeCategoryTable();

			System.out.println("Tables created");

		} catch (ConnectionPoolException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				ConnectionPool.getInstance().restoreConnections(con);
			} catch (ConnectionPoolException e) {
				System.out.println(e.getMessage());
			}
		}

	}

	private static ArrayList<String> startTablesArray() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("create table COMPANIES (" + "ID int not null primary key auto_increment," + "NAME varchar(255),"
				+ "EMAIL varchar(255)," + "PASSWORD varchar(255))");

		arrayList.add(
				"create table CUSTOMERS (" + "ID int not null primary key auto_increment," + "FIRST_NAME varchar(255),"
						+ "LAST_NAME varchar(255)," + "EMAIL varchar(255)," + "PASSWORD varchar(255))");

		arrayList.add(
				"create table CATEGORIES (" + "ID int not null primary key auto_increment," + "NAME varchar(255))");

		arrayList.add("create table COUPONS (" + "ID int not null primary key auto_increment," + "COMPANY_ID int,"
				+ "CATEGORY_ID int," + "TITLE varchar(255)," + "DESCRIPTION varchar(255)," + "START_DATE timestamp,"
				+ "END_DATE timestamp," + "AMOUNT int," + "PRICE double," + "IMAGE varchar(255))");

		arrayList.add("create table CUSTOMERS_VS_COUPONS (" + "CUSTOMER_ID int," + "COUPON_ID int)");

		return arrayList;

	}

	private static void initializeCategoryTable() {
		Category[] categories = Category.values();
		for (Category category : categories) {
			try {
				String sql = "insert into categories (name) values (?)";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, category.toString());
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
