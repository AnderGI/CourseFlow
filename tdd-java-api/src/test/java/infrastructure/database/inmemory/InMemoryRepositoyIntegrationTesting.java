package infrastructure.database.inmemory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import application.get.CourseGetter;
import application.get.CourseSaver;
import domain.Course;
import domain.CourseId;
import domain.CourseTitle;
import domain.InvalidArgumentException;
import infrastructure.InMemoryCourseRepository;

@TestMethodOrder(MethodOrderer.Random.class)
public class InMemoryRepositoyIntegrationTesting {
	@Test
	void it_should_add_a_course() {
		InMemoryCourseRepository repo = new InMemoryCourseRepository();
		CourseSaver useCase = new CourseSaver(repo);
		Course toRetrieveCourse = null;
		try {
			toRetrieveCourse = Course.createFromPrimitives("a87df656-c710-416d-81b6-fe341c2589e8","Course Title");
		} catch (InvalidArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		useCase.saveCourse(toRetrieveCourse);
	}
	@Test
	void it_should_get_an_existing_course_from_database() {
		InMemoryCourseRepository repo = new InMemoryCourseRepository();
		// Save a course
		Course newCourse = null;
		try {
			newCourse = Course.createFromPrimitives("a87df656-c710-416d-81b6-fe341c2589e8","Course Title");
		} catch (InvalidArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CourseSaver saver = new CourseSaver(repo);
		saver.saveCourse(newCourse);
		// Get the course
		CourseGetter getter = new CourseGetter(repo);
		Course retrievedCourse = getter.getCourseById(newCourse.getIdValue());
		assertNotNull(retrievedCourse);
		assertEquals(Course.class, retrievedCourse.getClass());
		assertEquals(newCourse, retrievedCourse);
	}

	@Test
	void it_should_not_get_an_inexisting_course_from_database() {
		InMemoryCourseRepository repo = new InMemoryCourseRepository();
		CourseGetter useCase = new CourseGetter(repo);
		Course retrievedCourse = useCase.getCourseById("fakeId");
		assertEquals(null, retrievedCourse);
	}
	

	
}
