package com.venus.unit.model;

import com.venus.model.Invoice;
import com.venus.model.Item;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hrishikeshjoshi on 6/17/16.
 */
public class ItemTests {

    private Item item;

    private Invoice invoice;

    @Before
    public void init() {
        invoice = new Invoice();
    }

    @Test
    public void testIfInvoiceIsPresentForLineItem() {
        item = new Item(invoice);
        Assert.assertNotNull("Invoice Item is null.",item.getInvoice());
        Assert.assertEquals("Invoice is not same as expected.",invoice,item.getInvoice());
    }

    @Test
    public void testItemUnitPriceAndQuantityIsPresent() {
        final BigDecimal unitPrice = new BigDecimal("100");
        final BigDecimal quantity = new BigDecimal("1.0");

        item = new Item(invoice).setUnitPriceAndQuantity(unitPrice, quantity);

        Assert.assertEquals("Item Unit Price is not set as expected.",unitPrice,item.getUnitPrice());

        Assert.assertEquals("Item Quantity is not set as expected.",quantity,item.getQuantity());
    }

    @Test
    public void testLineTotalAmountIsSetCorrectly() {
        final BigDecimal unitPrice = new BigDecimal("100");
        final BigDecimal quantity = new BigDecimal("1.0");

        item = new Item(invoice).setUnitPriceAndQuantity(unitPrice, quantity);
        Assert.assertEquals("The Line Total amount is not equal to unit price multiplied by quantity",unitPrice.multiply(quantity),item.getLineTotalAmount());
    }

    @Test
    public void testLineItemsAreSortedAsPerSequenceNumberAscending() {
        final Item item1 = new Item(invoice);
        final Item item2 = new Item(invoice);

        final List<Integer> actualSequenceNumbers = invoice.getItems().stream().map(x -> x.getSequenceNumber()).collect(Collectors.toList());

        Assert.assertArrayEquals("Item Sequence Numbers are not sorted in ascending order.",new Integer[]{1,2},actualSequenceNumbers.toArray(new Integer[actualSequenceNumbers.size()]));
    }



}
