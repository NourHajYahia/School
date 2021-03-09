package app.core.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Category;
import app.core.entities.Coupon;

public interface CouponRepositories extends JpaRepository<Coupon, Integer> {

	boolean existsByTitleAndCompanyId(String title, Integer companyID);
	
	Long deleteByEndDateBefore(Date endDate);

	List<Coupon> findAllByCompanyId(Integer companyID);

	List<Coupon> findAllByCompanyIdAndCategory(Integer companyID, Category category);

	List<Coupon> findAllByCompanyIdAndPrice(Integer companyID, Double price);

	boolean existsByIdAndCustomersId(Integer id, Integer customerID);

	List<Coupon> getCouponsByCustomersId(Integer customerID);

	List<Coupon> getCouponsByCustomersIdAndCategory(Integer customerID, Category category);

	List<Coupon> getCouponsByCustomersIdAndPriceLessThanEqual(Integer customerID, Double price);

}
