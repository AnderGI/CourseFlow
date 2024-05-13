package domain;

import java.util.UUID;

final public class CourseIdMother {
	public static CourseId random() throws InvalidArgumentException {
		return new CourseId(UUID.randomUUID().toString());
	}
}
