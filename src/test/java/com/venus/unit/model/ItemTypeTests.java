package com.venus.unit.model;

import com.venus.model.ItemType;
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

    }

    @Test
    public void testItemTypeMatches() {
        final String itemTypeVal = "ITEM TYPE 1";
        itemType = new ItemType(itemTypeVal);

        Assert.assertEquals("Item Type does not match",itemTypeVal,itemType.getItemType());
    }
}
