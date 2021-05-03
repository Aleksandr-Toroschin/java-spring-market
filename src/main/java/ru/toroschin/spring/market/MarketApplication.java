package ru.toroschin.spring.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarketApplication {
	// Домашнее задание:
	// 1. Добавить возможность добавлять товары в корзину (нажимая на кнопку Добавить в корзину)
	// 2. На странице под формой добавления нового товара необходимо
	// добавить таблицу с товарами, которые лежат в корзине
	// 3. Под таблицей с корзиной сделать кнопку "Очистить корзину"

	public static void main(String[] args) {
		SpringApplication.run(MarketApplication.class, args);
	}

}
