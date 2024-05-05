package domain;


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
}
