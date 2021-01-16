package test.dao;

import java.util.ArrayList;

import main.core.beans.Company;
import main.core.dao.CompaniesDAO;
import main.core.dao.impl.CompaniesDBDAO;
import main.core.exceptions.DAOException;

public class CompaniesDBDAOTest {
	
	static boolean fillFlag;
	
	public static void main(String[] args) {
		
		if (!fillFlag) {
			addAndFillTableTestSuccess();			
		}
		
	}
	
	public static void addAndFillTableTestSuccess() {
		try {
			fillFlag = true;
			int id = 0;
			ArrayList<Company> initialCompanies = new ArrayList<Company>();
			
			initialCompanies.add(new Company(id++, "aaa", "aaa@aaa.com", "aaa"));
			initialCompanies.add(new Company(id++, "bbb", "bbb@bbb.com", "bbb"));
			initialCompanies.add(new Company(id++, "ccc", "ccc@ccc.com", "ccc"));
			initialCompanies.add(new Company(id++, "ddd", "ddd@ddd.com", "ddd"));
			initialCompanies.add(new Company(id++, "eee", "eee@eee.com", "eee"));
			initialCompanies.add(new Company(id++, "fff", "fff@fff.com", "fff"));
			initialCompanies.add(new Company(id++, "ggg", "ggg@ggg.com", "ggg"));
			initialCompanies.add(new Company(id++, "hhh", "hhh@hhh.com", "hhh"));
			
			CompaniesDAO companiesDB = new CompaniesDBDAO();
			
			for (Company company : initialCompanies) {
				companiesDB.addCompany(company);
				System.out.println(company);
			}
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
