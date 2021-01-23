package main.core.facade;

import java.util.ArrayList;
import java.util.Date;

import main.core.beans.Category;
import main.core.beans.Company;
import main.core.beans.Coupon;
import main.core.dao.impl.CompaniesDBDAO;
import main.core.dao.impl.CouponsDBDAO;
import main.core.exceptions.DAOException;
import main.core.exceptions.FacadeException;

public class CompanyFacade extends ClientFacade {

	public int getCompanyID() {
		return companyID;
	}

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
			companyID = companiesDAO.getCompanyByEmailAndPassword(email, password);
			if (companyID != -1) {
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
			coupon.setCompanyID(companyID);
			if (coupon.getEndDate().before(new Date())) {
				throw new FacadeException("CompanyFacade Error: adding company failed: coupon is out off date");
			} else if (couponsDAO.isCouponExistByTitleAndCompanyId(coupon.getTitle(), companyID)) {
				throw new FacadeException(
						"CompanyFacade Error: adding company failed: coupon title already exist in this company");
			} else {
				couponsDAO.addCoupon(coupon);
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new FacadeException("CompanyFacade Error: adding company failed" + e.getMessage());
		}

	}

	public void updateCoupon(Coupon coupon) throws FacadeException {

		Coupon currCoupon;
		try {
			coupon.setCompanyID(companyID);
			currCoupon = couponsDAO.getCouponById(coupon.getId());
			if (currCoupon == null) {
				throw new FacadeException("CompanyFacade Error: updating company failed: coupon does not exist");
			} else if (couponsDAO.isCouponExistByTitleAndCompanyId(coupon.getTitle(), companyID)) {
				throw new FacadeException(
						"CompanyFacade Error: updating company failed: coupon title already exist in this company");
			} else if (coupon.getEndDate().before(new Date())) {
				throw new FacadeException("CompanyFacade Error: updating company failed: coupon is out off date");
			} else {
				couponsDAO.updateCoupon(coupon);
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
				couponsDAO.deleteCouponPurchase(couponID);
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
