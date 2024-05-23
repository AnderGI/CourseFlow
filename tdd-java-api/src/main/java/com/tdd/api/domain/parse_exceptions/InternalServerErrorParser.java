package com.tdd.api.domain.parse_exceptions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class InternalServerErrorParser implements ExceptionToJsonNodeParser {

	@Override
	public JsonNode parse(Exception exp, ObjectMapper mapper) {
		ObjectNode errorNode = mapper.createObjectNode();
		errorNode.put("error", "500 Internal Server Error");
		errorNode.put("message", "Ups! Something went wrong!");
		return errorNode;
	}

}
