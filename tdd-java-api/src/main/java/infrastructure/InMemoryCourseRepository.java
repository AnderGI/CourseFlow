package infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.Course;
import domain.CourseRepository;
import domain.InvalidArgumentException;

final public class InMemoryCourseRepository implements CourseRepository {
	private List<Course> database = new ArrayList<>();
	

	
	@Override
	public Optional<Course> getCourse(String id) {
		try {
			database.add(new Course("123", "Course Title"));
		} catch (InvalidArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(database);
		// TODO Auto-generated method stub
		return database.stream()
				.filter(c -> c.getIdValue().equals(id))
				.findFirst();
	}


	@Override
	public void saveCourse(Course course) {
		// TODO Auto-generated method stub
		database.add(course);		
	}

}
