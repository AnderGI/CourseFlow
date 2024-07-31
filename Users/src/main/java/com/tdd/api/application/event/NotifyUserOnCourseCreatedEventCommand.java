package com.tdd.api.application.event;

import java.util.Objects;

import com.fasterxml.jackson.databind.JsonNode;
import com.tdd.api.domain.event.EventCommand;

public final class NotifyUserOnCourseCreatedEventCommand implements EventCommand {

	private String title;
	private JsonNode courseDescription;
	private JsonNode courseLearning;
	private JsonNode courseFeatures;

	public NotifyUserOnCourseCreatedEventCommand
	(String title, JsonNode descr, JsonNode learnings, JsonNode features) {
		this.title = title;
		this.courseDescription = descr;
		this.courseLearning = learnings;
		this.courseFeatures = features;
	}

	public String getTitle() {
		return this.title;
	}

	public JsonNode getDescription() {
		return this.courseDescription;
	}

	public JsonNode getCourseLearnings() {
		return this.courseLearning;
	}

	public JsonNode getCourseFeatures() {
		return this.courseFeatures;
	}

	@Override
	public int hashCode() {
		return Objects.hash(courseDescription, courseFeatures, courseLearning, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotifyUserOnCourseCreatedEventCommand other = (NotifyUserOnCourseCreatedEventCommand) obj;
		return Objects.equals(courseDescription, other.courseDescription)
				&& Objects.equals(courseFeatures, other.courseFeatures)
				&& Objects.equals(courseLearning, other.courseLearning) && Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "NotifyUserOnCourseCreatedEventCommand [title=" + title + ", courseDescription=" + courseDescription
				+ ", courseLearning=" + courseLearning + ", courseFeatures=" + courseFeatures + "]";
	}


	
	

	
}
