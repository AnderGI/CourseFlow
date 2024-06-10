package com.tdd.api;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdd.api.application.bus.CourseCommandBusSync;
import com.tdd.api.application.bus.CourseEventBusSync;
import com.tdd.api.application.find_course.CourseToEventHandler;
import com.tdd.api.application.save_course.CourseSaver;
import com.tdd.api.domain.CourseRepository;
import com.tdd.api.domain.events.DomainEntityHandler;
import com.tdd.api.domain.events.DomainEventPublisher;
import com.tdd.api.domain.events.EventBus;
import com.tdd.api.infrastructure.bbdd.inmemory.InMemoryCourseRepository;
import com.tdd.api.infrastructure.events.rabbitmq.RabbitMqCourseEventPublisher;

@SpringBootApplication
public class TddJavaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TddJavaApiApplication.class, args);
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
    public DomainEventPublisher publisher(RabbitTemplate rabbitTemplate, Queue queue, EventBus bus, DomainEntityHandler converter) {
        return new RabbitMqCourseEventPublisher(rabbitTemplate, queue, bus, converter);
    }
    
    @Bean
    public CourseRepository courseRepository() {
        return new InMemoryCourseRepository();
    }

    @Bean
    public CourseSaver courseSaver(CourseRepository courseRepository, DomainEventPublisher publisher) {
        return new CourseSaver(courseRepository, publisher);
    }

    @Bean
    public CourseCommandBusSync courseCommandBusSync() {
        return new CourseCommandBusSync();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
    
    @Bean
    public EventBus eventBus() {
    	return new CourseEventBusSync();
    }
    
    @Bean
    public DomainEntityHandler domainEntityConverter() {
    	return new CourseToEventHandler();
    }
}
