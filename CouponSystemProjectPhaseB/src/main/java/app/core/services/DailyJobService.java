package app.core.services;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.core.repositories.CouponRepositories;

@Service
@Transactional
public class DailyJobService {
	
	@Autowired
	private CouponRepositories repositories;
	
	public long deleteByEndDateBefore(Date expiredDate) {
		return repositories.deleteByEndDateBefore(expiredDate);
	}

}
