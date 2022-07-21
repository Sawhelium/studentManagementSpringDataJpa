package com.example.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.model.CourseBean;

public interface CourseRepository extends CrudRepository<CourseBean, String> {

}
