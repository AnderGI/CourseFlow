package com.tdd.api;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdd.api.application.convert.DomainEntityConverter;
import com.tdd.api.application.convert.json.EntityToJsonConverter;
import com.tdd.api.application.find.UserFinder;
import com.tdd.api.application.save.UserSaver;
import com.tdd.api.domain.event.command.EventConsumer;
import com.tdd.api.domain.user.UserRepository;
import com.tdd.api.infrastructure.consumers.course.rabbitmq.RabbitMQCourseCreatedConsumer;
import com.tdd.api.infrastructure.database.inmemory.InMemoryUserRepository;

@Configuration
@Profile("dev")
public class CommonConfig {

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public UserRepository userRepository() {
		return new InMemoryUserRepository();
	}


	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory factory = new CachingConnectionFactory();
		factory.setHost("localhost");
		factory.setPort(5672);
		factory.setUsername("admin");
		factory.setPassword("password");
		return factory;
	}

	@Bean
	public Queue exampleQueue() {
		return new Queue("courses.course.notify_users_on_course_created", true);
	}

	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange("domain_events");
	}

	@Bean
	public EventConsumer eventConsumer(ConnectionFactory connectionFactory, Queue exampleQueue) {
		return new RabbitMQCourseCreatedConsumer(connectionFactory, exampleQueue);
	}

	@Bean
	public DomainEntityConverter domainEntityConverter(ObjectMapper objectMapper) {
		return new EntityToJsonConverter(objectMapper);
	}

	@Bean
	public UserSaver userSaver(UserRepository userRepository) {
		return new UserSaver(userRepository);
	}

	@Bean
	public UserFinder userFinder(UserRepository userRepository) {
		return new UserFinder(userRepository);
	}

}
