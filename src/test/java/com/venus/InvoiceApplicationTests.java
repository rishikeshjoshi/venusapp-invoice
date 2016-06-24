package com.venus;

import com.venus.repository.InvoiceStatusRepository;
import com.venus.repository.ItemTypeRepository;
import com.venus.repository.PartyRepository;
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

	@Test
	public void testMasterDataForInvoiceStatusIsInitialised() {
		InvoiceStatusRepository invoiceStatusRepository = context.getBean(InvoiceStatusRepository.class);
		Assert.assertNotNull("Invoice Status repository is not initialized", invoiceStatusRepository);
		Assert.assertNotEquals("Invoice Status is not setup correctly",0,invoiceStatusRepository.count());
	}

	@Test
	public void testMasterDataForItemTypeIsInitialised() {
		ItemTypeRepository itemTypeRepository = context.getBean(ItemTypeRepository.class);
		Assert.assertNotNull("Item type repository is not initialized", itemTypeRepository);
		Assert.assertNotEquals("Invoice type is not setup correctly",0,itemTypeRepository.count());
	}

	@Test
	public void testValidatorFactoryIsNotNull() {
		Assert.assertNotNull("Validation Factory is not null", context.getBean("validatorFactory"));
	}

	@Test
	public void testPartyRepositoryIsNotNull() {
		Assert.assertNotNull("Party repository is not null", context.getBean(PartyRepository.class));
	}


}
