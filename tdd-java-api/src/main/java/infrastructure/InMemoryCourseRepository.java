package infrastructure;

import java.util.Optional;

import domain.Course;
import domain.CourseRepository;

public class InMemoryCourseRepository implements CourseRepository {

	@Override
	public Optional<Course> getCourse(String id) {
		// TODO Auto-generated method stub
		Course newCourse = new Course("123", "Course Title");
		return Optional.of(newCourse);
	}

}
