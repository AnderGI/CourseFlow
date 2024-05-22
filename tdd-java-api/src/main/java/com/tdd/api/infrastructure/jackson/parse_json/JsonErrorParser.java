package com.tdd.api.infrastructure.jackson.parse_json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tdd.api.domain.CourseNotExistError;
import com.tdd.api.domain.InvalidArgumentException;

final public class JsonErrorParser {
	private static ObjectMapper mapper = new ObjectMapper();
	
	public JsonNode fromExceptionToJson(Exception exp) {
		ObjectNode errorNode = mapper.createObjectNode();
		if(exp instanceof InvalidArgumentException || exp instanceof NullPointerException) {
			errorNode.put("error", "422 Unprocessable Entity");
			errorNode.put("message", "Invalid arguments for course creation");
		}else if(exp instanceof CourseNotExistError){
			errorNode.put("error", "404 Not Found");
			errorNode.put("message", "Specified course not found");
		}else {
			errorNode.put("error", "500 Internal Server Error");
			errorNode.put("message", "Ups! Something went wrong!");
		}
		
		return errorNode;
	}
}
