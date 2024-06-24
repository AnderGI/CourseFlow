package com.tdd.api.application.convert.exception;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdd.api.domain.exception.InvalidArgumentException;
import com.tdd.api.domain.exception.UserNotExistException;

final public class ExceptionToJsonNodeFactory {
	
	private static Map<Class<? extends Exception>, ExceptionToJsonNodeConverter> exceptionToParserMap = new HashMap<>();
	private static ObjectMapper mapper = new ObjectMapper();
	static {
		exceptionToParserMap.put(InvalidArgumentException.class, new InvalidArgumentExceptionConverter());
		exceptionToParserMap.put(UserNotExistException.class, new UserNotExistExceptionConverter());
		exceptionToParserMap.put(NullPointerException.class, new NullPointerExceptionConverter());
	}
	
	public static JsonNode parse(Exception exp) {
		ExceptionToJsonNodeConverter expParser = exceptionToParserMap.get(exp.getClass());
		if(expParser == null) return defaultParser().parse(exp, mapper);
		return expParser.parse(exp, mapper);
	}
	
	// necessary ¿?
	private static ExceptionToJsonNodeConverter defaultParser() {
		return (exp, mapper) -> (new InternalServerExceptionConverter()).parse(exp, mapper);
	}
	
}
