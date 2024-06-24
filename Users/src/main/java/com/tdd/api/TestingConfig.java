package com.tdd.api;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdd.api.application.convert.DomainEntityConverter;
import com.tdd.api.application.convert.json.EntityToJsonConverter;
import com.tdd.api.application.find.UserFinder;
import com.tdd.api.application.save.UserSaver;
import com.tdd.api.domain.user.UserRepository;
import com.tdd.api.infrastructure.database.inmemory.InMemoryUserRepository;
import com.tdd.api.infrastructure.database.inmemory.InMemoryUserTestingRepository;

@Configuration
@Profile("testing")
public class TestingConfig {

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange("domain_events_testing");
	}

	@Bean
	public UserRepository userRepository() {
		return new InMemoryUserTestingRepository();
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
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		return new RabbitTemplate(connectionFactory);
	}

	@Bean
	public Queue exampleQueue() {
		return new Queue("courses.course.notify_users_on_course_created.testing", true);
	}

}
