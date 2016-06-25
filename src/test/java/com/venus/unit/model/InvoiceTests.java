package com.venus.unit.model;

import com.venus.model.Invoice;
import com.venus.model.InvoiceStatus;
import com.venus.model.Item;
import com.venus.model.Party;
import com.venus.util.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

/**
 * Created by hrishikeshjoshi on 6/16/16.
 */
public class InvoiceTests {

    private Invoice invoice;

    private Validator validator;

    @Before
    public void init() {
        invoice = new Invoice();
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void testConstraintViolationsAreThrownForInvoiceWithEmptyInvoiceDate() {
        invoice.setParty(new Party("ABC","ABD"));
        invoice.setStatus(new InvoiceStatus("NEW"));
        final Set<ConstraintViolation<Invoice>> constraintViolations = validator.validate(invoice);
        Assert.assertTrue("Constraint Violations are raised",constraintViolations.stream().count() == 1 );

        final Optional<ConstraintViolation<Invoice>> oinvoiceConstraintViolation = constraintViolations.stream().findFirst();
        final ConstraintViolation<Invoice> invoiceConstraintViolation = oinvoiceConstraintViolation.get();
        Assert.assertEquals("Constraint Violation not thrown for Property Path = 'InvoiceDate' ","invoiceDate",invoiceConstraintViolation.getPropertyPath().toString());
    }

    @Test
    public void testConstraintViolationThrownWhenPartyIsNotSetForInvoice() {
        invoice.setInvoiceDate(new Date());
        invoice.setStatus(new InvoiceStatus("NEW"));
        final Set<ConstraintViolation<Invoice>> constraintViolations = validator.validate(invoice);
        Assert.assertTrue("Constraint Violations are raised",constraintViolations.stream().count() == 1 );

        final Optional<ConstraintViolation<Invoice>> oinvoiceConstraintViolation = constraintViolations.stream().findFirst();
        final ConstraintViolation<Invoice> invoiceConstraintViolation = oinvoiceConstraintViolation.get();
        Assert.assertEquals("Constraint Violation not thrown for Property Path = 'Party' ","party",invoiceConstraintViolation.getPropertyPath().toString());
    }

    @Test
    public void testConstraintViolationThrownWhenInvoiceStatusIsNotSetForInvoice() {
        invoice.setInvoiceDate(new Date());
        invoice.setParty(new Party("ABC","PQR"));
        final Set<ConstraintViolation<Invoice>> constraintViolations = validator.validate(invoice);
        Assert.assertTrue("Constraint Violations are raised",constraintViolations.stream().count() == 1 );

        final Optional<ConstraintViolation<Invoice>> oinvoiceConstraintViolation = constraintViolations.stream().findFirst();
        final ConstraintViolation<Invoice> invoiceConstraintViolation = oinvoiceConstraintViolation.get();
        Assert.assertEquals("Constraint Violation not thrown for Property Path = 'Invoice Status' ","status",invoiceConstraintViolation.getPropertyPath().toString());
    }

    @Test
    public void testIfInvoiceNetTotalIsZeroInitially() {
        Assert.assertEquals("Invoice Total is not zero when initialized.",new BigDecimal("0.0"),invoice.getNetTotal());
    }

    @Test
    public void testIfInvoiceTaxTotalIsZeroInitially() {
        Assert.assertEquals("Invoice Tax Total is not zero when initialized.",new BigDecimal("0.0"),invoice.getTaxAmount());
    }

    @Test
    public void testIfInvoiceGrandTotalIsZeroInitially() {
        Assert.assertEquals("Invoice Grand Total is not zero when initialized.",new BigDecimal("0.0"),invoice.getGrandTotal());
    }

    @Test
    public void testIfInvoiceItemsAreEmptyInitially() {
        Assert.assertNotNull("Invoice Items Set is null.",invoice.getItems());
        Assert.assertTrue("Invoice Items are not empty.", invoice.getItems().isEmpty());
    }

    @Test
    public void testIfInvoiceItemIsProperlyInitialized() {
        Item item = new Item(invoice);
        Assert.assertNotNull("Invoice is not null for the Invoice Item",item.getInvoice());
        Assert.assertEquals("Invoice Item Total is not zero when initialized.",new BigDecimal("0.0"),item.getLineTotalAmount());
        Assert.assertEquals("Invoice Item Quantity is not zero when initialized.",new BigDecimal("0.0"),item.getQuantity());
        Assert.assertEquals("Invoice Item Unit Price is not zero when initialized.",new BigDecimal("0.0"),item.getUnitPrice());
        Assert.assertEquals("Invoice Item Sequence number is 1.",new Integer(1),item.getSequenceNumber());
        Assert.assertTrue("Invoice Item Added Date is the current date.", TestUtils.getDateWithoutTime(item.getAddedDate()).compareTo(TestUtils.getDateWithoutTime(new Date())) == 0);
    }

    @Test
    public void testIfInvoiceItemSequenceNumberIsIncreasedAfterAddingNewItem() {
        final Item item1 = new Item(invoice);
        invoice.addLineItem(item1);
        Assert.assertEquals("Invoice Item Sequence number is 1.",new Integer(1),item1.getSequenceNumber());

        final Item item2 = new Item(invoice);
        invoice.addLineItem(item2);
        Assert.assertEquals("Invoice Item Sequence number is 2.",new Integer(2),item2.getSequenceNumber());
    }

    @Test
    public void testIfInvoiceItemIsDeleted() {
        final Item item1 = new Item(invoice);
        invoice.addLineItem(item1);

        final Item item2 = new Item(invoice);
        invoice.addLineItem(item2);

        invoice.deleteLineItem(item1);
        Assert.assertEquals("Invoice has only one line item",1,invoice.getItems().size());

    }

    @Test
    public void testIfSequenceNumberOfFirstItemIsNotChangedIfNextItemIsDeleted() {
        final Item item1 = new Item(invoice);
        invoice.addLineItem(item1);

        final Item item2 = new Item(invoice);
        invoice.addLineItem(item2);

        invoice.deleteLineItem(item2);

        final Optional<Item> oFirstItem = invoice.getItems().stream().findFirst();
        final Item item = oFirstItem.isPresent()?oFirstItem.get():null;

        Assert.assertEquals("Invoice Item Sequence number after last item is deleted is 1.",new Integer(1),item.getSequenceNumber());
    }

    @Test
    public void testIfSequenceNumberOfItemIsChangedIfAnItemInMiddleOfTwoItemsIsDeleted() {
        final Item item1 = new Item(invoice);
        invoice.addLineItem(item1);

        final Item item2 = new Item(invoice);
        invoice.addLineItem(item2);

        final Item item3 = new Item(invoice);
        invoice.addLineItem(item3);

        //Delete second item
        invoice.deleteLineItem(item2);

        final Item firstItem = invoice.getItems().stream().findFirst().get();
        Assert.assertEquals("First Invoice Item Sequence number after middle item is deleted is not 1 as before.",new Integer(1),firstItem.getSequenceNumber());

        final Item lastItem = invoice.getItems().stream().sorted().max((o1,o2) -> {
           return o1.getSequenceNumber().compareTo(o2.getSequenceNumber());
        }).get();

        Assert.assertEquals("First Invoice Item Sequence number after middle item is deleted is not 1 as before.",
                new Integer(2),lastItem.getSequenceNumber());
    }

    @Test
    public void testInvoiceNetTotalIsSumOfLineItemsTotal() {
        final Item item1 = new Item(invoice).setUnitPriceAndQuantity(new BigDecimal("100"),new BigDecimal("1"));
        invoice.addLineItem(item1);

        final Item item2 = new Item(invoice).setUnitPriceAndQuantity(new BigDecimal("200"),new BigDecimal("2"));
        invoice.addLineItem(item2);

        final Item item3 = new Item(invoice).setUnitPriceAndQuantity(new BigDecimal("300"),new BigDecimal("1"));
        invoice.addLineItem(item3);

        final BigDecimal total = invoice.getItems().stream().map(x -> x.getLineTotalAmount()).reduce(new BigDecimal("0.0"),
                (x,y) -> {
                    return x.add(y);
                });

        Assert.assertEquals("Sum of items",total,invoice.getNetTotal());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionIsThrownIfTheItemToBeDeletedIsNotAlreadyPresentInInvoice() {
        final Invoice invoice2 = new Invoice();
        final Item item = new Item(invoice2);

        invoice.deleteLineItem(item);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionIsThrownWhenItemToBeDeletedIsNull() {
        final Item item = null;
        invoice.deleteLineItem(item);
    }

    @Test
    public void testPartyIsAddedForInvoice() {
        final Party party = new Party("John Smith","Address 123");

        invoice.setParty(party);
        Assert.assertEquals("Party is set correctly", party, invoice.getParty());
        Assert.assertTrue("Invoice is present for the party",party.getInvoices().stream().anyMatch(x -> {
            return x.getParty().equals(party);
        }));


    }


}
