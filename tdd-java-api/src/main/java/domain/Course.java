package domain;

import java.util.Objects;

final public class Course {
	private CourseId id;
	private CourseTitle title;
	
	public Course(String id, String title) {
		this.id = new CourseId(id); 
		this.title = new CourseTitle(title); 
	}
	
	public String getIdValue() {
		return id.getValue();
	}
	
	public String getTitleValue() {
		return title.getValue();
	}
	
	// Since Value Objects are unique and differ one another from its value
	// whenever a user updates a title it will update the whole VO
	// by creating a new one
	public void updateCourseTitle(String title) {
		this.title = new CourseTitle(title);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
