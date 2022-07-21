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

import com.example.dao.StudentRepository;
import com.example.dao.StudentService;
import com.example.model.CourseBean;
import com.example.model.StudentBean;

@SpringBootTest

public class TestStudentService {

	@Mock
	StudentRepository studentRepository;

	@InjectMocks
	StudentService studentService;

	@Test
	public void saveStudentTest() {
		
		StudentBean bean = new StudentBean();

		List<CourseBean> list = new ArrayList<CourseBean>();
		CourseBean cbean = new CourseBean();
		cbean.setCid("C001");
		cbean.setCname("java");

		CourseBean cbean1 = new CourseBean();
		cbean1.setCid("C002");
		cbean1.setCname("php");

		list.add(cbean);
		list.add(cbean1);

		bean.setSid("STD001");
		bean.setSname("Theingi");
		bean.setSgender("female");
		bean.setSdob("10.2.1999");
		bean.setSphone("09999999");
		bean.setEducation("degree");
		bean.setStuAttend(list);

		studentService.insertStudent(bean);
		verify(studentRepository, times(1)).save(bean);
	}

	@Test
	public void getAllStudentTest() {

		List<StudentBean> stulist = new ArrayList<StudentBean>();
		List<CourseBean> list = new ArrayList<CourseBean>();

		StudentBean bean = new StudentBean();
		CourseBean cbean = new CourseBean();
		cbean.setCid("C001");
		cbean.setCname("java");

		CourseBean cbean1 = new CourseBean();
		cbean1.setCid("C002");
		cbean1.setCname("php");

		list.add(cbean);
		list.add(cbean1);

		bean.setSid("STD001");
		bean.setSname("Theingi");
		bean.setSgender("female");
		bean.setSdob("10.2.1999");
		bean.setSphone("09999999");
		bean.setEducation("degree");
		bean.setStuAttend(list);

		stulist.add(bean);

		when(studentRepository.findAll()).thenReturn(stulist);
		
		List<StudentBean> studentlist = studentService.getAllStudent();
		assertEquals(1, studentlist.size());
		verify(studentRepository, times(1)).findAll();
	}

	@Test
	public void getStudentByIdTest() {

		List<StudentBean> stulist = new ArrayList<StudentBean>();
		List<CourseBean> list = new ArrayList<CourseBean>();

		StudentBean bean = new StudentBean();
		CourseBean cbean = new CourseBean();
		cbean.setCid("C001");
		cbean.setCname("java");

		CourseBean cbean1 = new CourseBean();
		cbean1.setCid("C002");
		cbean1.setCname("php");

		list.add(cbean);
		list.add(cbean1);

		bean.setSid("STD001");
		bean.setSname("Theingi");
		bean.setSgender("female");
		bean.setSdob("10.2.1999");
		bean.setSphone("09999999");
		bean.setEducation("degree");
		bean.setStuAttend(list);

		stulist.add(bean);
		when(studentService.getStudentById("STD001")).thenReturn(bean);
		StudentBean studentlist = studentService.getStudentById("STD001");
		assertEquals("STD001", studentlist.getSid());
		verify(studentRepository, times(1)).findBySid("STD001");
	}

	@Test
	public void getAllStudentByParaTest() {
		List<StudentBean> list = new ArrayList<StudentBean>();
		list = studentService.getAllStudentByPara("STD001","Snow", "php");
		verify(studentRepository, times(1)).findDistinctBySidOrSnameContainingOrStuAttend_CnameContaining("STD001","Snow", "php");
	}
	
	@Test
	public void deleteTest() {
		studentService.delete("STD001");
		verify(studentRepository,times(1)).deleteById("STD001");;
	}

}
