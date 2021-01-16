package main.core.dao.impl;

import main.core.exceptions.DAOException;

public class Test {

	public static void main(String[] args) {

		try {
			CompaniesDBDAO companies = new CompaniesDBDAO();
			System.out.println(companies.isCompanyExistByName("aaa"));
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
