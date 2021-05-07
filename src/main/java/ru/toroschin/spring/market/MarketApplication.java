package ru.toroschin.spring.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarketApplication {
	// Домашнее задание:
	// 1. Добавить CartDto при отправке данных о корзине js клиенту

	public static void main(String[] args) {
		SpringApplication.run(MarketApplication.class, args);
	}

}
