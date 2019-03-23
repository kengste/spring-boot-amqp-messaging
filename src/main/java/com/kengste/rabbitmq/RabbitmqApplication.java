package com.kengste.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

	/* Creating a bean for the Message queue Exchange */
	@Bean
	public TopicExchange getApp1Exchange() {
		return new TopicExchange(APP_EXCHANGE_1);
	}

	/* Creating a bean for Message queue */
	@Bean
	public Queue getApp1Queue() {
		return new Queue(APP_QUEUE_1);
	}

	/* Creating a bean for the Message queue Exchange */
	@Bean
	public TopicExchange getApp2Exchange() {
		return new TopicExchange(APP_EXCHANGE_2);
	}

	/* Creating a bean for Message queue */
	@Bean
	public Queue getApp2Queue() {
		return new Queue(APP_QUEUE_2);
	}

    /* Bindings between exchange and queue using routing key */
    @Bean
    public Binding declareBinding1() {
        return BindingBuilder.bind(getApp1Queue()).to(getApp1Exchange()).with(APP_ROUTING_KEY_1);
    }

	@Bean
	public Binding declareBinding2() {
		return BindingBuilder.bind(getApp2Queue()).to(getApp1Exchange()).with(APP_ROUTING_KEY_1);
	}

	/*
	You’ll use RabbitTemplate to send messages,
	and you will register a Receiver with the message listener container to receive messages.
	The connection factory drives both, allowing them to connect to the RabbitMQ server.
	*/

	/* Bean for rabbitTemplate */
	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
		return rabbitTemplate;
	}

	/* The bean Jackson2JsonMessageConverter takes care of deserializing the JSON messages
	to Java classes, using a default ObjectMapper */
	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

}
