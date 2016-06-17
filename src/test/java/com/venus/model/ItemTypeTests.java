package com.venus.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by hrishikeshjoshi on 6/17/16.
 */
public class ItemTypeTests {

    private ItemType itemType;

    @Before
    public void init() {
        itemType = new ItemType();
    }

    @Test
    public void testItemTypeMatches() {
        final String itemTypeVal = "ITEM TYPE 1";
        itemType.setItemType(itemTypeVal);

        Assert.assertEquals("Item Type does not match",itemTypeVal,itemType.getItemType());
    }
}
