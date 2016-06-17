package com.venus;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = InvoiceApplication.class)
@WebAppConfiguration
public class InvoiceApplicationTests {

	private ApplicationContext context;

	@Autowired
	public void setContext(ApplicationContext context) {
		this.context = context;
	}

	@Test
	public void contextLoads() {
		Assert.assertNotNull("Application Context not created.",context);
	}

	public void testMasterDataIsInitialised() {

	}

}
