package com.tdd.api.infrastructure.consumers.course.rabbitmq;

import com.tdd.api.domain.events.EventConsumer;

public class RabbitMQCourseCreatedConsumer implements EventConsumer {

	@Override
	public void consume(String event) {
		// TODO Auto-generated method stub
		System.out.println(event);

	}

}
