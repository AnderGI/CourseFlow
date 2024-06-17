package com.tdd.api.infrastructure.publishers.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.tdd.api.application.converters.events.json.CourseEventToJsonConverter;
import com.tdd.api.domain.DomainEntity;
import com.tdd.api.domain.events.DomainEntityHandler;
import com.tdd.api.domain.events.DomainEventPublisher;
import com.tdd.api.domain.events.course.EventBus;
import com.tdd.api.domain.exception.CourseEvent;

public class RabbitMqCourseEventPublisher implements DomainEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;
    private final EventBus bus;
    private final DomainEntityHandler converter;
    @Autowired
    public RabbitMqCourseEventPublisher(RabbitTemplate rabbitTemplate, Queue queue, EventBus bus, DomainEntityHandler converter) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
        this.bus = bus;
        this.converter = converter;
    }

    @Override
    public void publish(DomainEntity entity) {
    	// Register in a bus
        this.bus.register(entity.getClass(), converter);
        // bus will dispatch the event by calling its method and a converter will convert the domain entity to the
        // event
        CourseEvent event = this.bus.dispatch(entity);
        CourseEventToJsonConverter converter = new CourseEventToJsonConverter();       
        rabbitTemplate.convertAndSend(queue.getName(), ((JsonNode) converter.convert(event)).toString());
        
    }
}
