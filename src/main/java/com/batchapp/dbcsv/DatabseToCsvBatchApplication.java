package com.batchapp.dbcsv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class DatabseToCsvBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabseToCsvBatchApplication.class, args);
	}

}
