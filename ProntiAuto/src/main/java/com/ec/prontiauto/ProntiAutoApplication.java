package com.ec.prontiauto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ec.prontiauto.utils.LoadData;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@SecurityScheme(name = "com.ec.prontiauto", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class ProntiAutoApplication implements CommandLineRunner {

	@Autowired
	LoadData loadData;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ProntiAutoApplication.class);
		app.run(args);
	}

	@Override
	public void run(String... args) {
		try {
			loadData.loadData();
			System.out.println("Data loaded");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
