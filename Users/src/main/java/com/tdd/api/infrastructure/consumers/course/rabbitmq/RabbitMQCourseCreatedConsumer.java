package com.tdd.api.infrastructure.consumers.course.rabbitmq;

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
import com.tdd.api.application.event.course_created.NotifyUserOnCourseCreatedEventCommand;
import com.tdd.api.application.event.course_created.NotifyUserOnCourseCreatedEventCommandHandler;
import com.tdd.api.domain.event.command.EventCommand;
import com.tdd.api.domain.event.command.EventCommandBus;
import com.tdd.api.domain.event.command.EventConsumer;

public class RabbitMQCourseCreatedConsumer implements EventConsumer{

	@Autowired
	private ObjectMapper mapper;
	private ConnectionFactory connectionFactory;
	private Queue queue;
	public RabbitMQCourseCreatedConsumer
	(ConnectionFactory connectionFactory, Queue queue) {
		this.connectionFactory = connectionFactory;
		this.queue = queue;
	}

    @RabbitListener(queues = "courses.course.notify_users_on_course_created")
    public void consumeMessage(Message<String> message, Channel channel) throws Exception {
        String messageBody = message.getPayload();
        long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);

        try {
        	// Get the data from the message 
        	JsonNode node = mapper.readTree(messageBody);
        	String date = node.get("data").get("ocurred_on").asText();
        	String courseId = node.get("data").get("attributes").get("id").asText();
        	String courseTitle = node.get("data").get("attributes").get("title").asText();
        	// Create Command
        	NotifyUserOnCourseCreatedEventCommand command = new NotifyUserOnCourseCreatedEventCommand
        			(date, courseTitle, courseId);
        	// Create Handler
        	NotifyUserOnCourseCreatedEventCommandHandler handler = new NotifyUserOnCourseCreatedEventCommandHandler();
        	// Create Bus
        	EventCommandBus bus = new EventCommandBusSync();
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
