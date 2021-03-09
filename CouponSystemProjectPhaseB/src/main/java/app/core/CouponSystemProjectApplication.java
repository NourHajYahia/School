package app.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import app.core.dailyJob.ExpiredCouponsCleaner;
import app.core.exceptions.LoginManagerException;
import app.core.test.AdminServiceTest;
import app.core.test.CompanyServiceTest;
import app.core.test.CustomerServiceTest;

@SpringBootApplication
@EnableScheduling
public class CouponSystemProjectApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(CouponSystemProjectApplication.class, args);
		
//		Tests tests = context.getBean(Tests.class);
//		tests.test1();
		
		ExpiredCouponsCleaner eCleaner = context.getBean(ExpiredCouponsCleaner.class);

		try {

			
			AdminServiceTest adminTest = context.getBean(AdminServiceTest.class);
			adminTest.runTest("admin@admin.com", "admin");
			
			CompanyServiceTest compaTest = context.getBean(CompanyServiceTest.class);
			compaTest.runTest("aaa@aaa.com", "aaa");

			CustomerServiceTest customTest = context.getBean(CustomerServiceTest.class);
			customTest.runTest("aaa@aaa.com", "aaa");

			Thread.sleep(50000);
			
		} catch (LoginManagerException e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		eCleaner.stop();
	}

}
