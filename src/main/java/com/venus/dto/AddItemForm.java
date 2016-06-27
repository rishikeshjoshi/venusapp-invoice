package com.venus.dto;

import java.math.BigDecimal;

/**
 * Created by hrishikeshjoshi on 6/27/16.
 */
public class AddItemForm {

    private String description;

    private BigDecimal quantity;

    private BigDecimal unitPrice;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
