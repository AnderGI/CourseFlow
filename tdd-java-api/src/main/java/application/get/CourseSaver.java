package application.get;

import domain.Course;
import domain.CourseRepository;

final public class CourseSaver {
	private CourseRepository repo;
	public CourseSaver(CourseRepository repo) {
		this.repo = repo;
	}
	
	public void saveCourse(Course course) {
		repo.saveCourse(course);
	}
}
