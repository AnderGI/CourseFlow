package com.tdd.api.application.get;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.tdd.api.application.find_course.CourseFinder;
import com.tdd.api.application.save_course.CourseSaver;
import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseIdMother;
import com.tdd.api.domain.CourseMother;
import com.tdd.api.domain.CourseNotExistError;
import com.tdd.api.domain.CourseRepository;
import com.tdd.api.domain.InvalidArgumentException;
import com.tdd.api.infrastructure.InMemoryCourseRepository;

// Randomize JUNIT tests
// Each test suite will be random among different JVM
// But the randomization will be the same within each JVM once executed
@TestMethodOrder(MethodOrderer.Random.class)
public class GetCourseUnitTester {
	// All tests should be atomic and independent 
	@Test
	void it_should_get_an_existing_course() 
			throws InvalidArgumentException, // wont cause arguments in VO are Ok
			CourseNotExistError // wont cause course has been saved
	{
		// Given
		// Since it is in memory no need for a mock
		// The in memory repo should be moved into testing context when other repos are implemented
		// and it should be the new repo either mocked or we shoudl use the in memory repo
		CourseRepository inMemoryRepo = this.givenAnInMemoryRepository();
		CourseFinder courseFinder = this.givenACouseFinder(inMemoryRepo);
		CourseSaver courseSaver = this.givenACourseSaver(inMemoryRepo);
		Course course = this.givenACourse();
		courseSaver.saveCourse(course);
		System.out.println(inMemoryRepo.getAll());
		// When method is invoked
		Course retreieved = courseFinder.findCourse(CourseIdMother.fromValue(course.getIdValue()));
		// Then
		assertEquals(retreieved, course);
	}
/*
	@Test
	void it_should_not_get_an_inexistent_course() 
			throws InvalidArgumentException // wont
	{
		// Given
		CourseRepository inMemoryMock = this.givenAnInMemoryMock();
		CourseFinder courseFinder = this.givenACouseFinder(inMemoryMock);
		Course toSearchCourse = this.givenACourse();
		String toSearchCourseIdValue = toSearchCourse.getIdValue();
		
		// When method is invoked
		when(inMemoryMock.searchCourse(null)).thenReturn(Optional.empty());
		
		// Then	
		assertThrows(CourseNotExistError.class, 
				() -> courseFinder.findCourse(CourseIdMother.fromValue(toSearchCourseIdValue)));
	}

	*/
	private CourseRepository givenAnInMemoryRepository() {
		return new InMemoryCourseRepository();
	}
	
	private CourseFinder givenACouseFinder(CourseRepository repo) {
		return new CourseFinder(repo);
	}
	
	private Course givenACourse() throws InvalidArgumentException {
		return CourseMother.create();
	}
	
	private CourseSaver givenACourseSaver(CourseRepository repo) {
		return new CourseSaver(repo);
	}

}
