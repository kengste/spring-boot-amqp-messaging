package com.kengste.rabbitmq;

import com.kengste.rabbitmq.config.ApplicationConfigReader;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitmqApplication {

	@Autowired
	private ApplicationConfigReader applicationConfig;

	public ApplicationConfigReader getApplicationConfig() {
		return applicationConfig;
	}

	public void setApplicationConfig(ApplicationConfigReader applicationConfig) {
		this.applicationConfig = applicationConfig;
	}

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqApplication.class, args);
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RabbitmqApplication.class);
	}

	/* This bean is to read the properties file configs */
	@Bean
	public ApplicationConfigReader applicationConfig() {
		return new ApplicationConfigReader();
	}

	/* Creating a bean for the Message queue Exchange */
	@Bean
	public TopicExchange getApp1Exchange() {
		return new TopicExchange(getApplicationConfig().getApp1Exchange());
	}

	/* Creating a bean for Message queue */
	@Bean
	public Queue getApp1Queue() {
		return new Queue(getApplicationConfig().getApp1Queue());
	}

	/* Binding between exchange and queue using routing key */
	@Bean
	public Binding declareBindingApp1() {
		return BindingBuilder.bind(getApp1Queue()).to(getApp1Exchange()).with(getApplicationConfig().getApp1RoutingKey());
	}


	/* Creating a bean for the Message queue Exchange */
	@Bean
	public TopicExchange getApp2Exchange() {
		return new TopicExchange(getApplicationConfig().getApp2Exchange());
	}

	/* Creating a bean for Message queue */
	@Bean
	public Queue getApp2Queue() {
		return new Queue(getApplicationConfig().getApp2Queue());
	}

	/* Binding between exchange and queue using routing key */
	@Bean
	public Binding declareBindingApp2() {
		return BindingBuilder.bind(getApp2Queue()).to(getApp2Exchange()).with(getApplicationConfig().getApp2RoutingKey());
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
