package domain;


final public class Course {
	private String id;
	private String title;
	
	public Course(String id, String title) {
		this.id = id;
		this.title = title;
	}
	
	public String getIdValue() {
		return id;
	}
	
	public String getTitleValue() {
		return title;
	}
	
	public void updateCourseTitle(String title) {
		this.title = title;
	}
}
