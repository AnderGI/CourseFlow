package com.tdd.api.domain.parse_exceptions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface ExceptionToJsonNodeParser {
	JsonNode parse(Exception exp, ObjectMapper mapper);
}
