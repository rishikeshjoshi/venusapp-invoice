package com.venus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.venus.model.InvoiceStatus;
import com.venus.model.ItemType;
import com.venus.repository.InvoiceStatusRepository;
import com.venus.repository.ItemTypeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@SpringBootApplication
public class InvoiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(InvoiceStatusRepository invoiceStatusRepository, ItemTypeRepository itemTypeRepository, ApplicationProperties properties) {
		return envs -> {

			//Delete and initialize Invoice Status Master Data.
			invoiceStatusRepository.deleteAll();

			//Persist the invoice status types master data.
			Arrays.stream(properties.getInvoiceStatusList().split(",")).forEach(invoiceStatus -> {
				//Saving Invoice Status
				invoiceStatusRepository.save(new InvoiceStatus(invoiceStatus));
			});


			//Delete and initialize item type master data


		};
	}
}

@Component
@ConfigurationProperties(prefix = "venusapp.invoice")
class ApplicationProperties {

	@NotNull
	private String invoiceStatusList;

	@NotNull
	private String itemTypeList;

	//Getters and Setters
	public String getInvoiceStatusList() {
		return invoiceStatusList;
	}

	public void setInvoiceStatusList(String invoiceStatusList) {
		this.invoiceStatusList = invoiceStatusList;
	}

	public String getItemTypeList() {
		return itemTypeList;
	}

	public void setItemTypeList(String itemTypeList) {
		this.itemTypeList = itemTypeList;
	}
}

@Controller
@RequestMapping("/home")
class HomeController {

	@RequestMapping("/index")
	public String index() {
		return "index";
	}

}
