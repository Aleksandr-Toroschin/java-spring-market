package ru.toroschin.spring.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarketApplication {
	// Домашнее задание:
	// 1. Попробуйте самостоятельно повторить весь проект, можно подглядывать частями
	// (это не касается фронтенда angularJs, его делать/повторять не надо)
	// 2. * Добавьте @DeleteMapping и @PutMapping в ProductController
	// PUT - модифицирует ресурс
	// DELETE - удаляет ресурс/ресурсы

	public static void main(String[] args) {
		SpringApplication.run(MarketApplication.class, args);
	}

}
