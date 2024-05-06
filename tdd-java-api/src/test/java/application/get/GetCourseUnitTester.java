package application.get;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import domain.Course;
import domain.CourseRepository;
import domain.InvalidArgumentException;
import infrastructure.InMemoryCourseRepository;

public class GetCourseUnitTester {

	@Test
	void it_should_get_an_existing_course_by_id() {
		// Mock the CourseRepository
		CourseRepository inMemoryMock = Mockito.mock(InMemoryCourseRepository.class);
		// Dependency Inversion for the repository using mocks
		GetCouse getCourse = new GetCouse(inMemoryMock);
		// Specify the action and result of calling a mock method
		Course course = null; 
		try {
			course = new Course("a87df656-c710-416d-81b6-fe341c2589e8", "Course title");
		} catch (InvalidArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		when(inMemoryMock.getCourse("a87df656-c710-416d-81b6-fe341c2589e8")).thenReturn(Optional.of(course));

		// Car to receive compared to the one the repo returns
		Course toRetrieve = null; 
		try {
			toRetrieve = new Course("a87df656-c710-416d-81b6-fe341c2589e8", "Course title");
		} catch (InvalidArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Course retreieved = getCourse.getCourseById("a87df656-c710-416d-81b6-fe341c2589e8");
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
