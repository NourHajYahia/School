package app.core;

import java.util.Date;

import app.core.repositories.CouponRepositories;


public class Tests {


	private CouponRepositories couponRepositories;
	
	public void test1() {
		Long count = couponRepositories.deleteByEndDateBefore(new Date(System.currentTimeMillis()));
		System.out.println(count);
	}
	
}
