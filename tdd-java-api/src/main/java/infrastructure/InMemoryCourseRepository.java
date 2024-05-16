package infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.Course;
import domain.CourseId;
import domain.CourseRepository;
import domain.InvalidArgumentException;

final public class InMemoryCourseRepository implements CourseRepository {
	private List<Course> database = new ArrayList<>();
	

	
	@Override
	public Optional<Course> searchCourse(CourseId id) {
		return database.stream()
				.filter(c -> c.getIdValue().equals(id.getValue()))
				.findFirst();
	}


	@Override
	public void saveCourse(Course course) {
		// TODO Auto-generated method stub
		database.add(course);		
	}

}
