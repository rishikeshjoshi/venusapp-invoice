package com.venus.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by hrishikeshjoshi on 6/26/16.
 */
public class NewInvoice {

    private String partyName;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date invoiceDate;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date dueDate;

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
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
}
