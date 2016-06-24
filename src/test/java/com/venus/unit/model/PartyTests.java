package com.venus.unit.model;

import com.venus.model.Party;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by hrishikeshjoshi on 6/17/16.
 */
public class PartyTests {

    private Party party;

    @Before
    public void init() {
        party = new Party("Name","Address");
    }

    @Test
    public void testPartyNameAndAddressMatches() {
        final String name = "Name1";
        final String address = "Address1";
        party = new Party(name,address);

        Assert.assertEquals("Party Name does not match to the set value",name, party.getName() );

        Assert.assertEquals("Party Address does not match to the set value",address, party.getAddress() );
    }

    @Test
    public void testInvoicesIsNotNullForParty() {
        Assert.assertNotNull("Invoices are not initialized for the party",party.getInvoices());
    }
}
