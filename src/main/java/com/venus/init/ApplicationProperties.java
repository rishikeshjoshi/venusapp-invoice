package com.venus.init;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix = "venusapp.invoice")
public class ApplicationProperties {

    @NotNull
    private String invoiceStatusList;

    @NotNull
    private String itemTypeList;

    //Getters and Setters
    public String getInvoiceStatusList() {
        return invoiceStatusList;
    }

    public void setInvoiceStatusList(String invoiceStatusList) {
        this.invoiceStatusList = invoiceStatusList;
    }

    public String getItemTypeList() {
        return itemTypeList;
    }

    public void setItemTypeList(String itemTypeList) {
        this.itemTypeList = itemTypeList;
    }
}