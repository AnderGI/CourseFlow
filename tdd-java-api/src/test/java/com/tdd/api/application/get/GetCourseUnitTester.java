package com.tdd.api.application.get;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

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
import com.tdd.api.infrastructure.bbdd.inmemory.InMemoryCourseRepository;

// Randomize JUNIT tests
// Each test suite will be random among different JVM
// But the randomization will be the same within each JVM once executed
@TestMethodOrder(MethodOrderer.Random.class)
public class GetCourseUnitTester {
	@Test
	void it_should_get_an_existing_course() 
			throws InvalidArgumentException, // wont cause arguments in VO are Ok
			CourseNotExistError // wont cause course has been saved
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
		assertThrows(CourseNotExistError.class, 
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
		return new CourseSaver(repo);
	}

}
