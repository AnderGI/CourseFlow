package com.tdd.api.application.save_course;

import org.springframework.stereotype.Service;

import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseRepository;
import com.tdd.api.domain.events.DomainEventPublisher;

@Service
public final class CourseSaver {
	// Remaining event publisher for RabbitMQ
	private CourseRepository repo;
	private DomainEventPublisher publisher;
	public CourseSaver(CourseRepository repo, DomainEventPublisher publisher) {
		this.repo = repo;
		this.publisher = publisher;
	}
	
	// El curso ya llegaria validado
	// A la hora de instanciarlo ya los ValueObjects lanzan excepcciones
	public void saveCourse(Course course) {
		repo.saveCourse(course);
		publisher.publish(course);
	}
}
