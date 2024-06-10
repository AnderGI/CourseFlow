package com.tdd.api.domain.parse_exceptions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class CourseNotExistErrorParser implements ExceptionToJsonNodeParser {

	@Override
	public JsonNode parse(Exception exp, ObjectMapper mapper) {
		ObjectNode errorNode = mapper.createObjectNode();
		errorNode.put("error", "404 Not Found");
		errorNode.put("message", "Specified course not found");
		return errorNode;
	}

}
