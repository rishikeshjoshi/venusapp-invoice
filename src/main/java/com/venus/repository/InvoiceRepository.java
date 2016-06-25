package com.venus.repository;

import com.venus.model.Invoice;
import com.venus.model.Party;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hrishikeshjoshi on 6/18/16.
 */
@Repository
public interface InvoiceRepository extends CrudRepository<Invoice,Long> {

    /**
     * Find invoices for a given party.
     * @param party
     * @return
     */
    List<Invoice> findAllByParty(Party party);

    /**
     * Find invoice by invoice number.
     * @param invoiceNumber
     * @return
     */
    List<Invoice> findByInvoiceNumber(String invoiceNumber);

}
