package com.tdd.api.infrastructure.database.inmemory;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
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

@TestMethodOrder(MethodOrderer.Random.class)
public class InMemoryRepositoyIntegrationTesting {
	@Test
	// wont throw any exception
	void it_should_add_a_course() throws InvalidArgumentException {
		CourseRepository courseRepo = this.givenAnInMemoryCourseRepository();
		CourseSaver courseSaver = this.givenACourseSaver(courseRepo);
		Course newValidCourse = CourseMother.create();
		courseSaver.saveCourse(newValidCourse);
	}

	@Test
	void it_should_get_an_existing_course_from_database() throws InvalidArgumentException, CourseNotExistError {
		// Given
		CourseRepository courseRepo = this.givenAnInMemoryCourseRepository();
		CourseSaver courseSaver = this.givenACourseSaver(courseRepo);
		Course newValidCourse = CourseMother.create();
		courseSaver.saveCourse(newValidCourse);
		CourseFinder courseFinder = this.givenACourseFinder(courseRepo);
		String courseIdValue = newValidCourse.getIdValue();
		// When method get is called
		Course retreivedCourse = courseFinder.findCourse(CourseIdMother.fromValue(courseIdValue));
		// Then
		assertSame(newValidCourse, retreivedCourse);
	}

	@Test
	void it_should_not_get_a_non_existing_course_from_database() throws InvalidArgumentException {
		
		CourseRepository courseRepo = this.givenAnInMemoryCourseRepository();
		Course newValidCourse = CourseMother.create();
		CourseFinder courseFinder = this.givenACourseFinder(courseRepo);
		String courseIdValue = newValidCourse.getIdValue();
		// When findCourse is called it will throw an error for inexistent courses
		assertThrows(CourseNotExistError.class, 
				() -> courseFinder.findCourse(CourseIdMother.fromValue(courseIdValue)));
	}

	@Test
	void it_should_get_all_existing_courses() throws InvalidArgumentException {
		// Given
		CourseRepository repo = this.givenAnInMemoryCourseRepository();
		repo.saveCourse(CourseMother.create());
		repo.saveCourse(CourseMother.create());
		repo.saveCourse(CourseMother.create());
		repo.saveCourse(CourseMother.create());
		CourseFinder finder = new CourseFinder(repo);
		// When 
		List<Course> coursesToFind = finder.findAll();
		// Then
		assertArrayEquals(repo.getAll().orElse(null).toArray(), coursesToFind.toArray());
		
	}
	
	private CourseRepository givenAnInMemoryCourseRepository() {
		return new InMemoryCourseRepository();
	}

	private CourseSaver givenACourseSaver(CourseRepository repo) {
		return new CourseSaver(repo);
	}

	private CourseFinder givenACourseFinder(CourseRepository repo) {
		return new CourseFinder(repo);
	}
}
