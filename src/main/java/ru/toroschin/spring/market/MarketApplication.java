package ru.toroschin.spring.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarketApplication {
	// Домашнее задание:
	// 1. Сделайте кнопку оформить заказ, по нажатию на которую в базу должен быть сохранен заказ
	// ( без подвязки к пользователю, ** с подвязкой к пользователю)
	// 2. * Фронт: если пользователь вошел в систему, то в верхней панели отпечатать его имя

	public static void main(String[] args) {
		SpringApplication.run(MarketApplication.class, args);
	}

}
