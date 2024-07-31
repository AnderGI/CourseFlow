package com.tdd.api.infrastructure.rabbitmq.consumers;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.tdd.api.application.event.EventCommandBusSync;
import com.tdd.api.application.event.NotifyUserOnCourseCreatedEventCommand;
import com.tdd.api.application.event.NotifyUserOnCourseCreatedEventCommandHandler;
import com.tdd.api.domain.event.EventCommand;
import com.tdd.api.domain.event.EventCommandBus;
import com.tdd.api.domain.event.EventCommandHandler;
import com.tdd.api.domain.event.EventConsumer;

public class RabbitMQCourseCreatedConsumer implements EventConsumer{

	@Autowired
	private EventCommandHandler handler;
	@Autowired
	private ObjectMapper mapper;
	private ConnectionFactory connectionFactory;
	private Queue queue;
	private Queue consumerQueue;
	private EventCommandBus bus;
	public RabbitMQCourseCreatedConsumer
	(ConnectionFactory connectionFactory, Queue queue, EventCommandBus bus) {
		this.connectionFactory = connectionFactory;
		this.queue = queue;
		this.bus = bus;
	}

    @RabbitListener(queues = "#{consumerQueue.name}")
    public void consumeMessage(Message<String> message, Channel channel) throws Exception {
        String messageBody = message.getPayload();
        long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        try {
        	// Get the data from the message 
        	JsonNode node = mapper.readTree(messageBody);
        	JsonNode dataNode = node.get("event").get("data");
        	JsonNode attributesNode = dataNode.get("attributes");
        	String courseTitle = attributesNode.get("title").asText();
        	JsonNode courseDescription = attributesNode.get("description");
        	JsonNode courseLearning = attributesNode.get("learning");
        	JsonNode courseFeatures = attributesNode.get("features");
        	// Create Command
        	EventCommand command = new NotifyUserOnCourseCreatedEventCommand
        			(courseTitle, courseDescription, courseLearning, courseFeatures);
        	// Register command and handler in bus
        	bus.register(command.getClass(), handler);
        	// Call bus dispatch method
        	bus.dispatch(command);
        	// Acknowledge the message manually
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            // Handle any processing errors
            System.out.println("Failed to process message: " + messageBody);
            channel.basicNack(deliveryTag, false, true); // Requeue the message
        }
    }

	@Override
	public <E extends EventCommand> void handle(E domainEvent) {
		// TODO Auto-generated method stub
		
	}


}
