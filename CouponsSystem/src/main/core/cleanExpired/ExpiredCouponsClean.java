package main.core.cleanExpired;


import java.util.ArrayList;
import java.util.Date;
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
			timer.scheduleAtFixedRate(new ExpiredCouponsTask(), 0, 50);
			System.out.println("Timer Start at: ");
		} catch (DAOException e) {
			throw new CouponSystemExceprion("System Error: initializing ExpiredCouponsTask failed", e);
		}
	}
	
	public void stop() {
		timer.cancel();
		System.out.println("Timer Stoped");
	}
	
	private class ExpiredCouponsTask extends TimerTask{
		
		@Override
		public void run() {
			try {
				ArrayList<Coupon> coupons = couponsDAO.getAllExpiredCoupons();
				if (!coupons.isEmpty()) {
					System.err.println("deleted");
					for (Coupon coupon : coupons) {
						System.out.println("deleting expired: " + coupon);
						couponsDAO.deleteCouponPurchase(coupon.getId());
						couponsDAO.deleteCoupon(coupon.getId());
					}
				}
				System.err.println("run fineshed at: " + new Date());
			} catch (DAOException e) {
				System.out.println(e.getMessage());
			}
		}
		
	}
	
}
