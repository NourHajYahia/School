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

public class CompanyFacade extends  ClientFacade{

    private int companyID;

    public int getCompanyID() {
        return companyID;
    }

    public CompanyFacade() {
        this.companiesDAO = new CompaniesDBDAO();
        this.couponsDAO = new CouponsDBDAO();
    }

    /**
     * Authenticates login's email and password and returns value.
     * @param email
     * @param password
     * @return boolean
     */
    @Override
    public boolean login(String email, String password) throws FacadeException {
        try {
            Company company = companiesDAO.getCompanyByEmailAndPassword(email, password);
            if (company != null) {
                this.companyID = company.getId();
                return true;
            } else {
                return false;
            }
        } catch (DAOException e) {
            throw new FacadeException("CompanyFacade Error: log in failed,\n" + e.getMessage());
        }
    }

    /**
     * confirms that the coupon object to be added to coupons table obeys all business logic requirements and
     * calls DAO methods to communicate with the data base.
     *
     * @param coupon
     * @throws FacadeException
     * if coupon is out of date,
     * if coupon title already exist for the dame company.
     * if DAOException occurs.
     */
    public void addCoupon(Coupon coupon) throws FacadeException {
        try {
            coupon.setCompanyID(companyID);
            if (coupon.getEndDate().before(new Date()))
                throw new FacadeException("CompanyFacade Error: adding company failed: coupon is out off date");
            else if (couponsDAO.isCouponExistByTitleAndCompanyId(coupon.getTitle(), companyID))
                throw new FacadeException("CompanyFacade Error: adding company failed: coupon title already exist in this company");
            else couponsDAO.addCoupon(coupon);
        } catch (DAOException e) {
            throw new FacadeException("CompanyFacade Error: adding coupon failed,\n" + e.getMessage());
        }
    }

    /**
     * confirms that the coupon object to be updated to coupons table obeys all business logic requirements and
     * calls DAO methods to communicate with the data base.
     *
     * @param coupon
     * @throws FacadeException
     * if coupon is out of date,
     * if coupon title already exist for the dame company.
     * if coupon does not exist.
     * if DAOException occurs.
     */
    public void updateCoupon(Coupon coupon) throws FacadeException {
        Coupon currCoupon;
        try {
            coupon.setCompanyID(companyID);
            currCoupon = couponsDAO.getCouponById(coupon.getId());
            if (currCoupon == null)
                throw new FacadeException("CompanyFacade Error: updating company failed: coupon does not exist");
            else if (couponsDAO.isCouponExistByTitleAndCompanyId(coupon.getTitle(), companyID))
                throw new FacadeException("CompanyFacade Error: updating company failed: coupon title already exist in this company");
            else if (coupon.getEndDate().before(new Date()))
                throw new FacadeException("CompanyFacade Error: updating company failed: coupon is out off date");
            else couponsDAO.updateCoupon(coupon);
        } catch (DAOException e) {
            throw new FacadeException("CompanyFacade Error: updating coupon failed,\n" + e.getMessage());
        }
    }

    /**
     * confirms that the coupon object to be deleted to coupons table obeys all business logic requirements and
     * calls DAO methods to communicate with the data base.
     *
     * @throws FacadeException
     * if coupon does not exist.
     * if DAOException occurs.
     */
    public void deleteCoupon(int couponID) throws FacadeException {

        Coupon currCoupon;
        try {
            currCoupon = couponsDAO.getCouponById(couponID);
            if (currCoupon != null && currCoupon.getCompanyID() == companyID) {
                couponsDAO.deleteCouponPurchase(couponID);
                couponsDAO.deleteCoupon(couponID);
            } else throw new FacadeException("CompanyFacade Error: deleting coupon failed: coupon does not exist");
        } catch (DAOException e) {
            throw new FacadeException("CompanyFacade Error: deleting coupon failed,\n" + e.getMessage());
        }
    }

    /**
     * gets all coupons of this company.
     *
     * @return ArrayList
     * @throws FacadeException
     * if DAOException occurs.
     */
    public ArrayList<Coupon> getCompanyCoupons() throws FacadeException {
        try {
            return couponsDAO.getAllCouponsByCompanyId(companyID);
        } catch (DAOException e) {
            throw new FacadeException("CompanyFacade Error: getting coupons failed,\n" + e.getMessage());
        }
    }

    /**
     * gets all coupons of this company by category.
     *
     * @return ArrayList
     * @throws FacadeException
     * if DAOException occurs.
     */
    public ArrayList<Coupon> getCompanyCoupons(Category category) throws FacadeException {
        try {
            return couponsDAO.getAllCouponsByCompanyIdAndCategory(companyID, category.ordinal());
        } catch (DAOException e) {
            throw new FacadeException("CompanyFacade Error: getting coupons failed,\n" + e.getMessage());
        }
    }

    /**
     * gets all coupons of this company by max price.
     *
     * @return ArrayList
     * @throws FacadeException
     * if DAOException occurs.
     */
    public ArrayList<Coupon> getCompanyCoupons(double maxPrice) throws FacadeException {
        try {
            return couponsDAO.getAllCouponsByCompanyIdAndPrice(companyID, maxPrice);
        } catch (DAOException e) {
            throw new FacadeException("CompanyFacade Error: getting coupons failed,\n" + e.getMessage());
        }
    }

    /**
     * gets details of this company.
     *
     * @return Company
     * @throws FacadeException
     * if DAOException occurs.
     */
    public Company getCompanyDetails() throws FacadeException {
        try {
            return companiesDAO.getCompanyById(companyID);
        } catch (DAOException e) {
            throw new FacadeException("CompanyFacade Error: getting company details failed,\n" + e.getMessage());
        }
    }
}
