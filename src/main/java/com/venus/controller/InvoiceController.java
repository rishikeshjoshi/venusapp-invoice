package com.venus.controller;

import com.venus.model.Invoice;
import com.venus.model.Party;
import com.venus.repository.PartyRepository;
import com.venus.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Invoice> getAllInvoicesForParty(@PathVariable Long partyId) {
        if(partyId == null) {
            return invoiceService.getAllInvoices();
        } else {
            Party party = partyRepository.findOne(partyId);

            if(party == null) {
                //TODO : Return an error code.
            }

            return invoiceService.getAllInvoices(party);
        }
    }

    @RequestMapping(value = {"/invoices"}, method = RequestMethod.GET, produces = "application/json")
    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @Autowired
    public void setPartyRepository(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }
}
