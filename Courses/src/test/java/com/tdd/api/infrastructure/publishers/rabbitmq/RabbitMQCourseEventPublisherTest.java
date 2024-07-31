package com.tdd.api.infrastructure.publishers.rabbitmq;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.JsonNode;
import com.tdd.api.TestingConfig;
import com.tdd.api.application.converters.events.json.CourseEventToJsonConverter;
import com.tdd.api.domain.DomainEntity;
import com.tdd.api.domain.events.DomainEntityToEventHandler;
import com.tdd.api.domain.events.course.EventBus;
import com.tdd.api.domain.exception.CourseEvent;

@SpringBootTest
@ActiveProfiles("testing")
@Import(TestingConfig.class)
public class RabbitMQCourseEventPublisherTest {

    @Test
    public void testPublish() {
        /*
         * RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
         * Queue queue = mock(Queue.class);
         * EventBus eventBus = mock(EventBus.class);
         * DomainEntityToEventHandler converter =
         * mock(DomainEntityToEventHandler.class);
         * TopicExchange topicExchange = mock(TopicExchange.class);
         * CourseEventToJsonConverter eventToJsonConverter =
         * mock(CourseEventToJsonConverter.class);
         * 
         * RabbitMqCourseEventPublisher publisher = new RabbitMqCourseEventPublisher(
         * rabbitTemplate, queue, eventBus, converter, topicExchange,
         * eventToJsonConverter);
         * 
         * DomainEntity entity = mock(DomainEntity.class);
         * CourseEvent event = mock(CourseEvent.class);
         * JsonNode eventJson = mock(JsonNode.class);
         * 
         * when(converter.handle(entity)).thenReturn(event);
         * when(eventToJsonConverter.convert(event)).thenReturn(eventJson);
         * when(event.getRoutingKey()).thenReturn("routingKey");
         * when(eventJson.toString()).thenReturn("eventJson");
         * 
         * publisher.publish(entity);
         * 
         * verify(rabbitTemplate).convertAndSend(topicExchange.getName(), "routingKey",
         * "eventJson");
         */
    }
}