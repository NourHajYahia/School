package app.core.dailyJob;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import app.core.services.DailyJobService;

@Component
@Lazy
public class ExpiredCouponsCleaner {

	private final Timer timer;
	private DailyJobService service;

	/**
	 * initializing the cleaner and it's scheduled timer
	 * 
	 */
	@Autowired
	public ExpiredCouponsCleaner(DailyJobService service) {
		this.service = service;
		timer = new Timer();
		timer.scheduleAtFixedRate(new ExpiredCouponsTask(), 0, 1000);
		System.out.println(">>>>>>>>>> Timer Started");
	}

	/*
	 * stops the cleaner timer
	 */
	public void stop() {
		timer.cancel();
		timer.purge();
		System.out.println(">>>>>>>>>> Timer Stopped");
	}

	// timer task method for deleting expired coupons
	private class ExpiredCouponsTask extends TimerTask {

		@Override
		public void run() {
			System.out.println(">>>>> Timer run");
			Long count = service.deleteByEndDateBefore(new Date(System.currentTimeMillis()));
			System.out.println(">>>>>>>>>> delted raws: " + count);
		}
		
	}
}
