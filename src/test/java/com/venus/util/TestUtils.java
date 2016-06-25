package com.venus.util;

import com.venus.model.Invoice;
import com.venus.model.InvoiceStatus;
import com.venus.model.Party;
import com.venus.service.InvoiceService;
import com.venus.service.exception.InvoiceServiceException;
import org.junit.Assert;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by hrishikeshjoshi on 6/17/16.
 */
public class TestUtils {

    public static Date getDateWithoutTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        Date dateWithoutTime = calendar.getTime();
        return dateWithoutTime;
    }

    public static void createInvoice(Party party, InvoiceStatus invoiceStatus, InvoiceService invoiceService) {
        Invoice invoice = new Invoice();
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
    }
}
