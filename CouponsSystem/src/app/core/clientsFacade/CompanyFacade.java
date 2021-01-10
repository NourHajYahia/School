package app.core.clientsFacade;

import java.util.ArrayList;

import app.core.beans.Category;
import app.core.beans.Company;
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
			e.printStackTrace();
			throw new FacadeException("CompanyFacade Error: initializing CompanyFacade failed", e);
		}
	}

	@Override
	public boolean login(String email, String password) throws FacadeException {

		try {
			if (companiesDAO.isCompanyExistByEmailAndPassword(email, password)) {
				companyID = companiesDAO.getCompanyByEmailAndPassword(email, password).getId();
				return true;
			} else {
				return false;
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("CompanyFacade Error: log in CompanyFacade failed", e);
		}
	}

	public void addCoupon(Coupon coupon) throws FacadeException {

		try {
			if (!couponsDAO.isCouponExistByTitleAndCompanyId(coupon.getTitle(), companyID) && coupon.getCompanyID() == companyID) {
				if (couponsDAO.isCatogeryExist(coupon.getCategory().ordinal())) {
					couponsDAO.addCategory(coupon.getCategory());
				}
				couponsDAO.addCoupon(coupon);
			} else {
				throw new FacadeException(
						"CompanyFacade Error: adding company failed: coupon title already exist in this company");
			}
		} catch (DAOException | FacadeException e) {
			e.printStackTrace();
			throw new FacadeException("CompanyFacade Error: adding company failed", e);
		}

	}

	public void updateCoupon(Coupon coupon) throws FacadeException {

		Coupon currCoupon;
		try {
			if (coupon.getCompanyID() == companyID) {
				currCoupon = couponsDAO.getCouponById(coupon.getId());
				if (currCoupon != null) {
					couponsDAO.updateCoupon(coupon);
				} else {
					throw new FacadeException("CompanyFacade Error: updating company failed: coupon does not exist");
				}
			}else {
				throw new FacadeException("CompanyFacade Error: updating company failed: not allowed to update company's id");
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("CompanyFacade Error: updating company failed", e);
		}

	}

	public void deleteCoupon(int couponID) throws FacadeException {

		Coupon currCoupon;
		try {
			currCoupon = couponsDAO.getCouponById(couponID);
			if (currCoupon != null && currCoupon.getCompanyID() == companyID) {
				couponsDAO.deleteCoupon(couponID);
			} else {
				throw new FacadeException("CompanyFacade Error: deleting coupon failed: coupon does not exist");
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("CompanyFacade Error: deleting coupon failed", e);
		}

	}

	public ArrayList<Coupon> getCompanyCoupons() throws FacadeException {
		try {
			return couponsDAO.getAllCouponsByCompanyId(companyID);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("CompanyFacade Error: getiing company coupons failed", e);
		}

	}

	public ArrayList<Coupon> getCompanyCoupons(Category category) throws FacadeException {
		try {
			return couponsDAO.getAllCouponsByCompanyIdAndCategory(companyID, category.ordinal());
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("CompanyFacade Error: getiing company coupons by catogery failed", e);
		}
	}

	public ArrayList<Coupon> getCompanyCoupons(double maxPrice) throws FacadeException {
		try {
			return couponsDAO.getAllCouponsByCompanyIdAndPrice(companyID, maxPrice);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("CompanyFacade Error: getiing company coupons by max price failed", e);
		}
	}

	public Company getCompanyDetails() throws FacadeException {
		try {
			return companiesDAO.getCompanyById(companyID);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("CompanyFacade Error: getiing company details failed", e);
		}
	}

}
