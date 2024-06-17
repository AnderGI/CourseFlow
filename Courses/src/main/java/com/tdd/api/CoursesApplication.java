package com.tdd.api;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

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

@SpringBootApplication
@EnableDiscoveryClient
public class CoursesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoursesApplication.class, args);
	}
}
