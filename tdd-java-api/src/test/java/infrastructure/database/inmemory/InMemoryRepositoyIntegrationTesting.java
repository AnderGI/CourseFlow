package infrastructure.database.inmemory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import application.get.CourseGetter;
import domain.Course;
import domain.CourseId;
import domain.CourseTitle;
import domain.InvalidArgumentException;
import infrastructure.InMemoryCourseRepository;

public class InMemoryRepositoyIntegrationTesting {
	@Test
	void it_should_get_an_existing_course_from_database() {
		InMemoryCourseRepository repo = new InMemoryCourseRepository();
		CourseGetter useCase = new CourseGetter(repo);
		Course toRetrieveCourse = null;
		try {
			toRetrieveCourse = new Course("a87df656-c710-416d-81b6-fe341c2589e8","Course Title");
		} catch (InvalidArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Course retrievedCourse = useCase.getCourseById("a87df656-c710-416d-81b6-fe341c2589e8");
		assertNotNull(retrievedCourse);
		assertEquals(Course.class, retrievedCourse.getClass());
		assertEquals(toRetrieveCourse, retrievedCourse);
	}

	@Test
	void it_should_not_get_an_inexisting_course_from_database() {
		InMemoryCourseRepository repo = new InMemoryCourseRepository();
		CourseGetter useCase = new CourseGetter(repo);
		Course retrievedCourse = useCase.getCourseById("fakeId");
		assertEquals(null, retrievedCourse);
	}
	
	@Test
	void it_should_add_a_course() {
		InMemoryCourseRepository repo = new InMemoryCourseRepository();
		CourseGetter useCase = new CourseGetter(repo);
		Course toRetrieveCourse = null;
		try {
			toRetrieveCourse = new Course("a87df656-c710-416d-81b6-fe341c2589e8","Course Title");
		} catch (InvalidArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
