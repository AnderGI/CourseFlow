package com.tdd.api.application.save_course;

import org.springframework.stereotype.Service;

import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseRepository;

@Service
final public class CourseSaver {
	// Remaining event publisher for RabbitMQ
	private CourseRepository repo;
	public CourseSaver(CourseRepository repo) {
		this.repo = repo;
	}
	
	// El curso ya llegaria validado
	// A la hora de instanciarlo ya los ValueObjects lanzan excepcciones
	public void saveCourse(Course course) {
		repo.saveCourse(course);
		// ejemplo publisher
		// publisher.publish(new CourseCreated(course)) <- CourseCreated es un evento de dominio
	}
}
