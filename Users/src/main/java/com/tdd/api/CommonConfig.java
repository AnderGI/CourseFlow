package com.tdd.api;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdd.api.application.convert.DomainEntityConverter;
import com.tdd.api.application.convert.DomainEventConverter;
import com.tdd.api.application.convert.json.EntityToJsonConverter;
import com.tdd.api.application.convert.json.EventToJsonConverter;
import com.tdd.api.application.event.EventCommandBusSync;
import com.tdd.api.application.event.NotifyUserOnCourseCreatedEventCommandHandler;
import com.tdd.api.application.find.UserFinder;
import com.tdd.api.application.save.UserSaver;
import com.tdd.api.domain.event.EventCommandBus;
import com.tdd.api.domain.event.EventCommandHandler;
import com.tdd.api.domain.event.EventConsumer;
import com.tdd.api.domain.event.EventPublisher;
import com.tdd.api.domain.user.UserRepository;
import com.tdd.api.infrastructure.database.inmemory.InMemoryUserRepository;
import com.tdd.api.infrastructure.rabbitmq.consumers.RabbitMQCourseCreatedConsumer;
import com.tdd.api.infrastructure.rabbitmq.publishers.RabbitMQFullCoursePublisher;

@Configuration
@Profile("dev")
public class CommonConfig {
	@Value("${spring.rabbitmq.host}")
	private String rabbitHost;
	@Value("${spring.rabbitmq.port}")
	private Integer rabbitPort;
	@Value("${spring.rabbitmq.username}")
	private String rabbitUser;
	@Value("${spring.rabbitmq.password}")
	private String rabbitPsswd;
	@Value("${app.rabbitmq.users.exchange.topic}")
	private String rabbitUsersExchangeTopic;
	@Value("${app.rabbitmq.users.queue.producer}")
	private String rabbitUsersProducerQueue;
	@Value("${app.rabbitmq.users.queue.consumer}")
	private String rabbitUsersConsumerQueue;
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	@Bean
	public DomainEventConverter eventToJsonConverter(ObjectMapper objectMapper) {
		return new EventToJsonConverter(objectMapper);
	}

	@Bean
	public DomainEntityConverter entityToJsonConverter(ObjectMapper objectMapper) {
		return new EntityToJsonConverter(objectMapper);
	}
	
	@Bean
	public UserRepository userRepository() {
		return new InMemoryUserRepository();
	}

	@Bean
	public EventCommandBus eventCommandBusSync() {
		return new EventCommandBusSync();
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory factory = new CachingConnectionFactory();
		factory.setHost(rabbitHost);
		factory.setPort(rabbitPort);
		factory.setUsername(rabbitUser);
		factory.setPassword(rabbitPsswd);
		return factory;
	}

	@Bean
	public Queue producerQueue() {
		return new Queue(rabbitUsersProducerQueue, true);
	}
	
	@Bean
	public Queue consumerQueue() {
		return new Queue(rabbitUsersConsumerQueue, true);
	}

	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(rabbitUsersExchangeTopic);
	}

	@Bean
	public EventConsumer eventConsumer(ConnectionFactory connectionFactory, Queue consumerQueue, EventCommandBus eventCommandBusSync) {
		return new RabbitMQCourseCreatedConsumer(connectionFactory, consumerQueue, eventCommandBusSync);
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
	public EventPublisher publisher
	(RabbitTemplate rabbitTemplate, Queue producerQueue, TopicExchange topicExchange, DomainEventConverter entityToJsonConverter) {
		return new RabbitMQFullCoursePublisher(rabbitTemplate, producerQueue, topicExchange, entityToJsonConverter);
	}
	
	@Bean 
	public EventCommandHandler handler(EventPublisher publisher)
	{
		return new NotifyUserOnCourseCreatedEventCommandHandler(publisher);
	}
	


}
