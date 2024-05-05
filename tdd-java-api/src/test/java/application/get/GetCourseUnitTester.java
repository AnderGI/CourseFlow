package application.get;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import domain.Course;
import domain.CourseRepository;
import infrastructure.InMemoryCourseRepository;

public class GetCourseUnitTester {

	@Test
	void it_should_get_an_existing_course_by_id() {
		// Mock the CourseRepository
		CourseRepository inMemoryMock = Mockito.mock(InMemoryCourseRepository.class);
		// Dependency Inversion for the repository using mocks
		GetCouse getCourse = new GetCouse(inMemoryMock);
		// Specify the action and result of calling a mock method
		when(inMemoryMock.getCourse("123")).thenReturn(Optional.of(new Course("123", "Course title")));

		// Car to receive compared to the one the repo returns
		Course toRetrieve = new Course("123", "Course title");
		Course retreieved = getCourse.getCourseById("123");
		assertEquals(toRetrieve, retreieved);
	}

	@Test
	void it_should_not_get_an_inexistent_course() {
		// Mock the CourseRepository
		CourseRepository inMemoryMock = Mockito.mock(InMemoryCourseRepository.class);
		// Dependency Inversion for the repository using mocks
		GetCouse getCourse = new GetCouse(inMemoryMock);
		
		when(inMemoryMock.getCourse("fakeId")).thenReturn(Optional.empty());
		Course retrieved = getCourse.getCourseById("fakeId");
		assertEquals(null, retrieved);
	}
}
