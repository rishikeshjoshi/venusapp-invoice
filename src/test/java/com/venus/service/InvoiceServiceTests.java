package com.venus.service;

import com.venus.model.Invoice;
import org.junit.Before;

/**
 * Created by hrishikeshjoshi on 6/16/16.
 */
public class InvoiceServiceTests {


    private Invoice invoice;

    @Before
    public void init() {
        invoice = new Invoice();
    }

    /**
     * Check that an invoice is persisted
     */
    public void invoiceIsPersisted() {
        Invoice invoice = new Invoice();
    }

}
