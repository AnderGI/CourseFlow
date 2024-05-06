package application.post;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import domain.Course;
import domain.CourseRepository;
import domain.InvalidArgumentException;
import infrastructure.InMemoryCourseRepository;

public class AddCourseUnitTester {

	@Test
	void it_should_add_a_course() {
		Course validCourse = null;
		try {
			validCourse = new Course("a87df656-c710-416d-81b6-fe341c2589e8", "Course title");
		}catch(InvalidArgumentException exp) {
			exp.printStackTrace();
		}
		// Mocking of the repository
		CourseRepository mock = Mockito.mock(InMemoryCourseRepository.class);
		// We will test that it does not break
		mock.saveCourse(validCourse);
		
	}
	
	// We cannot validate the saving of an invalid course because of static typing
}
