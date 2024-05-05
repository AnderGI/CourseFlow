package domain;

final public class CourseTitle {
	private String value;
	
	public CourseTitle(String title) {
		// We should ensure title is valid
		this.value = title;
	}
	
	public String getValue() {
		return value;
	}
}
