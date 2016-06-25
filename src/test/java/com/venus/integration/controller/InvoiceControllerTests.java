package com.venus.integration.controller;

import com.venus.InvoiceApplication;
import com.venus.model.Invoice;
import com.venus.model.InvoiceStatus;
import com.venus.model.Party;
import com.venus.repository.InvoiceRepository;
import com.venus.repository.InvoiceStatusRepository;
import com.venus.repository.PartyRepository;
import com.venus.service.InvoiceService;
import com.venus.service.exception.InvoiceServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

/**
 * Created by hrishikeshjoshi on 6/25/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = InvoiceApplication.class)
@WebAppConfiguration
public class InvoiceControllerTests {

    public static final String URI_INVOICES_ALL = "/invoices";

    public static final String URI_INVOICES_BY_PARTY_ID = "/invoices/party/";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private InvoiceStatusRepository invoiceStatusRepository;

    @Autowired
    private InvoiceService invoiceService;

    private MockMvc mockMvc;

    private Party party;

    private InvoiceStatus invoiceStatus;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        invoiceRepository.deleteAll();
        partyRepository.deleteAll();
    }

    @Test
    public void testThatInvoiceListURIReturnsJSON() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URI_INVOICES_ALL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testInvoicesAreReturnedByTheInvoiceAllURI() throws Exception {

        invoiceRepository.deleteAll();

        //Delete and create a party.
        partyRepository.deleteAll();

        party = new Party("John Smith", "Address");
        partyRepository.save(party);
        Assert.assertNotEquals("Party is not saved",new Long(0), party.getId());

        invoiceStatus = invoiceStatusRepository.findByValue("NEW");
        Assert.assertNotNull("Invoice Status - NEW is not present in the master data", invoiceStatus);

        //Create a new invoice
        Invoice invoice = new Invoice();

        invoice.setInvoiceNumber("INV001");
        //Set Party
        invoice.setParty(party);

        //Set Invoice Status
        invoice.setStatus(invoiceStatus);

        //Set Invoice Date
        invoice.setInvoiceDate(new Date());
        try {
            invoice = invoiceService.saveInvoice(invoice);
        } catch (InvoiceServiceException e) {
            Assert.fail("Exception thrown from the invoice service:" + e.getMessage());
        }

        Assert.assertNotEquals("Invoice is saved", null, invoice.getId());

        //Verify that invoices exists as arrays.
        mockMvc.perform(MockMvcRequestBuilders.get(URI_INVOICES_ALL))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].invoiceNumber").value("INV001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].party.name").value("John Smith"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status.value").value("NEW"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].invoiceDate").value("25-06-2016"));
    }


    @Test
    public void testInvoicesAreReturnedForParty() throws Exception {

        invoiceRepository.deleteAll();

        //Delete and create a party.
        partyRepository.deleteAll();

        party = new Party("John Smith", "Address");
        partyRepository.save(party);
        Assert.assertNotEquals("Party is not saved",new Long(0), party.getId());

        invoiceStatus = invoiceStatusRepository.findByValue("NEW");
        Assert.assertNotNull("Invoice Status - NEW is not present in the master data", invoiceStatus);

        //Create a new invoice
        Invoice invoice = new Invoice();

        invoice.setInvoiceNumber("INV001");
        //Set Party
        invoice.setParty(party);

        //Set Invoice Status
        invoice.setStatus(invoiceStatus);

        //Set Invoice Date
        invoice.setInvoiceDate(new Date());
        try {
            invoice = invoiceService.saveInvoice(invoice);
        } catch (InvoiceServiceException e) {
            Assert.fail("Exception thrown from the invoice service:" + e.getMessage());
        }

        Assert.assertNotEquals("Invoice is saved", null, invoice.getId());

        //Verify that invoices exists as arrays.
        mockMvc.perform(MockMvcRequestBuilders.get(URI_INVOICES_BY_PARTY_ID+"/" + party.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].invoiceNumber").value("INV001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].invoiceDate").value("25-06-2016"));
    }

    @Test
    public void testNoInvoicesAreReturnedForInvalidParty() throws Exception {

        invoiceRepository.deleteAll();

        party = new Party("John Smith", "Address");
        partyRepository.save(party);
        Assert.assertNotEquals("Party is not saved",new Long(0), party.getId());

        invoiceStatus = invoiceStatusRepository.findByValue("NEW");
        Assert.assertNotNull("Invoice Status - NEW is not present in the master data", invoiceStatus);

        //Create a new invoice
        Invoice invoice = new Invoice();

        invoice.setInvoiceNumber("INV001");
        //Set Party
        invoice.setParty(party);

        //Set Invoice Status
        invoice.setStatus(invoiceStatus);

        //Set Invoice Date
        invoice.setInvoiceDate(new Date());
        try {
            invoice = invoiceService.saveInvoice(invoice);
        } catch (InvoiceServiceException e) {
            Assert.fail("Exception thrown from the invoice service:" + e.getMessage());
        }

        Assert.assertNotEquals("Invoice is saved", null, invoice.getId());

        //Verify that invoices exists as arrays.
        mockMvc.perform(MockMvcRequestBuilders.get(URI_INVOICES_BY_PARTY_ID+"/" + 0))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    public void invoiceIsReturnedForInvoiceId() throws Exception {
        //Create new party
        party = new Party("John Smith", "Address");
        partyRepository.save(party);
        Assert.assertNotEquals("Party is not saved",new Long(0), party.getId());

        invoiceStatus = invoiceStatusRepository.findByValue("NEW");
        Assert.assertNotNull("Invoice Status - NEW is not present in the master data", invoiceStatus);

        //Create a new invoice
        Invoice invoice = new Invoice();

        invoice.setInvoiceNumber("INV001");
        //Set Party
        invoice.setParty(party);

        //Set Invoice Status
        invoice.setStatus(invoiceStatus);

        //Set Invoice Date
        invoice.setInvoiceDate(new Date());
        try {
            invoice = invoiceService.saveInvoice(invoice);
        } catch (InvoiceServiceException e) {
            Assert.fail("Exception thrown from the invoice service:" + e.getMessage());
        }

        mockMvc.perform(MockMvcRequestBuilders.get("/invoices/" + invoice.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.invoiceNumber").value("INV001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.invoiceDate").value("25-06-2016"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.value").value("NEW"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.party.name").value("John Smith"));

    }



}
