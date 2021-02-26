package main.core.dailyJob;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import main.core.beans.Coupon;
import main.core.dao.CouponsDAO;
import main.core.dao.impl.CouponsDBDAO;
import main.core.exceptions.DAOException;

public class ExpiredCouponsCleaner {

    private final Timer timer;
    private final CouponsDAO couponsDAO;

    
    /**
     * initializing the cleaner and it's scheduled timer
     * 
     */
    public ExpiredCouponsCleaner() {
        this.couponsDAO = new CouponsDBDAO();
        timer = new Timer();
        timer.scheduleAtFixedRate(new ExpiredCouponsTask(), 0, 1000 * 60);
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

    //timer task method for deleting expired coupons
    private class ExpiredCouponsTask extends TimerTask {

        @Override
        public void run() {
            try {
            	System.out.println(">>>>>>>>>> Timer on run");
                ArrayList<Coupon> coupons = couponsDAO.getAllExpiredCoupons();
                if (!coupons.isEmpty()) {
                    for (Coupon coupon : coupons) {
                        System.out.println(">>>>>>>>>> deleted expired: " + coupon);
                        couponsDAO.deleteCouponPurchase(coupon.getId());
                        couponsDAO.deleteCoupon(coupon.getId());
                    }
                }
            } catch (DAOException e) {
                System.out.println("System Error: daily job: ExpiredCouponsTask failed" + e.getMessage());
                timer.cancel();
            }
        }
    }
}
