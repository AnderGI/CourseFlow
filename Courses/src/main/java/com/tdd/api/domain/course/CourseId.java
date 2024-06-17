package com.tdd.api.domain.course;

import java.util.Objects;
import java.util.regex.Pattern;
import com.tdd.api.domain.exception.InvalidArgumentException;

public final class CourseId {
	private String value;

	public CourseId(String value) throws InvalidArgumentException {
		// We should ensure id is valid
		this.ensureIdIsValid(value);
		this.value = value;
	}

	private void ensureIdIsValid(String id) throws InvalidArgumentException {
		Pattern UUID_REGEX = Pattern
				.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$");
		if (!UUID_REGEX.matcher(id).matches()) {
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

	@Override
	public String toString() {
		return this.value;
	}

}
