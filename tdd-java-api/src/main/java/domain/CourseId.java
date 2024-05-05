package domain;

import java.util.Objects;

final public class CourseId {
	private String value;
	
	public CourseId(String id) {
		// We should ensure id is valid
		this.value = id;
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
		CourseId other = (CourseId) obj;
		return Objects.equals(value, other.value);
	}

}
