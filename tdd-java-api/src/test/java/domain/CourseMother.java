package domain;

final public class CourseMother {
	private CourseIdMother id;
	private CourseTitleMother title;
	
	public static Course create() throws InvalidArgumentException {
		return new Course(CourseIdMother.random(), CourseTitleMother.random());
	}
}
