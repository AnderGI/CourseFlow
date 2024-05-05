package infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.Course;
import domain.CourseRepository;

final public class InMemoryCourseRepository implements CourseRepository {
	private List<Course> database = new ArrayList<>();
	

	
	@Override
	public Optional<Course> getCourse(String id) {
		database.add(new Course("123", "Course Title"));
		System.out.println(database);
		// TODO Auto-generated method stub
		return database.stream()
				.filter(c -> c.getIdValue().equals(id))
				.findFirst();
	}

}
