package com.tdd.api.infrastructure.jackson.parse_json;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdd.api.domain.CourseNotExistError;
import com.tdd.api.domain.InvalidArgumentException;
import com.tdd.api.domain.parse_exceptions.CourseNotExistErrorParser;
import com.tdd.api.domain.parse_exceptions.ExceptionToJsonNodeParser;
import com.tdd.api.domain.parse_exceptions.InternalServerErrorParser;
import com.tdd.api.domain.parse_exceptions.InvalidArgumentExceptionParser;
import com.tdd.api.domain.parse_exceptions.NullPointerExceptionParser;

final public class ExceptionToJsonNodeFactory {
	
	private static Map<Class<? extends Exception>, ExceptionToJsonNodeParser> exceptionToParserMap = new HashMap<>();
	private static ObjectMapper mapper = new ObjectMapper();
	static {
		exceptionToParserMap.put(InvalidArgumentException.class, new InvalidArgumentExceptionParser());
		exceptionToParserMap.put(CourseNotExistError.class, new CourseNotExistErrorParser());
		exceptionToParserMap.put(NullPointerException.class, new NullPointerExceptionParser());
	}
	
	public static JsonNode parse(Exception exp) {
		ExceptionToJsonNodeParser expParser = exceptionToParserMap.get(exp.getClass());
		if(expParser == null) return defaultParser().parse(exp, mapper);
		return expParser.parse(exp, mapper);
	}
	// necessary ¿?
	private static ExceptionToJsonNodeParser defaultParser() {
		return (exp, mapper) -> (new InternalServerErrorParser()).parse(exp, mapper);
	}
	
}
