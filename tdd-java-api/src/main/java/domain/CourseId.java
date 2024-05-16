package domain;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

final public class CourseId {
	private final Pattern UUID_REGEX = Pattern.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$");
	private String value;
	
	public CourseId(String id) throws InvalidArgumentException {
		// We should ensure id is valid
		this.ensureIdIsValid(id);
		this.value = id;
	}
	
	private void ensureIdIsValid(String id) throws InvalidArgumentException {
		if(!UUID_REGEX.matcher(id).matches()) {
			throw new InvalidArgumentException("Course id does not have valid format: " + id);
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
		CourseId other = (CourseId) obj;
		return Objects.equals(value, other.value);
	}

}
