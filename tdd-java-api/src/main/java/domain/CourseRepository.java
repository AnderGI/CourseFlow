package domain;

import java.util.Optional;

public interface CourseRepository {
	Optional<Course> getCourse(String id);
	void saveCourse(Course course);
}
