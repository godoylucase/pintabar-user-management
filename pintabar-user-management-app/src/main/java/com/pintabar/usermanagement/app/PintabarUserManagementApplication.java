package com.pintabar.usermanagement.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.pintabar"})
public class PintabarUserManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(PintabarUserManagementApplication.class, args);
	}
}
