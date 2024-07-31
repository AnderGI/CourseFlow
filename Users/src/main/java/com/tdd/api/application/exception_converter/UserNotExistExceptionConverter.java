package com.tdd.api.application.exception_converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class UserNotExistExceptionConverter implements ExceptionToJsonNodeConverter {

	@Override
	public JsonNode parse(Exception exp, ObjectMapper mapper) {
		ObjectNode errorNode = mapper.createObjectNode();
		errorNode.put("error", "404 Not Found");
		errorNode.put("message", "Specified user not found");
		return errorNode;
	}

}
