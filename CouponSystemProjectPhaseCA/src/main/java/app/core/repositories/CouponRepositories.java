package app.core.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Category;
import app.core.entities.Coupon;

public interface CouponRepositories extends JpaRepository<Coupon, Integer> {

	boolean existsByTitleAndCompanyId(String title, Integer companyID);
	
	Long deleteByEndDateBefore(Date endDate);

	List<Coupon> findAllByCompanyId(int companyID);

	List<Coupon> findAllByCompanyIdAndCategory(int companyID, Category category);

	List<Coupon> findAllByCompanyIdAndPrice(int companyID, Double price);

	boolean existsByIdAndCustomersId(int id, int customerID);

	List<Coupon> getCouponsByCustomersId(int customerID);

	List<Coupon> getCouponsByCustomersIdAndCategory(int customerID, Category category);

	List<Coupon> getCouponsByCustomersIdAndPriceLessThanEqual(int customerID, Double price);

}
