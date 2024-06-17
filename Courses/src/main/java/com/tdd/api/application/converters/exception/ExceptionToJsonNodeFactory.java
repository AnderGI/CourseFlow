package com.tdd.api.application.converters.exception;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdd.api.application.converters.exception.json.CourseNotExistErrorConverter;
import com.tdd.api.application.converters.exception.json.InternalServerErrorConverter;
import com.tdd.api.application.converters.exception.json.InvalidArgumentExceptionConverter;
import com.tdd.api.application.converters.exception.json.NullPointerExceptionConverter;
import com.tdd.api.domain.exception.CourseNotExistException;
import com.tdd.api.domain.exception.InvalidArgumentException;

final public class ExceptionToJsonNodeFactory {
	
	private static Map<Class<? extends Exception>, ExceptionToJsonNodeConverter> exceptionToParserMap = new HashMap<>();
	private static ObjectMapper mapper = new ObjectMapper();
	static {
		exceptionToParserMap.put(InvalidArgumentException.class, new InvalidArgumentExceptionConverter());
		exceptionToParserMap.put(CourseNotExistException.class, new CourseNotExistErrorConverter());
		exceptionToParserMap.put(NullPointerException.class, new NullPointerExceptionConverter());
	}
	
	public static JsonNode parse(Exception exp) {
		ExceptionToJsonNodeConverter expParser = exceptionToParserMap.get(exp.getClass());
		if(expParser == null) return defaultParser().parse(exp, mapper);
		return expParser.parse(exp, mapper);
	}
	
	// necessary ¿?
	private static ExceptionToJsonNodeConverter defaultParser() {
		return (exp, mapper) -> (new InternalServerErrorConverter()).parse(exp, mapper);
	}
	
}
