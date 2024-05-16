package application.post;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;

import application.save_course.CourseSaver;
import domain.Course;
import domain.CourseMother;
import domain.CourseRepository;
import domain.InvalidArgumentException;
import infrastructure.InMemoryCourseRepository;

@TestMethodOrder(MethodOrderer.Random.class)
public class AddCourseUnitTester {

	@Test
	void it_should_add_a_course() 
			throws InvalidArgumentException // wont throw it
	{
		// 	Test only if saver and repo dont "break"
		CourseRepository inMemoryMock = this.givenAnInMemoryCourseRepository();
		CourseSaver courseSaver = this.givenACourseSaver(inMemoryMock);
		Course toAddValidCourse = CourseMother.create();
		courseSaver.saveCourse(toAddValidCourse);

	}

	private CourseRepository givenAnInMemoryCourseRepository() {
		return Mockito.mock(InMemoryCourseRepository.class);
	}
	
	private CourseSaver givenACourseSaver(CourseRepository repo) {
		return new CourseSaver(repo);
	}
}
