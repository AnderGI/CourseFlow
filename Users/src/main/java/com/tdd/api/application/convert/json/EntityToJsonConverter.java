package com.tdd.api.application.convert.json;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tdd.api.application.convert.DomainEntityConverter;
import com.tdd.api.domain.user.User;

public final class EntityToJsonConverter implements DomainEntityConverter<JsonNode, User>{

	
	private ObjectMapper mapper;
	
	public EntityToJsonConverter(ObjectMapper mapper) {
		this.mapper = mapper;
	}
	
	@Override
	public JsonNode convertSingle(User entity) {
		ObjectNode rootNode = mapper.createObjectNode();
		return convertSingleInternals(entity, rootNode);
	}
	
	private JsonNode convertSingleInternals(Object sourceObject, Object node)
	{
		Field[] fields = sourceObject.getClass().getDeclaredFields();
		for(Field field : fields) {
			field.setAccessible(true);
			Object sourceObjectProperty = null;
			try {
				sourceObjectProperty = field.get(sourceObject);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (isPrimitiveOrWrapper(sourceObjectProperty)) 
			{
				// inside value obejcts
				((ObjectNode) node).put("value", sourceObjectProperty.toString());
			}
			else 
			{
				// pointing at value object
				ObjectNode childNode = mapper.createObjectNode();
				((ObjectNode) node).set(field.getName(), 
						this.convertSingleInternals(sourceObjectProperty, childNode));
			}
		}
		return (JsonNode) node;
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

	@Override
	public JsonNode convertAll(List<User> entities) {
		// TODO Auto-generated method stub
		ObjectNode rootNode = mapper.createObjectNode();
		ArrayNode data = mapper.createArrayNode();
		if(entities != null) {
			for(User entity: entities) {
				data.add(convertSingle(entity));
			}
			
		}
		rootNode.set("data", data);
		return rootNode;
	}

}
