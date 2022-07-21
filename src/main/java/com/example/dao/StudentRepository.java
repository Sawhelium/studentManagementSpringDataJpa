package com.example.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.model.StudentBean;

@Repository
public interface StudentRepository extends CrudRepository<StudentBean, String> {
	List<StudentBean> findDistinctBySidOrSnameContainingOrStuAttend_CnameContaining(String sid, String sname, String course); 
	StudentBean findBySid(String sid);
}
