package com.kengste.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqApplication {

    public static final String APP_EXCHANGE_1 = "app1-exchange";
    public static final String APP_QUEUE_1 = "app1-queue";
    public static final String APP_ROUTING_KEY_1 = "app-routing-key-1";
    public static final String APP_EXCHANGE_2 = "app2-exchange";
    public static final String APP_QUEUE_2 = "app2-queue";
    public static final String APP_ROUTING_KEY_2 = "app-routing-key-2";

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqApplication.class, args);
	}



}
