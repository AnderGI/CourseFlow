package com.tdd.api.infrastructure.bus;

import java.util.HashMap;
import java.util.Map;

import com.tdd.api.application.find_course.FindCourseQuery;
import com.tdd.api.application.find_course.FindCourseQueryHandler;
import com.tdd.api.domain.InvalidArgumentException;
import com.tdd.api.domain.Query;
import com.tdd.api.domain.QueryBus;
import com.tdd.api.domain.QueryHandler;

public class CourseQueryBus implements QueryBus {
    private final Map<Class<? extends Query>, QueryHandler> handlers = new HashMap<>();

    @Override
    public <Q extends Query, R> void registerHandler(Class<Q> queryType, QueryHandler<Q, R> handler) {
        handlers.put(queryType, handler);
    }
	@Override
	public <R> R ask(Query query) throws Exception {
		// TODO Auto-generated method stub
		QueryHandler handler = handlers.get(query.getClass());
		return (R) handler.handle(query);
	}
	


}
