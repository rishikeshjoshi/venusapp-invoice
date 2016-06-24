package com.venus.unit.init;

import com.venus.InvoiceApplication;
import com.venus.init.ApplicationProperties;
import com.venus.repository.InvoiceStatusRepository;
import com.venus.repository.ItemTypeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * Created by hrishikeshjoshi on 6/16/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = InvoiceApplication.class)
public class MasterDataInitialiserTests {

    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    @Test
    public void dataInitialised() {
        //Check that application context loads properly
        Assert.assertNotNull("Application Context is not initialized properly",context);

    }

    @Test
    public void testInvoiceStatusRepositoryIsInitialised() {
        //Check that InvoiceStatusRepository is injected correctly.
        assertThatBeanIsNotNull(InvoiceStatusRepository.class);
    }

    @Test
    public void testItemTypeRepositoryIsInitialised() {
        //Check that InvoiceType Repository is loaded correctly.
        assertThatBeanIsNotNull(ItemTypeRepository.class);
    }

    @Test
    public void testApplicationPropertiesIsInitialised() {
        //Check that InvoiceType Repository is loaded correctly.
        assertThatBeanIsNotNull(ApplicationProperties.class);
    }

    @Test
    public void testThatInvoiceStatusRepositoryIsInitialized() {
        final InvoiceStatusRepository invoiceStatusRepository = context.getBean(InvoiceStatusRepository.class);
        final long count = invoiceStatusRepository.count();
        Assert.assertTrue("Invoice Status Repository is not initialized.",count > 0);
    }

    @Test
    public void testThatItemTypeRepositoryIsInitialized() {
        final ItemTypeRepository itemTypeRepository = context.getBean(ItemTypeRepository.class);
        final long count = itemTypeRepository.count();
        Assert.assertTrue("Invoice Status Repository is not initialized.",count > 0);
    }

    /**
     * Check that the bean is initialized correctly
     * @param cls
     */
    private void assertThatBeanIsNotNull(Class cls) {
        final Object repository = context.getBean(cls);
        Assert.assertNotNull(cls.getClass().getName() + " is not injected correctly", repository);
    }

}
