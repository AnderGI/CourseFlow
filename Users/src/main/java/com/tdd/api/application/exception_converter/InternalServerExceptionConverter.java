package com.tdd.api.application.exception_converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class InternalServerExceptionConverter implements ExceptionToJsonNodeConverter {

	@Override
	public JsonNode parse(Exception exp, ObjectMapper mapper) {
		ObjectNode errorNode = mapper.createObjectNode();
		errorNode.put("error", "500 Internal Server Error");
		errorNode.put("message", "Ups! Something went wrong!");
		return errorNode;
	}

}
