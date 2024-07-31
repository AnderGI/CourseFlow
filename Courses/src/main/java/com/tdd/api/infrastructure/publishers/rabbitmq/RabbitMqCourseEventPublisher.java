package com.tdd.api.infrastructure.publishers.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.tdd.api.application.converters.events.json.CourseEventToJsonConverter;
import com.tdd.api.domain.DomainEntity;
import com.tdd.api.domain.events.DomainEntityToEventHandler;
import com.tdd.api.domain.events.DomainEventPublisher;
import com.tdd.api.domain.events.course.EventBus;
import com.tdd.api.domain.exception.CourseEvent;

public class RabbitMqCourseEventPublisher implements DomainEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;
    private final EventBus eventBus;
    private final DomainEntityToEventHandler entityToEventHandler;
    private final TopicExchange topicExchange;
    private final CourseEventToJsonConverter eventToJsonConverter;

    @Autowired
    public RabbitMqCourseEventPublisher(RabbitTemplate rabbitTemplate,
            Queue queue, EventBus bus,
            DomainEntityToEventHandler converter, TopicExchange topicExchange,
            CourseEventToJsonConverter eventToJsonConverter) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
        this.eventBus = bus;
        this.entityToEventHandler = converter;
        this.topicExchange = topicExchange;
        this.eventToJsonConverter = eventToJsonConverter;
    }

    @Override
    public void publish(DomainEntity entity) {
        // Register in a bus
        this.eventBus.register(entity.getClass(), entityToEventHandler);
        // bus will dispatch the event by calling its method and a converter will
        // convert the domain entity to the
        // event
        CourseEvent event = this.eventBus.dispatch(entity);
        rabbitTemplate.convertAndSend(
                topicExchange.getName(), event.getRoutingKey(),
                ((JsonNode) eventToJsonConverter.convert(event)).toString());

    }
}
