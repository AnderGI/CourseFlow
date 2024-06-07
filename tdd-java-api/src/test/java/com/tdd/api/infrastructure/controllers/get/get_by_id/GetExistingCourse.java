package com.tdd.api.infrastructure.controllers.get.get_by_id;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.tdd.api.application.convert_reponse.CourseJsonResponseConverter;
import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseMother;
import com.tdd.api.domain.CourseRepository;

@WebMvcTest
public class GetExistingCourse {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseRepository repository;


    @Test
    void it_should_get_an_existing_course_by_id() throws Exception {
    	Course validCourse = CourseMother.create();
    	CourseJsonResponseConverter converter = new CourseJsonResponseConverter();
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    	params.set("id", validCourse.getIdValue());
    	params.set("title", validCourse.getTitleValue());
    	mockMvc.perform(post("/courses")
    			.headers(headers)
    			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
    			.params(params)
       );
          
        mockMvc.perform(get("/courses/{id}", validCourse.getIdValue())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json(((JsonNode)converter.convertSingle(validCourse)).toString())
                		);
    }
}