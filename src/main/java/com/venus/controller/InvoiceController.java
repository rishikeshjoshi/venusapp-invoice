package com.venus.controller;

import com.venus.model.Invoice;
import com.venus.model.Party;
import com.venus.repository.PartyRepository;
import com.venus.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by hrishikeshjoshi on 6/25/16.
 */
@RestController(value = "/invoices")
public class InvoiceController {

    private InvoiceService invoiceService;

    private PartyRepository partyRepository;

    @Autowired
    public InvoiceController(@NotNull InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @RequestMapping(value = {"/invoices/party/{partyId}"}, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Invoice>> getAllInvoicesForParty(@PathVariable Long partyId) {
        Party party = partyRepository.findOne(partyId);

        if(party == null) {
            return new ResponseEntity<List<Invoice>>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<Invoice>>(invoiceService.getAllInvoices(party),HttpStatus.OK);
    }

    @RequestMapping(value = {"/invoices"}, method = RequestMethod.GET, produces = "application/json")
    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @RequestMapping(value = {"/invoices/{invoiceId}"}, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Invoice> getInvoice(@PathVariable @NotNull Long invoiceId) {
        final Invoice invoice = invoiceService.getInvoiceById(invoiceId);

        if(invoice == null) {
            return new ResponseEntity<Invoice>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Invoice>(invoice, HttpStatus.OK);
    }


    @Autowired
    public void setPartyRepository(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }
}
