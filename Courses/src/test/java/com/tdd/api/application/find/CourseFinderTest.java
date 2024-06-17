package com.tdd.api.application.find;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;

import com.tdd.api.application.save.CourseSaver;
import com.tdd.api.domain.CourseIdMother;
import com.tdd.api.domain.CourseMother;
import com.tdd.api.domain.course.Course;
import com.tdd.api.domain.course.CourseRepository;
import com.tdd.api.domain.events.DomainEventPublisher;
import com.tdd.api.domain.exception.CourseNotExistException;
import com.tdd.api.domain.exception.InvalidArgumentException;
import com.tdd.api.infrastructure.database.inmemory.InMemoryCourseRepository;

//Randomize JUNIT tests
//Each test suite will be random among different JVM
//But the randomization will be the same within each JVM once executed
@TestMethodOrder(MethodOrderer.Random.class)
public class CourseFinderTest {
	@Test
	void it_should_get_an_existing_course() 
			throws InvalidArgumentException, // wont cause arguments in VO are Ok
			CourseNotExistException // wont cause course has been saved
	{
		CourseRepository inMemoryRepo = this.givenAnInMemoryRepository();
		CourseFinder courseFinder = this.givenACouseFinder(inMemoryRepo);
		CourseSaver courseSaver = this.givenACourseSaver(inMemoryRepo);
		Course course = this.givenACourse();
		courseSaver.saveCourse(course);
		// When method is invoked
		Course retreieved = courseFinder.findCourse(CourseIdMother.fromValue(course.getIdValue()));
		// Then
		assertEquals(retreieved, course);
	}

	@Test
	void it_should_not_get_a_non_existent_course() 
			throws InvalidArgumentException // wont
	{
		// Given
		CourseRepository inMemoryRepo = this.givenAnInMemoryRepository();
		CourseFinder courseFinder = this.givenACouseFinder(inMemoryRepo);
		Course toSearchCourse = this.givenACourse();
		String toSearchCourseIdValue = toSearchCourse.getIdValue();
		
		
		// Then	
		assertThrows(CourseNotExistException.class, 
				() -> courseFinder.findCourse(CourseIdMother.fromValue(toSearchCourseIdValue)));
	}
	
	@Test
	void it_should_get_all_courses_with_courses() throws InvalidArgumentException {
		// Given
		CourseRepository inMemoryRepo = this.givenAnInMemoryRepository();
		inMemoryRepo.saveCourse(CourseMother.create());
		inMemoryRepo.saveCourse(CourseMother.create());
		inMemoryRepo.saveCourse(CourseMother.create());
		CourseFinder finder = new CourseFinder(inMemoryRepo);
		// When
		List<Course> findedCourses = finder.findAll();
		
		// Then
		List<Course> inRepoCourses = inMemoryRepo.getAll().orElse(null);
		assertArrayEquals(inRepoCourses.toArray(), findedCourses.toArray());
	}

	@Test
	void it_should_get_all_course_with_empty_repo() {
		CourseRepository inMemoryRepo = this.givenAnInMemoryRepository();
		CourseFinder finder = new CourseFinder(inMemoryRepo);
		assertArrayEquals(inMemoryRepo.getAll().orElse(null).toArray(), finder.findAll().toArray());
	}

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
		// we mock the publisher just needed for adding to repo
		return new CourseSaver(repo, Mockito.mock(DomainEventPublisher.class));  
	}
}
