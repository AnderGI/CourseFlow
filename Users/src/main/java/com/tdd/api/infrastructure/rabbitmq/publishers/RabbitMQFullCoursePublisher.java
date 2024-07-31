package com.tdd.api.infrastructure.rabbitmq.publishers;

import java.lang.reflect.Field;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tdd.api.application.convert.DomainEntityConverter;
import com.tdd.api.application.convert.DomainEventConverter;
import com.tdd.api.domain.event.CourseEvent;
import com.tdd.api.domain.event.DomainEvent;
import com.tdd.api.domain.event.EventPublisher;
import com.tdd.api.domain.user.DomainEntity;

public class RabbitMQFullCoursePublisher implements EventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;
    private final TopicExchange topicExchange;
    @Autowired
    private ObjectMapper mapper;
    private DomainEventConverter converter;
    
    @Autowired
    public RabbitMQFullCoursePublisher(RabbitTemplate rabbitTemplate, 
    		Queue queue, TopicExchange topicExchange, DomainEventConverter converter) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
        this.topicExchange = topicExchange;
        this.converter = converter;
    }
    
	@Override
	public void publish(DomainEvent entity) {
		// TODO Auto-generated method stub
		JsonNode node = (JsonNode) converter.convertSingle(entity);
		rabbitTemplate.convertAndSend(
        		topicExchange.getName(), ((CourseEvent) entity).getRoutingKey(),
        		(node).toString()); // en el to string da el error
	}
	

	


}
