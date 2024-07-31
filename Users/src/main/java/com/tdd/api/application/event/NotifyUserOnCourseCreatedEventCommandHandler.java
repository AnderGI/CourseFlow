package com.tdd.api.application.event;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.tdd.api.domain.event.CourseEvent;
import com.tdd.api.domain.event.DomainEvent;
import com.tdd.api.domain.event.EventCommand;
import com.tdd.api.domain.event.EventCommandHandler;
import com.tdd.api.domain.event.EventPublisher;
import com.tdd.api.domain.event.valueobjects.EventAttributes;
import com.tdd.api.domain.event.valueobjects.EventAttributesDescription;
import com.tdd.api.domain.event.valueobjects.EventAttributesFeatures;
import com.tdd.api.domain.event.valueobjects.EventAttributesLearning;
import com.tdd.api.domain.event.valueobjects.EventData;
import com.tdd.api.domain.event.valueobjects.EventMeta;

public final class NotifyUserOnCourseCreatedEventCommandHandler implements EventCommandHandler {

    private final EventPublisher publisher;

    @Autowired
    public NotifyUserOnCourseCreatedEventCommandHandler(EventPublisher publisher) {
        this.publisher = publisher;
    }
	
	@Override
	public <E extends EventCommand> void handle(E eventCommand) {
		// Create Value Objects
		EventMeta meta = null;
		try {
			meta = EventMeta.create()
					.withHostMetaInfo(Inet4Address.getLocalHost().toString());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

        NotifyUserOnCourseCreatedEventCommand command = (NotifyUserOnCourseCreatedEventCommand) eventCommand;

		EventAttributesDescription descr = EventAttributesDescription.create()
				.withEnglishDesc(command.getDescription().get("eng").get("value").asText())
				.withSpanishDesc(command.getDescription().get("esp").get("value").asText());
		
        EventAttributesLearning learnings = EventAttributesLearning.create()
				.withEnglishLearning(getListAsString(command.getCourseLearnings().get("eng").get("value")))
				.withSpanishLearning(getListAsString(command.getCourseLearnings().get("esp").get("value")));

		EventAttributesFeatures features = EventAttributesFeatures.create()
				.withEnglishFeatures(getListAsString(command.getCourseFeatures().get("eng").get("value")))
				.withSpanishFeatures(getListAsString(command.getCourseFeatures().get("esp").get("value")));

		EventAttributes attr = EventAttributes.create()
				.withCourseTitle(command.getTitle())
				.withCourseDescription(descr)
				.withCourseLearnings(learnings)
				.withCourseFeatures(features);

		EventData data = EventData.create()
						.ocurredOn(new Date()).withId()
						.withType("agi.users.1.event.full-course-created")
						.withAttributes(attr)
						.withMeta(meta);

		DomainEvent event = CourseEvent.create().withEventData(data);
		publisher.publish(event);
	}

    private String getListAsString(JsonNode node) {
        List<String> values = new ArrayList<>();
        node.elements().forEachRemaining(value -> values.add(value.asText()));
        return values.stream().collect(Collectors.joining(", "));
    }
}
