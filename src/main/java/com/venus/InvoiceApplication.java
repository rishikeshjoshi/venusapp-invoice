package com.venus;

import com.venus.init.MasterDataInitialiser;
import com.venus.repository.InvoiceStatusRepository;
import com.venus.repository.PartyRepository;
import com.venus.service.InvoiceService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

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
		};
	}

	@Bean
	public ValidatorFactory validatorFactory() {
		return Validation.buildDefaultValidatorFactory();
	}
}



