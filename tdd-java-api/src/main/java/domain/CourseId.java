package domain;

final public class CourseId {
	private String value;
	
	public CourseId(String id) {
		// We should ensure id is valid
		this.value = id;
	}
	
	public String getValue() {
		return value;
	}
}
