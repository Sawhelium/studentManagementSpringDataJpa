package com.example.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.CourseBean;

@Service
public class CourseService {
	@Autowired
	CourseRepository courseRepository;
	
	//get all course using CrudRepository
	public List<CourseBean> getAllCourse() {
		List<CourseBean> courselist = (List<CourseBean>) courseRepository.findAll();
		return courselist;
	}
	
	//inserting new course
	public void save(CourseBean cbean) {
		courseRepository.save(cbean);
	}
	
	/*
	 * //get course by student Id public Optional<CourseBean> getCourseBySid(String
	 * sid) { return courseRepository.findById(sid); }
	 */
}
