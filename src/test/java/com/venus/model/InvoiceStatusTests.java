package com.venus.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by hrishikeshjoshi on 6/17/16.
 */
public class InvoiceStatusTests {

    private InvoiceStatus invoiceStatus;

    @Before
    public void init() {
        invoiceStatus = new InvoiceStatus();
    }

    @Test
    public void invoiceStatusValueAndDescriptionMatches() {
        final String value = "VALUE1";
        final String description = "This is a sample description";

        invoiceStatus.setValue(value);
        invoiceStatus.setDescription(description);

        Assert.assertEquals("InvoiceStatus Value doesnt match",value,invoiceStatus.getValue());
        Assert.assertEquals("InvoiceStatus Description doesnt match",description,invoiceStatus.getDescription());
    }

}
