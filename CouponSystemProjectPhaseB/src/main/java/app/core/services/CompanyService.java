package app.core.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.exceptions.ServiceException;
import app.core.repositories.CompanyRepositories;
import app.core.repositories.CouponRepositories;

@Service
@Transactional
@Scope("prototype")
public class CompanyService extends ClientService {

	@Autowired
	CompanyRepositories companyRepositories;

	@Autowired
	CouponRepositories couponRepositories;

	private Integer companyID;

	/**
	 * Authenticates login's email and password and returns value.
	 * 
	 * @param email
	 * @param password
	 * @return boolean
	 */
	@Override
	public boolean login(String email, String password) {
		List<Company> companies = companyRepositories.findByEmailAndPassword(email, password);
		if (companies.isEmpty()) {
			return false;
		} else {
			this.companyID = companies.get(0).getId();
			return true;
		}
	}

	/**
	 * confirms that the coupon object to be added to coupons table obeys all
	 * business logic requirements and calls DAO methods to communicate with the
	 * data base.
	 *
	 * @param coupon
	 * @throws ServiceException if coupon is out of date, if coupon title already
	 *                          exist for the dame company. if DAOException occurs.
	 */
	public void addCoupon(Coupon coupon) throws ServiceException {
		Company company = getCompany();
		if (company == null)
			throw new ServiceException("CompanyFacade Error: adding coupon failed: company does not found");
		if (coupon.getEndDate().before(new Date()))
			throw new ServiceException("CompanyFacade Error: adding coupon failed: coupon is out off date");
		else if (couponRepositories.existsByTitleAndCompanyId(coupon.getTitle(), companyID))
			throw new ServiceException(
					"CompanyFacade Error: adding company failed: coupon title already exist in this company");
		else
			company.addCoupon(coupon);
	}

	/**
	 * confirms that the coupon object to be updated to coupons table obeys all
	 * business logic requirements and calls DAO methods to communicate with the
	 * data base.
	 *
	 * @param coupon
	 * @throws FacadeException if coupon is out of date, if coupon title already
	 *                         exist for the dame company. if coupon does not exist.
	 *                         if DAOException occurs.
	 */
	public void updateCoupon(Coupon coupon) throws ServiceException {
		Optional<Coupon> optional = couponRepositories.findById(coupon.getId());
		if (optional.isEmpty())
			throw new ServiceException("CompanyFacade Error: updating company failed: coupon does not exist");
		else if (couponRepositories.existsByTitleAndCompanyId(coupon.getTitle(), companyID))
			throw new ServiceException(
					"CompanyFacade Error: updating company failed: coupon title already exist in this company");
		else if (coupon.getEndDate().before(new Date()))
			throw new ServiceException("CompanyFacade Error: updating company failed: coupon is out off date");
		else
			couponRepositories.save(coupon);
	}

	/**
	 * confirms that the coupon object to be deleted to coupons table obeys all
	 * business logic requirements and calls DAO methods to communicate with the
	 * data base.
	 *
	 * @throws FacadeException if coupon does not exist. if DAOException occurs.
	 */
	public void deleteCoupon(int couponID) throws ServiceException {
		Optional<Coupon> optional = couponRepositories.findById(couponID);
		if (optional.isPresent()) {
			couponRepositories.delete(optional.get());
		} else
			throw new ServiceException("CompanyFacade Error: deleting coupon failed: coupon does not exist");

	}

	/**
	 * gets all coupons of this company.
	 *
	 * @return ArrayList
	 * @throws FacadeException if DAOException occurs.
	 */
	public List<Coupon> getCompanyCoupons() {
		return couponRepositories.findAllByCompanyId(this.companyID);
	}

	/**
	 * gets all coupons of this company by category.
	 *
	 * @return ArrayList
	 * @throws FacadeException if DAOException occurs.
	 */
	public List<Coupon> getCompanyCoupons(Category category) {
		return couponRepositories.findAllByCompanyIdAndCategory(this.companyID, category);
	}

	/**
	 * gets all coupons of this company by max price.
	 *
	 * @return ArrayList
	 * @throws FacadeException if DAOException occurs.
	 */
	public List<Coupon> getCompanyCoupons(Double maxPrice) {
		return couponRepositories.findAllByCompanyIdAndPrice(this.companyID, maxPrice);
	}

	/**
	 * gets details of this company.
	 *
	 * @return Company
	 * @throws ServiceException
	 * @throws FacadeException  if DAOException occurs.
	 */
	public Company getCompany() throws ServiceException {
		Optional<Company> optional = companyRepositories.findById(this.companyID);
		if (optional.isPresent()) {
			Company company = optional.get();
			return company;
		}
		throw new ServiceException("CompanyFacade Error: company is not found");
	}

}
