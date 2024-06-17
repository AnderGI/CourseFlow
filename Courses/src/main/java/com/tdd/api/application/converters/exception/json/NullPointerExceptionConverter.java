package com.tdd.api.application.converters.exception.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tdd.api.application.converters.exception.ExceptionToJsonNodeConverter;

public final class NullPointerExceptionConverter implements ExceptionToJsonNodeConverter {

	@Override
	public JsonNode parse(Exception exp, ObjectMapper mapper) {
		ObjectNode errorNode = mapper.createObjectNode();
		errorNode.put("error", "422 Unprocessable Entity");
		errorNode.put("message", "Invalid arguments for course creation");
		return errorNode;
	}

}
