package domain;

import java.util.Optional;

public interface CourseRepository {
	Optional<Course> searchCourse(CourseId id);
	void saveCourse(Course course);
}
