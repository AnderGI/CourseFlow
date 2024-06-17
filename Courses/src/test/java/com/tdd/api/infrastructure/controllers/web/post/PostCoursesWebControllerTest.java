package com.tdd.api.infrastructure.controllers.web.post;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tdd.api.TestingConfig;
import com.tdd.api.domain.CourseMother;
import com.tdd.api.domain.course.Course;

@WebMvcTest
@ActiveProfiles("testing")
@Import(TestingConfig.class)
public class PostCoursesWebControllerTest {
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void it_should_add_course_from_form() throws Exception{
		Course course = CourseMother.create();
		ObjectNode postSuccessNode = mapper.createObjectNode();
		postSuccessNode.put("message", "New course created");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		mockMvc.perform(
				post("/courses")
				.headers(headers)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.params(fromCourseToFormFieldsMap(course))
		)
		.andDo(print())
		.andExpectAll(
				status().isCreated(),
				content().contentType(MediaType.APPLICATION_JSON),
				content().json(postSuccessNode.toString())
		);
	}
	
	@Test
	void it_should_not_add_not_valid_course_json_response() throws Exception {
		Course newCourse = CourseMother.create();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		ObjectNode postErrorNode = mapper.createObjectNode();
		postErrorNode.put("error", "422 Unprocessable Entity");
		postErrorNode.put("message", "Invalid arguments for course creation");
		mockMvc.perform(
				post("/courses")
				.headers(headers)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		)
		.andExpectAll(
				status().isUnprocessableEntity(),
				content().contentType(MediaType.APPLICATION_JSON),
				content().json(postErrorNode.toString())
		)
		;
	}
	
	
	private MultiValueMap<String, String> fromCourseToFormFieldsMap(Course course) {
		LinkedMultiValueMap<String, String> formMap = new LinkedMultiValueMap<>();
		// reflection for course properties
		setToMapCourseProperties(formMap, course, null);
		return formMap;
	}
	
	private void setToMapCourseProperties(MultiValueMap<String, String> map, Object current, Object previous) {
		Field[] fields = current.getClass().getDeclaredFields();
		for(Field field: fields) {
			field.setAccessible(true);
			Object value = null;
			try {
				// Get the value of the field from course object
				value = field.get(current);
				if (isPrimitiveOrWrapper(value)) 
				{
					// get property name
					// From previous get all fields
					String propertyName = 	
					Arrays.asList(previous.getClass().getDeclaredFields())
					// make them accesible
					.stream().map(f -> {
						f.setAccessible(true);
						return f;
					})
					// with field.get(object) get the type of the field property
					// if class equals current return true 
					.filter(f -> {
						try {
							return f.get(previous).getClass().equals(current.getClass());
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return false;
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return false;
						}
					})
					.findFirst().orElse(null)
					.getName()
					;
					
					// property and value
					map.add(propertyName, value.toString());
				}
				else
				{
					// value object
					setToMapCourseProperties(map, value, current);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
	
	
	private boolean isPrimitiveOrWrapper(Object object) {
		final boolean IS_PRIMITIVE = object.getClass().isPrimitive();
		final boolean IS_BOOLEAN_WRAPPER = object.getClass() == Boolean.class;
		final boolean IS_CHAR_WRAPPER = object.getClass() == Character.class;
		final boolean IS_STRING_WRAPPER = object.getClass() == String.class;
		final boolean IS_BYTE_WRAPPER = object.getClass() == Byte.class;
		final boolean IS_SHORT_WRAPPER = object.getClass() == Short.class;
		final boolean IS_INTEGER_WRAPPER = object.getClass() == Integer.class;
		final boolean IS_DOUBLE_WRAPPER = object.getClass() == Double.class;
		final boolean IS_FLOAT_WRAPPER = object.getClass() == Float.class;
		final boolean IS_LONG_WRAPPER = object.getClass() == Long.class;
		return IS_PRIMITIVE || IS_BOOLEAN_WRAPPER || IS_CHAR_WRAPPER || IS_STRING_WRAPPER ||
				IS_BYTE_WRAPPER || IS_SHORT_WRAPPER || IS_INTEGER_WRAPPER || IS_DOUBLE_WRAPPER ||
				IS_FLOAT_WRAPPER || IS_LONG_WRAPPER;
	}
}
