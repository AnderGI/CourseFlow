package com.tdd.api;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdd.api.application.converters.response.ResponseConverter;
import com.tdd.api.application.converters.response.json.CourseJsonResponseConverter;
import com.tdd.api.application.find.CourseEventBusSync;
import com.tdd.api.application.find.CourseFinder;
import com.tdd.api.application.find.CourseToEventHandler;
import com.tdd.api.application.save.CourseSaver;
import com.tdd.api.domain.course.CourseRepository;
import com.tdd.api.domain.events.DomainEntityHandler;
import com.tdd.api.domain.events.DomainEventPublisher;
import com.tdd.api.domain.events.course.EventBus;
import com.tdd.api.infrastructure.database.inmemory.InMemoryCourseRepository;
import com.tdd.api.infrastructure.publishers.rabbitmq.RabbitMqCourseEventPublisher;

@Configuration
public class CommonConfig {
	
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
        return new Queue("created.courses", true);
    }
    
    @Bean
    public EventBus eventBus() {
    	return new CourseEventBusSync();
    }
    
    @Bean
    public DomainEntityHandler domainEntityConverter() {
    	return new CourseToEventHandler();
    }
    @Bean
    public DomainEventPublisher publisher(RabbitTemplate rabbitTemplate, Queue queue, EventBus bus, DomainEntityHandler converter) {
        return new RabbitMqCourseEventPublisher(rabbitTemplate, queue, bus, converter);
    }
	
	@Bean
	public CourseSaver courseSaver(CourseRepository courseRepository, DomainEventPublisher publisher) {
		return new CourseSaver(courseRepository, publisher);
	}
}
