package application.find_course;

import domain.Course;
import domain.CourseId;
import domain.CourseNotExistError;
import domain.CourseRepository;

// As a use case
// it needs to have a repository
// I will do that by applying DIP 
// Working against an abstraction not an implementation
final public class CourseFinder {

	private CourseRepository repository;
	
	public CourseFinder(CourseRepository repository) {
		this.repository = repository;
	}

	public Course findCourse(CourseId id) throws CourseNotExistError {
		Course course = repository.searchCourse(id).orElse(null);
		if(course == null) {
			throw new CourseNotExistError(id);
		}
		return course;
	}

}
