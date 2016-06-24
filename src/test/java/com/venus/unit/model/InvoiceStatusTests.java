package com.venus.unit.model;

import com.venus.model.InvoiceStatus;
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

    }

    @Test
    public void invoiceStatusValueAndDescriptionMatches() {
        final String value = "VALUE1";
        final String description = "This is a sample description";

        invoiceStatus = new InvoiceStatus(value,description);

        Assert.assertEquals("InvoiceStatus Value doesnt match",value,invoiceStatus.getValue());
        Assert.assertEquals("InvoiceStatus Description doesnt match",description,invoiceStatus.getDescription());
    }

}
