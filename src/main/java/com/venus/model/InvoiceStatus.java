package com.venus.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by hrishikeshjoshi on 6/15/16.
 */
@Entity
public class InvoiceStatus implements Serializable {

    @Id
    private String value;

    private String description;

    InvoiceStatus(){}

    public InvoiceStatus(String value) {
        this(value,null);
    }

    public InvoiceStatus(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
