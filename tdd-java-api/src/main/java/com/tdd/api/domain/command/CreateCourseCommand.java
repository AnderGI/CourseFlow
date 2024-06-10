package com.tdd.api.domain.command;

public final class CreateCourseCommand implements Command {
	private String id;
	private String title;
	
	public CreateCourseCommand(String id, String title) {
		this.id = id;
		this.title = title;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getTitle() {
		return this.title;
	}
}
