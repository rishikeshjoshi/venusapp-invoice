package com.venus.controller;

import com.venus.dto.AddItemForm;
import com.venus.dto.NewInvoice;
import com.venus.model.Invoice;
import com.venus.model.Item;
import com.venus.model.Party;
import com.venus.repository.InvoiceStatusRepository;
import com.venus.repository.PartyRepository;
import com.venus.service.InvoiceService;
import com.venus.service.exception.InvoiceServiceErrorCode;
import com.venus.service.exception.InvoiceServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * Created by hrishikeshjoshi on 6/25/16.
 */
@RestController(value = "/invoices")
public class InvoiceController {

    private InvoiceService invoiceService;

    private PartyRepository partyRepository;

    private InvoiceStatusRepository invoiceStatusRepository;

    @Autowired
    public InvoiceController(@NotNull InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @RequestMapping(value = {"/api/invoices/party/{partyId}"}, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Invoice>> getAllInvoicesForParty(@PathVariable Long partyId) {
        Party party = partyRepository.findOne(partyId);

        if(party == null) {
            return new ResponseEntity<List<Invoice>>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<Invoice>>(invoiceService.getAllInvoices(party),HttpStatus.OK);
    }

    @RequestMapping(value = {"/api/invoices"}, method = RequestMethod.GET, produces = "application/json")
    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @RequestMapping(value = "/invoices/save", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Invoice> saveInvoice(@RequestBody @NotNull Invoice invoice) {
        try {
            final Invoice resultInvoice = invoiceService.saveInvoice(invoice);
            return new ResponseEntity<Invoice>(resultInvoice,HttpStatus.OK);
        } catch (InvoiceServiceException e) {
            ResponseEntity<Invoice> re = new ResponseEntity<Invoice>(HttpStatus.BAD_REQUEST);
            final Set<InvoiceServiceErrorCode> errorCodes = e.getErrorCodes();
        }

        return null;
    }

    @RequestMapping(value = "/api/invoices/draft", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Invoice> draftInvoice(@NotNull @RequestBody NewInvoice newInvoice) {
        final Party party = new Party(newInvoice.getPartyName());
        partyRepository.save(party);

        Invoice invoice = new Invoice();
        invoice.setInvoiceDate(newInvoice.getInvoiceDate());
        invoice.setDueDate(newInvoice.getDueDate());
        invoice.setParty(party);
        invoice.setStatus(invoiceStatusRepository.findByValue("DRAFT"));

        try {
            final Invoice resultInvoice = invoiceService.saveInvoice(invoice);
            return new ResponseEntity<Invoice>(resultInvoice,HttpStatus.OK);
        } catch (InvoiceServiceException e) {
            ResponseEntity<Invoice> re = new ResponseEntity<Invoice>(HttpStatus.BAD_REQUEST);
            final Set<InvoiceServiceErrorCode> errorCodes = e.getErrorCodes();
            return re;
        }
    }

    @RequestMapping(value = {"/api/invoices/{invoiceId}"}, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Invoice> getInvoice(@PathVariable @NotNull Long invoiceId) {
        final Invoice invoice = invoiceService.getInvoiceById(invoiceId);

        if(invoice == null) {
            return new ResponseEntity<Invoice>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Invoice>(invoice, HttpStatus.OK);
    }

    @RequestMapping(value = {"/api/invoices/{invoiceId}/addItem"}, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Invoice> addItem(@PathVariable @NotNull Long invoiceId, @RequestBody AddItemForm addItemForm) {
        final Invoice invoice = invoiceService.getInvoiceById(invoiceId);

        if (invoice == null) {
            return new ResponseEntity<Invoice>(HttpStatus.NOT_FOUND);
        }

        invoice.setStatus(invoiceStatusRepository.findByValue("NEW"));

        Item item = new Item(invoice);
        item.setDescription(addItemForm.getDescription());
        item.setQuantity(addItemForm.getQuantity());
        item.setUnitPrice(addItemForm.getUnitPrice());
        invoice.addLineItem(item);

        try {
            final Invoice resultInvoice = invoiceService.saveInvoice(invoice);
            return new ResponseEntity<Invoice>(resultInvoice,HttpStatus.OK);
        } catch (InvoiceServiceException e) {
            ResponseEntity<Invoice> re = new ResponseEntity<Invoice>(HttpStatus.BAD_REQUEST);
            final Set<InvoiceServiceErrorCode> errorCodes = e.getErrorCodes();
            return re;
        }

    }

    @Autowired
    public void setInvoiceStatusRepository(InvoiceStatusRepository invoiceStatusRepository) {
        this.invoiceStatusRepository = invoiceStatusRepository;
    }

    @Autowired
    public void setPartyRepository(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }
}
