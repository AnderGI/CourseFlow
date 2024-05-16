package application.get;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;

import application.find_course.CourseFinder;
import application.save_course.CourseSaver;
import domain.Course;
import domain.CourseId;
import domain.CourseIdMother;
import domain.CourseMother;
import domain.CourseNotExistError;
import domain.CourseRepository;
import domain.InvalidArgumentException;
import infrastructure.InMemoryCourseRepository;

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
		CourseRepository inMemoryMock = this.givenAnInMemoryMock();
		CourseFinder courseFinder = this.givenACouseFinder(inMemoryMock);
		CourseSaver courseSaver = this.givenACourseSaver(inMemoryMock);
		Course toSearchCourse = this.givenACourse();
		courseSaver.saveCourse(toSearchCourse);
		String toSearchCourseIdValue = toSearchCourse.getIdValue();
		
		// When method is invoked
		when(inMemoryMock.searchCourse(CourseIdMother.fromValue(toSearchCourseIdValue)))
		.thenReturn(Optional.of(toSearchCourse));
	
		// Then
		Course retreieved = courseFinder.findCourse(CourseIdMother.fromValue(toSearchCourseIdValue));
		assertEquals(retreieved, toSearchCourse);
	}

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

	
	private CourseRepository givenAnInMemoryMock() {
		return Mockito.mock(InMemoryCourseRepository.class);
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
