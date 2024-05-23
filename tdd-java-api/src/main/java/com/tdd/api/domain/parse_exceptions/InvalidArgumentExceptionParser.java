package com.tdd.api.domain.parse_exceptions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class InvalidArgumentExceptionParser implements ExceptionToJsonNodeParser {

	@Override
	public JsonNode parse(Exception exp, ObjectMapper mapper) {
		ObjectNode errorNode = mapper.createObjectNode();
		errorNode.put("error", "422 Unprocessable Entity");
		errorNode.put("message", "Invalid arguments for course creation");
		return errorNode;
	}

}
