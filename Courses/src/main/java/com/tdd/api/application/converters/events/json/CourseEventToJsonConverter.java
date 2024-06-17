package com.tdd.api.application.converters.events.json;

import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tdd.api.application.converters.events.EventToResponseConverter;
import com.tdd.api.domain.exception.CourseEvent;

public final class CourseEventToJsonConverter implements EventToResponseConverter<CourseEvent, JsonNode>{

	private ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public JsonNode convert(CourseEvent event) {
		ObjectNode root = mapper.createObjectNode();
		return convertReflection(root, event);
	}
	
	private JsonNode convertReflection(ObjectNode rootNode, Object reflectedObject) {
		// Get all the fields from that class only
		Field[] fields = reflectedObject.getClass().getDeclaredFields();
		for(Field field : fields) {
			// ensure accesibility to that field
			field.setAccessible(true);
			Object property;
			try {
				property = field.get(reflectedObject);
				// Scenario 1 : Have a value object
				if(isPrimitiveOrWrapper(property)) {
					// get property name and value and add it to the node
					rootNode.put(field.getName(), property.toString());
				}else if(property instanceof Object) {
					// reflection inside that value object	
					ObjectNode node = mapper.createObjectNode();
					rootNode.set(field.getName(), this.convertReflection(node, property));
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
