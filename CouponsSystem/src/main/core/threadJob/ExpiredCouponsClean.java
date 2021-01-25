package main.core.threadJob;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import main.core.beans.Coupon;
import main.core.dao.CouponsDAO;
import main.core.dao.impl.CouponsDBDAO;
import main.core.exceptions.CouponSystemExceprion;
import main.core.exceptions.DAOException;

public class ExpiredCouponsClean  {

	private Timer timer;
	private CouponsDAO couponsDAO;

	public ExpiredCouponsClean() throws CouponSystemExceprion {
		super();
		try {
			this.couponsDAO = new CouponsDBDAO();
			timer = new Timer();
			timer.scheduleAtFixedRate(new ExpiredCouponsTask(), 0, 1000*60);
			System.out.println(">>>>>>>>>> Timer Started");
		} catch (DAOException e) {
			throw new CouponSystemExceprion("System Error: initializing ExpiredCouponsTask failed", e);
		}
	}
	
	public void stop() {
		timer.cancel();
		timer.purge();
		System.out.println(">>>>>>>>>> Timer Stoped");
	}
	
	private class ExpiredCouponsTask extends TimerTask{
		
		@Override
		public void run() {
			try {
				ArrayList<Coupon> coupons = couponsDAO.getAllExpiredCoupons();
				if (!coupons.isEmpty()) {
					for (Coupon coupon : coupons) {
						System.out.println(">>>>>>>>>> deleted expired: " + coupon);
						couponsDAO.deleteCouponPurchase(coupon.getId());
						couponsDAO.deleteCoupon(coupon.getId());
					}
				}
			} catch (DAOException e) {
				System.out.println(e.getMessage());
			}
		}
		
	}
	
}
