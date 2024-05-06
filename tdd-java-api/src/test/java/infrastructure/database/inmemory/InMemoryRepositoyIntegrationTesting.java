package infrastructure.database.inmemory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import application.get.GetCouse;
import domain.Course;
import domain.CourseId;
import domain.CourseTitle;
import domain.InvalidArgumentException;
import infrastructure.InMemoryCourseRepository;

public class InMemoryRepositoyIntegrationTesting {
	@Test
	void it_should_get_an_existing_course_from_database() {
		InMemoryCourseRepository repo = new InMemoryCourseRepository();
		GetCouse useCase = new GetCouse(repo);
		Course toRetrieveCourse = null;
		try {
			toRetrieveCourse = new Course("123","Course Title");
		} catch (InvalidArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Course retrievedCourse = useCase.getCourseById("123");
		assertNotNull(retrievedCourse);
		assertEquals(Course.class, retrievedCourse.getClass());
		assertEquals(toRetrieveCourse, retrievedCourse);
	}

	@Test
	void it_should_not_get_an_inexisting_course_from_database() {
		InMemoryCourseRepository repo = new InMemoryCourseRepository();
		GetCouse useCase = new GetCouse(repo);
		Course retrievedCourse = useCase.getCourseById("fakeId");
		assertEquals(null, retrievedCourse);
	}
	
}
