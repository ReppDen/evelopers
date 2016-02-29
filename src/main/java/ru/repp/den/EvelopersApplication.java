package ru.repp.den;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class EvelopersApplication {

	public static final int WORKERS_COUNT = 10;

	@Bean
	ExecutorService executorService() {
		return Executors.newFixedThreadPool(WORKERS_COUNT);
	}

	public static void main(String[] args) {
		SpringApplication.run(EvelopersApplication.class, args);
	}
}
