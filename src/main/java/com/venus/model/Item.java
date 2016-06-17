package com.venus.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.OptionalInt;

/**
 * Created by hrishikeshjoshi on 6/14/16.
 */
@Entity
public class Item implements Comparable<Item> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Integer sequenceNumber;

    private BigDecimal unitPrice = new BigDecimal("0.0");

    private BigDecimal quantity = new BigDecimal("0.0");

    private BigDecimal lineTotalAmount = new BigDecimal("0.0");

    @ManyToOne
    private Invoice invoice;

    @ManyToOne
    private ItemType type;

    private String description;

    private Date addedDate = new Date();

    Item(){

    }

    public Item(Invoice invoice) {
        this.invoice = invoice;

        //Get sequence number for Invoice Item
        final OptionalInt optionalInt = invoice.getItems().stream().mapToInt(x -> x.getSequenceNumber()).max();
        final Integer max = optionalInt.isPresent()?optionalInt.getAsInt():new Integer(0);
        this.sequenceNumber = max.intValue() + 1;

        //TODO : Check if this is ok.
        invoice.addLineItem(this);
    }

    public Item setUnitPriceAndQuantity(BigDecimal unitPrice, BigDecimal quantity) {
        this.setUnitPrice(unitPrice);
        this.setQuantity(quantity);
        updateLineTotalAmount();
        return this;
    }

    private void updateLineTotalAmount() {
        this.setLineTotalAmount(unitPrice.multiply(quantity));
    }

    //Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        updateLineTotalAmount();
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        updateLineTotalAmount();
    }

    public BigDecimal getLineTotalAmount() {
        return lineTotalAmount;
    }

    public void setLineTotalAmount(BigDecimal lineTotalAmount) {
        this.lineTotalAmount = lineTotalAmount;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    @Override
    public int compareTo(Item o) {
        return this.getSequenceNumber().compareTo(o.getSequenceNumber());
    }
}
