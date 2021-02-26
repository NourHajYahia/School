package app.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import app.core.exceptions.LoginManagerException;
import app.core.test.AdminServiceTest;
import app.core.test.CompanyServiceTest;
import app.core.test.CustomerServiceTest;

@SpringBootApplication
public class CouponSystemProjectApplication {

	public static void main(String[] args) {

		try {
			ApplicationContext context = SpringApplication.run(CouponSystemProjectApplication.class, args);
			
			AdminServiceTest adminTest = context.getBean(AdminServiceTest.class);
			adminTest.runTest("admin@admin.com", "admin");
			
			CompanyServiceTest compaTest = context.getBean(CompanyServiceTest.class);
			compaTest.runTest("aaa@aaa.com", "aaa");
			
			CustomerServiceTest customTest = context.getBean(CustomerServiceTest.class);
			customTest.runTest("aaa@aaa.com", "aaa");
			
		} catch (LoginManagerException e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			e.printStackTrace();
		}

	}

}
