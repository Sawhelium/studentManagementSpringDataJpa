package com.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.dao.CourseRepository;
import com.example.dao.CourseService;

@SpringBootTest
@AutoConfigureMockMvc
public class TestCourseController {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	CourseService courseService;
	
	@MockBean 
	CourseRepository courserRepository;
	
	@Test
	public void testsetupaddcourse() throws Exception {
		this.mockMvc.perform(get("/setupaddcourse"))
		.andExpect(status().isOk())
		.andExpect(view().name("BUD003"))
		.andExpect(model().attributeExists("cbean")); 
		
	}
	
	@Test
	public void testaddcoursevalidate() throws Exception{
		this.mockMvc.perform(post("/addcourse"))
		.andExpect(status().isOk())
		.andExpect(view().name("BUD003"));
	}
	

}
