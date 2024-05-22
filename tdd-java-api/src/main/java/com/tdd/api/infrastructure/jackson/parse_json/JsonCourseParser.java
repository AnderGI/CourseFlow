package com.tdd.api.infrastructure.jackson.parse_json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tdd.api.domain.Course;
import com.tdd.api.domain.InvalidArgumentException;

final public class JsonCourseParser {
	private ObjectMapper mapper = new ObjectMapper(); 
	// JSON ¿? ObjectNode¿?
	public ObjectNode fromCourseToJson(Course course) throws JsonProcessingException {
		ObjectNode mainNode = mapper.createObjectNode();
		// de momento a mano
		// nodo de id value 
		ObjectNode idNode = mapper.createObjectNode();
		idNode.put("value", course.getIdValue());
		mainNode.set("id", idNode);
		// title value
		ObjectNode titleNode = mapper.createObjectNode();
		titleNode.put("value", course.getTitleValue());
		mainNode.set("title", titleNode);
		return mainNode;
	}
	
	// ObjectNode extends from JsonNode
	// JsonNode is inmutable -> Better option fro reading
	// ObjectNode better option for creating JSON programmatically
	public Course fromJsonToCourse(JsonNode jsonCourseNode) 
			throws NullPointerException, // when getting the json field values
				InvalidArgumentException // course creation
	{
		// Read all values of the json node
		String id = jsonCourseNode.get("id").get("value").asText();
		String title = jsonCourseNode.get("title").get("value").asText();
		return Course.createFromPrimitives(id, title);
	}
}
