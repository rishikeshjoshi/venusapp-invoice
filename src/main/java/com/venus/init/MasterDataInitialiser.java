package com.venus.init;

import com.venus.model.InvoiceStatus;
import com.venus.model.ItemType;
import com.venus.repository.InvoiceStatusRepository;
import com.venus.repository.ItemTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Initialize all the master data
 *
 * Created by hrishikeshjoshi on 6/16/16.
 */
@Component
public class MasterDataInitialiser implements DataInitializer {

    private InvoiceStatusRepository invoiceStatusRepository;

    private ItemTypeRepository itemTypeRepository;

    private ApplicationProperties properties;

    @Override
    public void initialise() {
        //Delete and initialize Invoice Status Master Data.
        invoiceStatusRepository.deleteAll();

        Arrays.stream(properties.getInvoiceStatusList().split(",")).forEach(invoiceStatus -> {
            invoiceStatusRepository.save(new InvoiceStatus(invoiceStatus));
        });


        //Delete all and persist item types into Master Data.
        itemTypeRepository.deleteAll();

        Arrays.stream(properties.getItemTypeList().split(",")).forEach(it -> {
            itemTypeRepository.save(new ItemType(it));
        });
    }

    @Autowired
    public void setInvoiceStatusRepository(InvoiceStatusRepository invoiceStatusRepository) {
        this.invoiceStatusRepository = invoiceStatusRepository;
    }

    @Autowired
    public void setItemTypeRepository(ItemTypeRepository itemTypeRepository) {
        this.itemTypeRepository = itemTypeRepository;
    }

    @Autowired
    public void setProperties(ApplicationProperties properties) {
        this.properties = properties;
    }
}
