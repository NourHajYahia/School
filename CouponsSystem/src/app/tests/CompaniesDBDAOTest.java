package app.tests;

import java.util.ArrayList;
import java.util.List;

import app.core.beans.Category;
import app.core.beans.Company;
import app.core.dao.impel.CompaniesDBDAO;
import app.core.exceptions.DAOException;

public class CompaniesDBDAOTest {

	public static void main(String[] args) {
		
		Company updateCompany = new Company(2, "Nurs", "Nurs@Nurs.com", "12345");
		Company getCompany = new Company();
		List<Company> companies = new ArrayList<Company>();
		boolean doesExists = false;
		
		try {
			CompaniesDBDAO compDao = new CompaniesDBDAO();
			companies = compDao.getAllCompanies();
			getCompany = compDao.getCompanyById(3);
			doesExists = compDao.isCompanyExists("fff@fff.com", "fff");
			//compDao.updateCompany(updateCompany);
			//compDao.deleteCompany(2);
			compDao.addCompany(updateCompany);
			
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("=========== getAllCompanies Test ==========");
		for (Company company : companies) {
			System.out.println(company);			
		}
		
		System.out.println();
		System.out.println("========== getOneCompany Test ===========");
		System.out.println(getCompany);
		
		
		System.out.println();
		System.out.println("============ isExists Test ================");
		System.out.println("does company with name 'fff' exist? " + doesExists);
		
		System.out.println();
		System.out.println("============ updateCompany Test ================");
		System.out.println(updateCompany);
		
		System.out.println();
		System.out.println("============ updateCompany Test ================");
		System.out.println("deleting company with id 2 ");
		
		Category category = Category.FOOD;
		
		System.out.println(category);
		
		

	}

}
