package com.venus.service;

import com.venus.model.Invoice;
import com.venus.model.Party;
import com.venus.repository.InvoiceRepository;
import com.venus.service.exception.InvoiceServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by hrishikeshjoshi on 6/18/16.
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private InvoiceRepository invoiceRepository;

    private ValidatorFactory validatorFactory;

    @Override
    public Invoice saveInvoice(Invoice invoice) throws InvoiceServiceException {

        if(invoice == null) {
            final InvoiceServiceException exception = new InvoiceServiceException("Invoice parameter is null");
            exception.addNewErrorCode("ILLEGAL_PARAMETER","Illegal parameter passed to the service.");
            throw exception;
        }

        Validator validator = validatorFactory.getValidator();
        final Set<ConstraintViolation<Invoice>> constraintViolations = validator.validate(invoice);

        if(constraintViolations != null && !constraintViolations.isEmpty()) {

            final InvoiceServiceException exception = new InvoiceServiceException("Constraint Violations in Invoice parameter passed.");

            constraintViolations.stream().forEach(x -> {
                exception.addNewErrorCode("INVALID_PARAMETER."+x.getPropertyPath(),x.getMessage());
            });

            throw exception;
        }

        final Invoice savedInvoiceInstance = invoiceRepository.save(invoice);

        return savedInvoiceInstance;
    }

    @Override
    public Invoice getInvoiceByInvoiceNumber(String number) {
        return null;
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        return null;
    }

    @Override
    public List<Invoice> getAllInvoices() {
        List<Invoice> list = new ArrayList<>();
        invoiceRepository.findAll().forEach(x -> list.add(x));
        return list;
    }

    @Override
    public List<Invoice> getAllInvoices(Party party) {
        List<Invoice> list = new ArrayList<>();
        invoiceRepository.findAllByParty(party).forEach(x -> list.add(x));
        return list;
    }

    @Autowired
    public void setInvoiceRepository(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Autowired
    public void setValidatorFactory(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }
}
