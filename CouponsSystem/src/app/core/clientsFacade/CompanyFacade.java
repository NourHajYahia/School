package app.core.clientsFacade;

import app.core.beans.Coupon;
import app.core.dao.impel.CompaniesDBDAO;
import app.core.dao.impel.CouponsDBDAO;
import app.core.exceptions.DAOException;
import app.core.exceptions.FacadeException;

public class CompanyFacade extends ClientFacade {
	
	private int companyID;
	
	public CompanyFacade() throws FacadeException {
		try {
			this.companiesDAO = new CompaniesDBDAO();
			this.couponsDAO = new CouponsDBDAO();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new FacadeException("CompanyFacade Error: initializing CompanyFacade failed", e);
		}
	}

	@Override
	public boolean login(String email, String password) throws FacadeException {
		
			try {
				if (companiesDAO.isCompanyExists(email, password)) {				
					companyID = companiesDAO.getCompanyByEmailAndPassword(email, password).getId();
					return true;
				}else {
					return false;
				}
			} catch (DAOException e) {
				e.printStackTrace();
				throw new FacadeException("CompanyFacade Error: log in CompanyFacade failed", e);
			}
	}
	
	public void addCoupon(Coupon coupon) throws FacadeException {
		
//		try {
//			if (!couponsDAO.isCouponTitleInCompany(coupon.getTitle(), coupon.getCompanyID())) {
//				couponsDAO.addCoupon(coupon);
//			}else {
//				throw new FacadeException("CompanyFacade Error: adding company failed: coupon title already exist in this company");
//			}
//		} catch (DAOException | FacadeException e) {
//			e.printStackTrace();
//			
//			throw new FacadeException("CompanyFacade Error: adding company failed", e);
//		}
		
	}
	
	public void updateCoupon(Coupon coupon) {
		
	}
	
	

}
