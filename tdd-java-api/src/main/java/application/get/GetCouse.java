package application.get;

import domain.Course;
import domain.CourseRepository;

// As a use case
// it needs to have a repository
// I will do that by applying DIP 
// Working against an abstraction not an implementation
final public class GetCouse {

	private CourseRepository repository;
	
	public GetCouse(CourseRepository repository) {
		this.repository = repository;
	}

	public Course getCourseById(String id) {
		Course course = repository.getCourse(id).orElse(null);
		return course;
	}

}
