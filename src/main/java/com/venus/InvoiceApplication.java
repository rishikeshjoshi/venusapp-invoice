package com.venus;

import com.venus.init.MasterDataInitialiser;
import com.venus.model.Invoice;
import com.venus.model.InvoiceStatus;
import com.venus.model.Item;
import com.venus.model.Party;
import com.venus.repository.InvoiceStatusRepository;
import com.venus.repository.PartyRepository;
import com.venus.service.InvoiceService;
import com.venus.service.exception.InvoiceServiceException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Date;

@SpringBootApplication
public class InvoiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(MasterDataInitialiser masterDataInitialiser, PartyRepository partyRepository, InvoiceStatusRepository invoiceStatusRepository, InvoiceService invoiceService) {
		return envs -> {
			//Init all the master data after the spring context has been loaded.
			masterDataInitialiser.initialise();


			//Add Some Preconfigured Invoices For Testing Purpose
			Party party = new Party("John Smith", "Address");
			partyRepository.save(party);

			InvoiceStatus invoiceStatus = invoiceStatusRepository.findByValue("NEW");

			//Create a new invoice
			Invoice invoice = new Invoice();
			//Set Party
			invoice.setParty(party);

			//Set Invoice Status
			invoice.setStatus(invoiceStatus);

			//Set Invoice Date
			invoice.setInvoiceDate(new Date());

			Item item = new Item(invoice);
			item.setDescription("Test Description");
			item.setUnitPrice(new BigDecimal("100"));
			item.setQuantity(new BigDecimal("1"));

			try {
				invoice = invoiceService.saveInvoice(invoice);
			} catch (InvoiceServiceException e) {
				System.exit(-1);
			}

			//Create second invoice
			Party party2 = new Party("John Smith", "Address");
			partyRepository.save(party2);

			//Create a new invoice
			Invoice invoice2 = new Invoice();
			//Set Party
			invoice2.setParty(party2);

			//Set Invoice Status
			invoice2.setStatus(invoiceStatus);

			//Set Invoice Date
			invoice2.setInvoiceDate(new Date());

			Item item2 = new Item(invoice2);
			item2.setDescription("Test Description");
			item2.setUnitPrice(new BigDecimal("100"));
			item2.setQuantity(new BigDecimal("1"));

			Item item3 = new Item(invoice2);
			item3.setDescription("Test Description 2");
			item3.setUnitPrice(new BigDecimal("200"));
			item3.setQuantity(new BigDecimal("2"));

			try {
				invoice2 = invoiceService.saveInvoice(invoice2);
			} catch (InvoiceServiceException e) {
				System.exit(-1);
			}

		};
	}

	@Bean
	public ValidatorFactory validatorFactory() {
		return Validation.buildDefaultValidatorFactory();
	}
}



