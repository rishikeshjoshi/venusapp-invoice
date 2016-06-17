package com.venus.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hrishikeshjoshi on 6/14/16.
 */
@Entity
public class Party implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String address;

    @OneToMany(mappedBy = "party")
    private Set<Invoice> invoices = new HashSet<Invoice>();

    //default constructor
    Party(){

    }

    public Party(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }
}
