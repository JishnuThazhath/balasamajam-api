package com.balasamajam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class BalasamajamApplication {

	public static void main(String[] args) {
		SpringApplication.run(BalasamajamApplication.class, args);
	}

}
