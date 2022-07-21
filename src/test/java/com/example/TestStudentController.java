package com.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.dao.StudentRepository;
import com.example.dao.StudentService;
import com.example.model.CourseBean;
import com.example.model.StudentBean;
import com.example.model.UserBean;

@SpringBootTest
@AutoConfigureMockMvc
public class TestStudentController {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	StudentService studentService;

	@MockBean
	StudentRepository studentRepository;

	// testing setupaddstudent method
	@Test
	public void setupaddstudentTest() throws Exception {
		this.mockMvc.perform(get("/setupaddstudent")).andExpect(status().isOk()).andExpect(view().name("STU001"))
				.andExpect(model().attributeExists("stubean"));
	}

	// testing addstudentValidate method
	@Test
	public void addstudentValidateTest() throws Exception {
		this.mockMvc.perform(post("/addstudent")).andExpect(status().isOk()).andExpect(view().name("STU001"));
	}

	// testing addstudentValidate method
	@Test
	public void addstudentEqualTest() throws Exception {
		//StudentBean stubean = new StudentBean();
		this.mockMvc.perform(post("/addstudent"))
		.andExpect(status().isOk())
		.andExpect(view().name("STU001"));
	}

	// testing addstudent method
	@Test
	public void addstudenttest() throws Exception {
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

		this.mockMvc.perform(post("/addstudent").flashAttr("stubean", bean)).andExpect(status().is(302))
				.andExpect(redirectedUrl("/STU003"));
	}

	// testing display all student
	@Test
	public void displayAllStudentTest() throws Exception {
		this.mockMvc.perform(get("/STU003")).andExpect(status().isOk()).andExpect(view().name("STU003"))
				.andExpect(model().attributeExists("studentlist"));
	}

	// testing prepareUpdateStudent method
	@Test
	public void prepareUpdateStudentTest() throws Exception {

		List<CourseBean> list = new ArrayList<CourseBean>();

		CourseBean cbean = new CourseBean();
		cbean.setCid("C001");
		cbean.setCname("java");

		CourseBean cbean1 = new CourseBean();
		cbean1.setCid("C002");
		cbean1.setCname("php");

		list.add(cbean);
		list.add(cbean1);

		StudentBean stubean = new StudentBean();
		stubean.setSid("STD001");
		stubean.setSname("Theingi");
		stubean.setSgender("female");
		stubean.setSdob("10.2.1999");
		stubean.setSphone("09999999");
		stubean.setEducation("degree");
		stubean.setStuAttend(list);

		when(studentService.getStudentById("STD001")).thenReturn(stubean);
		this.mockMvc.perform(get("/prepareUpdateStudent").param("sid", "STD001")).andExpect(status().isOk())
				.andExpect(view().name("STU002")).andExpect(model().attributeExists("stubean"));

	}

	// testing setupUpdateStudent method
	@Test
	public void setupUpdateStudentTest() throws Exception {

		List<CourseBean> list = new ArrayList<CourseBean>();

		CourseBean cbean = new CourseBean();
		cbean.setCid("C001");
		cbean.setCname("java");

		CourseBean cbean1 = new CourseBean();
		cbean1.setCid("C002");
		cbean1.setCname("php");

		list.add(cbean);
		list.add(cbean1);

		StudentBean stubean = new StudentBean();
		stubean.setSid("STD001");
		stubean.setSname("Theingi");
		stubean.setSgender("female");
		stubean.setSdob("10.2.1999");
		stubean.setSphone("09999999");
		stubean.setEducation("degree");
		stubean.setStuAttend(list);

		when(studentService.getStudentById("STD001")).thenReturn(stubean);
		this.mockMvc.perform(get("/setupUpdateStudent").param("sid", "STD001")).andExpect(status().isOk())
				.andExpect(view().name("STU002-01")).andExpect(model().attributeExists("stubean"));

	}

	// testing updatestudent method
	@Test
	public void updatestudentValidateTest() throws Exception {
		this.mockMvc.perform(post("/updatestudent")).andExpect(status().isOk()).andExpect(view().name("STU002"));
	}

	// testing updatestudent method
	@Test
	public void updatestudentEqualTest() throws Exception {
		this.mockMvc.perform(post("/updatestudent")).andExpect(status().isOk()).andExpect(view().name("STU002"));
	}

	// testing updatestudent method
	@Test
	public void updatestudentTest() throws Exception {
		List<CourseBean> list = new ArrayList<CourseBean>();

		CourseBean cbean = new CourseBean();
		cbean.setCid("C001");
		cbean.setCname("java");

		CourseBean cbean1 = new CourseBean();
		cbean1.setCid("C002");
		cbean1.setCname("php");

		list.add(cbean);
		list.add(cbean1);

		StudentBean stubean = new StudentBean();
		stubean.setSid("STD001");
		stubean.setSname("Theingi");
		stubean.setSgender("female");
		stubean.setSdob("10.2.1999");
		stubean.setSphone("09999999");
		stubean.setEducation("degree");
		stubean.setStuAttend(list);

		this.mockMvc.perform(post("/updatestudent").flashAttr("stubean", stubean)).andExpect(status().is(302))
				.andExpect(redirectedUrl("/STU003"));
	}

	// testing searchstudent method
	@Test
	public void searchstudentTest() throws Exception {
		this.mockMvc.perform(get("/searchstudent").param("sid", "STD001").param("sname", "snow").param("course", "php"))
				.andExpect(status().isOk()).andExpect(model().attributeExists("studentlist"))
				.andExpect(view().name("STU003"));
	}

	// testing deleteStudent method
	@Test
	public void deleteStudentTest() throws Exception {
		this.mockMvc.perform(get("/deleteStudent").param("sid", "STD001")).andExpect(status().is(302))
				.andExpect(redirectedUrl("/STU003"));

	}

}
