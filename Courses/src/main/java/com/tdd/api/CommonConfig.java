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
import com.tdd.api.application.converters.events.json.CourseEventToJsonConverter;
import com.tdd.api.application.converters.response.ResponseConverter;
import com.tdd.api.application.converters.response.json.CourseJsonResponseConverter;
import com.tdd.api.application.find.CourseEventBusSync;
import com.tdd.api.application.find.CourseFinder;
import com.tdd.api.application.find.CourseToEventHandler;
import com.tdd.api.application.save.CourseSaver;
import com.tdd.api.domain.course.CourseRepository;
import com.tdd.api.domain.events.DomainEntityToEventHandler;
import com.tdd.api.domain.events.DomainEventPublisher;
import com.tdd.api.domain.events.course.EventBus;
import com.tdd.api.infrastructure.database.inmemory.InMemoryCourseRepository;
import com.tdd.api.infrastructure.publishers.rabbitmq.RabbitMqCourseEventPublisher;

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
	@Value("${app.rabbitmq.courses.queue}")
	private String rabbitCoursesQueue;
	@Value("${app.rabbitmq.courses.exchange.topic}")
	private String rabbitCoursesExchangeTopic;

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public CourseRepository courseRepository() {
		return new InMemoryCourseRepository();
	}

	@Bean
	public ResponseConverter responseConverter() {
		return new CourseJsonResponseConverter();
	}

	@Bean
	public CourseFinder courseFinder(CourseRepository courseRepository) {
		return new CourseFinder(courseRepository);
	}

	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(rabbitCoursesExchangeTopic);
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
	public CourseEventToJsonConverter eventToJsonConverter() {
		return new CourseEventToJsonConverter();
	}

	@Bean
	public DomainEventPublisher publisher(RabbitTemplate rabbitTemplate, Queue queue, EventBus bus,
			DomainEntityToEventHandler converter, TopicExchange topicExchange,
			CourseEventToJsonConverter eventToJsonConverter) {
		return new RabbitMqCourseEventPublisher(rabbitTemplate, queue, bus, converter, topicExchange,
				eventToJsonConverter);
	}

	@Bean
	public Queue exampleQueue() {
		return new Queue(rabbitCoursesQueue, true);
	}

	@Bean
	public EventBus eventBus() {
		return new CourseEventBusSync();
	}

	@Bean
	public DomainEntityToEventHandler domainEntityConverter() {
		return new CourseToEventHandler();
	}

	@Bean
	public CourseSaver courseSaver(CourseRepository courseRepository, DomainEventPublisher publisher) {
		return new CourseSaver(courseRepository, publisher);
	}
}
