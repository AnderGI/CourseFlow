package com.tdd.api.application.converters.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface ExceptionToJsonNodeConverter {
	JsonNode parse(Exception exp, ObjectMapper mapper);
}
