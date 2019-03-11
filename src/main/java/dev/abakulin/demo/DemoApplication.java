package dev.abakulin.demo;

import dev.abakulin.demo.model.Customer;
import dev.abakulin.demo.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	private static final Logger LOG = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository repo) {
		return (args) -> {
			// saving customers
			LOG.info("Saving new users...");
			repo.save(new Customer("Alexey", "Bakulin"));
			repo.save(new Customer("Robin", "Hood"));
			repo.save(new Customer("Bart", "Simpson"));

			LOG.info("Getting all users...");
			repo.findAll().forEach((c) -> LOG.info(c.toString()));
		};
	}
}
