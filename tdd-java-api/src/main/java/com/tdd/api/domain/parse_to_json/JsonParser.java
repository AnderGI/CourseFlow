package com.tdd.api.domain.parse_to_json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tdd.api.domain.Course;

final public class JsonParser {
	private ObjectMapper mapper = new ObjectMapper(); 
	public String createJsonCourseResponse(Course course) throws JsonProcessingException {
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
		return mapper.writeValueAsString(mainNode);
	}
}
