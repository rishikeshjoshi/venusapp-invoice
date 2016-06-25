package com.venus.service;

import com.venus.model.Invoice;
import com.venus.model.Party;
import com.venus.service.exception.InvoiceServiceException;

import java.util.List;

/**
 * Invoice Service
 *
 * Created by hrishikeshjoshi on 6/16/16.
 */
public interface InvoiceService {

    /**
     *
     * Create a new invoice if the invoice is not present, otherwise update the existing invoice.
     * @param invoice
     * @return
     * @throws InvoiceServiceException
     */
    Invoice saveInvoice(Invoice invoice) throws InvoiceServiceException;

    /**
     * Get Invoice by Invoice Number
     * @param number
     * @return
     */
    Invoice getInvoiceByInvoiceNumber(String number);

    /**
     * Get Invoice By Id.
     * @param id
     * @return
     */
    Invoice getInvoiceById(Long id);

    /**
     * Get all invoices
     * @return
     */
    List<Invoice> getAllInvoices();


    /**
     * Get all invoices for party
     * @param party
     * @return
     */
    List<Invoice> getAllInvoices(Party party);



}
