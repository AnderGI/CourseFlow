package com.tdd.api.application.event.course_created;

import com.tdd.api.domain.event.command.EventCommand;

public final class NotifyUserOnCourseCreatedEventCommand implements EventCommand {

	private String date;
	private String courseTitle;
	private String courseId;

	public NotifyUserOnCourseCreatedEventCommand(String date, String courseTitle, String courseId) {
		this.courseId = courseId;
		this.courseTitle = courseTitle;
		this.date = date;
	}

	public String getDate() {
		return this.date;
	}

	public String getCourseTitle() {
		return this.courseTitle;
	}

	public String getCourseId() {
		return this.courseId;
	}

	@Override
	public String toString() {
		return "NotifyUserOnCourseCreatedEventCommand [date=" + date + ", courseTitle=" + courseTitle + ", courseId="
				+ courseId + "]";
	}
}
