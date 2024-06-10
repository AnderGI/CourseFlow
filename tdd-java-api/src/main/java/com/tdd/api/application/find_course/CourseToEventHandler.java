package com.tdd.api.application.find_course;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Date;

import com.tdd.api.domain.Course;
import com.tdd.api.domain.events.CourseEvent;
import com.tdd.api.domain.events.DomainEntityHandler;
import com.tdd.api.domain.events.value_objects.EventAttributes;
import com.tdd.api.domain.events.value_objects.EventData;
import com.tdd.api.domain.events.value_objects.EventMeta;

public class CourseToEventHandler implements DomainEntityHandler<CourseEvent, Course>{

	@Override
	public CourseEvent handle(Course entity) {
		EventAttributes attributes = EventAttributes.create()
				.withCourseId(entity.getIdValue())
				.withCourseTitle( entity.getTitleValue());
		EventMeta meta = null;
		try {
			meta = EventMeta.create()
					.withHostMetaInfo(Inet4Address.getLocalHost().toString());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EventData data = EventData.create().withId()
				.withType("course.created")
				.ocurredOn(new Date())
				.withAttributes(attributes)
				.withMeta(meta);
		CourseEvent event = CourseEvent.create().withEventData(data);
		return event;
	}

}
