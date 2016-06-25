package com.venus.integration.service;

import com.venus.InvoiceApplication;
import com.venus.model.Invoice;
import com.venus.model.InvoiceStatus;
import com.venus.model.Party;
import com.venus.repository.InvoiceRepository;
import com.venus.repository.InvoiceStatusRepository;
import com.venus.repository.PartyRepository;
import com.venus.service.InvoiceService;
import com.venus.service.exception.InvoiceServiceException;
import com.venus.util.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.List;

/**
 * Created by hrishikeshjoshi on 6/23/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = InvoiceApplication.class)
@WebAppConfiguration
public class InvoiceServiceIntTests {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceStatusRepository invoiceStatusRepository;

    private Party party;
    private InvoiceStatus invoiceStatus;

    @Before
    public void beforeTests() {
        //Deleting all invoices
        invoiceRepository.deleteAll();

        //Delete and create a party.
        partyRepository.deleteAll();

        party = new Party("John Smith", "Address");
        partyRepository.save(party);
        Assert.assertNotEquals("Party is not saved",new Long(0), party.getId());

        invoiceStatus = invoiceStatusRepository.findByValue("NEW");
        Assert.assertNotNull("Invoice Status - NEW is not present in the master data", invoiceStatus);

    }

    @Test
    public void testApplicationContextLoads() {
        Assert.assertNotNull("Application context is not loaded properly", context);
        Assert.assertNotNull("Invoice service is not null", invoiceService);
    }

    @Test
    public void invoiceIsSavedWithCorrectAttributes() {
        TestUtils.createInvoice(party,invoiceStatus,invoiceService);
    }

    @Test(expected = InvoiceServiceException.class)
    public void exceptionIsThrownWhenInvoiceIsIncorrect() throws InvoiceServiceException {
        Invoice invoice = new Invoice();

        //Set Invoice Status
        invoice.setStatus(invoiceStatus);

        //Set Invoice Date
        invoice.setInvoiceDate(new Date());
        invoice = invoiceService.saveInvoice(invoice);
    }

    @Test
    public void listOfInvoicesIsReturned() {
        TestUtils.createInvoice(party,invoiceStatus,invoiceService);
        TestUtils.createInvoice(party,invoiceStatus,invoiceService);

        final List<Invoice> allInvoices = invoiceService.getAllInvoices();
        Assert.assertNotNull("No invoices returned.", allInvoices);
        Assert.assertNotEquals("Invoices not found",0,allInvoices.size());
    }

    @Test
    public void noListIsReturnedIfInvoicesAreEmpty() {
        invoiceRepository.deleteAll();

        final List<Invoice> allInvoices = invoiceService.getAllInvoices();
        Assert.assertNotNull("No invoices returned.", allInvoices);
        Assert.assertEquals("Invoices not found",0,allInvoices.size());
    }

    @Test
    public void invoicesAreReturnedForAGivenParty() {
        //Created two invoices.
        TestUtils.createInvoice(party,invoiceStatus,invoiceService);
        TestUtils.createInvoice(party,invoiceStatus,invoiceService);

        List<Invoice> invoicesForParty = invoiceService.getAllInvoices(party);
        Assert.assertNotNull("No invoices returned.", invoicesForParty);
        Assert.assertNotEquals("Invoices not found",0,invoicesForParty.size());

        invoicesForParty.forEach(x -> {
            Assert.assertEquals("Party does not match", party.getId(), x.getParty().getId());
        });
    }

    @Test
    public void testThatNoInvoicesAreCorrectlyReturned() {
        Party party2 = new Party("Jerry Smith", "Address");
        partyRepository.save(party2);

        TestUtils.createInvoice(party2,invoiceStatus,invoiceService);

        List<Invoice> invoicesForParty = invoiceService.getAllInvoices(party);
        Assert.assertNotNull("No invoices returned.", invoicesForParty);
        Assert.assertEquals("Invoices not found",0,invoicesForParty.size());
    }
}
