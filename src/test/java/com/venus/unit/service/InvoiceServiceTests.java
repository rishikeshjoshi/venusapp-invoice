package com.venus.unit.service;

import com.venus.model.Invoice;
import com.venus.model.InvoiceStatus;
import com.venus.model.Party;
import com.venus.repository.InvoiceRepository;
import com.venus.service.InvoiceService;
import com.venus.service.InvoiceServiceImpl;
import com.venus.service.exception.InvoiceServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Date;

/**
 * Created by hrishikeshjoshi on 6/16/16.
 */
public class InvoiceServiceTests {


    private Invoice invoice = new Invoice();

    @InjectMocks
    private final InvoiceService invoiceService = new InvoiceServiceImpl();

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private ValidatorFactory validatorFactory;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        Assert.assertNotNull("Invoice Repository is not null", invoiceRepository);
        Mockito.when(invoiceRepository.save(invoice)).thenReturn(invoice);

        Mockito.when(validatorFactory.getValidator()).thenReturn(Validation.buildDefaultValidatorFactory().getValidator());
    }

    /**
     * Check that an invoice is persisted
     */
    @Test(expected = InvoiceServiceException.class)
    public void exceptionIsThrownWhenInvoiceIsNull() throws InvoiceServiceException {
        invoiceService.saveInvoice(null);
    }

    @Test(expected = InvoiceServiceException.class)
    public void errorCode_ILLEGAL_PARAMETER_IsReturnedByInvoiceService() throws InvoiceServiceException {
        try {
            invoiceService.saveInvoice(null);
        } catch (InvoiceServiceException e) {
            //Verify that the correct error code is thrown by the service.
            final boolean errorCodeFound = isErrorCodeFound(e, "ILLEGAL_PARAMETER");

            Assert.assertTrue("Expected error code is not returned by Invoice Service.", errorCodeFound);

            throw e;
        }
    }

    @Test(expected = InvoiceServiceException.class)
    public void constraintViolationsAreThrownWhenInvoiceParameterIsNotValid() throws InvoiceServiceException {
        try {
            invoiceService.saveInvoice(invoice);
        } catch (InvoiceServiceException e) {
            //Verify that the correct error code is thrown by the service.
            final String invoiceDateErrorCode = "INVALID_PARAMETER." + "invoiceDate";
            final String personErrorCode = "INVALID_PARAMETER." + "party";
            final String statusErrorCode = "INVALID_PARAMETER." + "party";

            Assert.assertTrue("Expected error code is not returned by Invoice Service.", isErrorCodeFound(e, invoiceDateErrorCode));
            Assert.assertTrue("Expected error code is not returned by Invoice Service.", isErrorCodeFound(e, personErrorCode));
            Assert.assertTrue("Expected error code is not returned by Invoice Service.", isErrorCodeFound(e, statusErrorCode));
            throw  e;
        }
    }

    @Test
    public void noExceptionIsThrownWhenCorrectInvoiceParameterIsPassed() {
        invoice.setParty(new Party("ABC","PQR"));
        invoice.setInvoiceDate(new Date());
        invoice.setStatus(new InvoiceStatus("NEW","New"));

        try {
            invoiceService.saveInvoice(invoice);
        } catch (InvoiceServiceException e) {
            Assert.fail("Exception is thrown from the invoice service");
        }
    }

    private boolean isErrorCodeFound(InvoiceServiceException e, String errorCode) {
        return e.getErrorCodes().stream().anyMatch(x -> {
            return x.getErrorCode().equals(errorCode);
        });
    }

}
