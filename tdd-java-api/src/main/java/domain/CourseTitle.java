package domain;

import java.util.Objects;

final public class CourseTitle {
	private String value;
	
	public CourseTitle(String title) {
		// We should ensure title is valid
		this.value = title;
	}
	
	private void ensureTitleIsValid(String title) throws InvalidArgumentException{
		if(title.isBlank()) {
			throw new InvalidArgumentException("Course title does not have valid format: " + title);
		}
	}
	public String getValue() {
		return value;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseTitle other = (CourseTitle) obj;
		return Objects.equals(value, other.value);
	}

}
