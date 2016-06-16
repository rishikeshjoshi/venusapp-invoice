package com.venus.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * Created by hrishikeshjoshi on 6/14/16.
 */
@Entity
public class Invoice implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String invoiceNumber;

    private Date invoiceDate;

    private Date dueDate;

    private BigDecimal netTotal = new BigDecimal("0.0");

    private BigDecimal taxAmount = new BigDecimal("0.0");

    private BigDecimal grandTotal = new BigDecimal("0.0");

    @ManyToOne
    private InvoiceStatus status;

    @OneToMany(mappedBy = "invoice",cascade = CascadeType.ALL)
    private Set<Item> items;

    /**
     *
     * @param item
     */
    public void addLineItem(Item item) {
       if(item == null) {
           throw new IllegalArgumentException("Invoice Item cannot be null");
       }

       //Check if the line items are null or empty and initialize the line items for the invoice
       if(this.items == null || items.isEmpty()) {
           this.items = new TreeSet<Item>();
       }

       this.items.add(item);

       //Calculate Net Total.
       calculateNetTotal();

    }

    /**
     * Calculate net total for the invoice
     */
    private void calculateNetTotal() {
        final BinaryOperator<BigDecimal> adder = (x, y) -> x.add(y);
        //maps from an Item object to a BigDecimal object.
        final Function<Item, BigDecimal> multiplier =
                 e -> e.getQuantity().multiply(e.getUnitPrice());

        //Calculate the net total for the invoice.
        this.netTotal = items.stream()
                 .map(multiplier)
                 .reduce(new BigDecimal("0.0"), adder);
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(BigDecimal netTotal) {
        this.netTotal = netTotal;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }
}
