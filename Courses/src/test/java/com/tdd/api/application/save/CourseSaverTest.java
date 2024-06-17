package com.tdd.api.application.save;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;

import com.tdd.api.domain.CourseMother;
import com.tdd.api.domain.course.Course;
import com.tdd.api.domain.course.CourseRepository;
import com.tdd.api.domain.events.DomainEvent;
import com.tdd.api.domain.events.DomainEventPublisher;
import com.tdd.api.domain.exception.InvalidArgumentException;
import com.tdd.api.infrastructure.database.inmemory.InMemoryCourseRepository;

@TestMethodOrder(MethodOrderer.Random.class)
public class CourseSaverTest {

	@Test
	void it_should_add_a_course() 
			throws InvalidArgumentException // wont throw it
	{
		// 	Test only if saver and repo dont "break"
		CourseRepository inMemoryMock = this.givenAnInMemoryCourseRepository();
		DomainEventPublisher mockPublisher = this.givenAnDomainEventPublisher();
		DomainEvent mockEvent = this.givenADomainEvent();
		CourseSaver courseSaver = this.givenACourseSaver(inMemoryMock, mockPublisher);
		Course toAddValidCourse = CourseMother.create();
		courseSaver.saveCourse(toAddValidCourse);
		
		// Verify calls
		Mockito.verify(inMemoryMock).saveCourse(toAddValidCourse);
		Mockito.verify(mockPublisher).publish(toAddValidCourse);
		
	}

	private CourseRepository givenAnInMemoryCourseRepository() {
		return Mockito.mock(InMemoryCourseRepository.class);
	}
	
	private CourseSaver givenACourseSaver(CourseRepository repo, DomainEventPublisher publisher) {
		return new CourseSaver(repo, publisher);
	}
	
	private DomainEventPublisher givenAnDomainEventPublisher() {
		return Mockito.mock(DomainEventPublisher.class);
	}
	
	private DomainEvent givenADomainEvent() {
		return Mockito.mock(DomainEvent.class);
	}

}
