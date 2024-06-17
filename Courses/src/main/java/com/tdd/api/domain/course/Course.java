package com.tdd.api.domain.course;

import java.util.Objects;
import com.tdd.api.domain.DomainEntity;
import com.tdd.api.domain.exception.InvalidArgumentException;

public final class Course implements DomainEntity{
	private CourseId id;
	private CourseTitle title;

	public Course(CourseId id, CourseTitle title)
			throws InvalidArgumentException {
		this.id = id;
		this.title = title;
	}

	public static Course createFromPrimitives(String id, String title) throws InvalidArgumentException {
		Course newCourse = new Course(new CourseId(id), new CourseTitle(title));
		return newCourse;
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
	public void updateCourseTitle(String title) throws InvalidArgumentException {
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

	@Override
	public String toString() {
		return "Course [id=" + id + ", title=" + title + "]";
	}

}