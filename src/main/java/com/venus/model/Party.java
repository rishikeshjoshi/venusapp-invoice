package com.venus.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

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
}
