package com.tdd.api.application.converters.response.json;

import java.lang.reflect.Field;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tdd.api.application.converters.response.ResponseConverter;
import com.tdd.api.domain.course.Course;

public final class CourseJsonResponseConverter implements ResponseConverter {
	private static ObjectMapper mapper = new ObjectMapper();

	
	@Override
	public <R> R convertSingle(Course course) {
		ObjectNode mainCourseNode = mapper.createObjectNode();
		
		return (R) ((JsonNode) this.convertEntityToJsonNode(course, mainCourseNode));
	}


	@Override
	public <R> R convertAll(List<Course> courses) {
		ObjectNode rootNode = mapper.createObjectNode();
		ArrayNode arrayNode = mapper.createArrayNode();
		for(Course course : courses) {
			ObjectNode courseNode = mapper.createObjectNode();
			arrayNode.add(this.convertEntityToJsonNode(course, courseNode));
		}
		rootNode.set("courses", arrayNode);
		return (R) ((JsonNode) rootNode);
	}
	
	private JsonNode convertEntityToJsonNode(Object entity, ObjectNode rootNode) {
		Field[] fields = entity.getClass().getDeclaredFields();
		for(Field field : fields) {
			// allow java to access each field of the course class
			// without IllegalAccessException
			field.setAccessible(true);
			try {
				Object property = field.get(entity);
				if(isPrimitiveOrWrapper(property)) { // "inside" Value Object class
					rootNode.put("value", property.toString());
				}else { // field is value object, NOT "inside" of it
					// property === ValueObject
					// Create New node for following this structure
					/*
					 *{ "id" : { "value" : "uuidv4" }
					 */
					rootNode.set(field.getName(), 
							convertEntityToJsonNode(property, mapper.createObjectNode()));
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return rootNode;
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
