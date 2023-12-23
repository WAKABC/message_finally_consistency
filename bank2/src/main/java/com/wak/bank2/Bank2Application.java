package com.wak.bank2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.wak.bank2.mapper"})
public class Bank2Application {

	public static void main(String[] args) {
		SpringApplication.run(Bank2Application.class, args);
	}

}
