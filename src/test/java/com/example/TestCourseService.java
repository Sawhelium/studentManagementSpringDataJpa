package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.dao.CourseRepository;
import com.example.dao.CourseService;
import com.example.model.CourseBean;

@SpringBootTest
public class TestCourseService {
	@Mock
	CourseRepository courseRepository;
	
	@InjectMocks
	CourseService courseService;
	
	@Test
	public void getAllCourseTest() throws Exception{
		List<CourseBean> list = new ArrayList<CourseBean>();
		
		CourseBean cb1 = new CourseBean();
		cb1.setCid("C001");
		cb1.setCname("php");
		
		CourseBean cb2 = new CourseBean();
		cb2.setCid("C002");
		cb2.setCname("css");
		
		CourseBean cb3 = new CourseBean();
		cb3.setCid("C003");
		cb3.setCname("java");
		
		list.add(cb1);
		list.add(cb2);
		list.add(cb3);
		
		when(courseRepository.findAll()).thenReturn(list);
		List<CourseBean> clist = courseService.getAllCourse();
		assertEquals(3,clist.size());
		verify(courseRepository, times(1)).findAll();
	}
	
	@Test
	public void saveTest() throws Exception{
		CourseBean cbean = new CourseBean();
		cbean.setCid("C001");
		cbean.setCname("Python");
		courseService.save(cbean);
		verify(courseRepository,times(1)).save(cbean);
	}

}
