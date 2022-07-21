package com.example.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.dao.CourseService;
import com.example.dao.StudentService;
import com.example.model.CourseBean;
import com.example.model.StudentBean;

import net.sf.jasperreports.engine.JRException;

@Controller
public class StudentController {
	@Autowired
	StudentService studentService;

	@Autowired
	CourseService courseService;

	@ModelAttribute("stubean")
	public StudentBean getStudentBean() {
		return new StudentBean();
	}

	@RequestMapping(value = "/setupaddstudent", method = RequestMethod.GET)
	public ModelAndView setupaddstudent(ModelMap model) {

		List<CourseBean> courselist = courseService.getAllCourse();
		model.addAttribute("courselist", courselist);

		StudentBean stubean = new StudentBean();
		List<StudentBean> stulist = studentService.getAllStudent();
		if (stulist.size() == 0) {
			stubean.setSid("STD001");
		} else {
			int temId = Integer.parseInt(stulist.get(stulist.size() - 1).getSid().substring(3)) + 1;
			String sid = String.format("STD%03d", temId);
			stubean.setSid(sid);
		}

		return new ModelAndView("STU001", "stubean", stubean);
	}

	@RequestMapping(value = "/addstudent", method = RequestMethod.POST)
	public String addsutdent(@ModelAttribute("stubean") @Validated StudentBean stubean, BindingResult bs,
			ModelMap model) {
		if (bs.hasErrors()) {
			return "STU001";
		}

		studentService.insertStudent(stubean);

		return "redirect:/STU003";
	}

	@RequestMapping(value = "/STU003", method = RequestMethod.GET)
	public String displayAllStudent(ModelMap model) {

		List<StudentBean> list = studentService.getAllStudent();
		model.addAttribute("studentlist", list);

		return "STU003";
	}

	@GetMapping("/STU003/report/pdf")
	public void generateReport(@PathVariable String pdf) throws JRException, IOException {
		studentService.exportReport(pdf);
	}

	@RequestMapping(value = "/prepareUpdateStudent", method = RequestMethod.GET)
	public ModelAndView prepareUpdateStudent(@RequestParam("sid") String sid, ModelMap model) {
		StudentBean astulist = studentService.getStudentById(sid);
		List<CourseBean> stuAttend = courseService.getAllCourse();
		model.addAttribute("courseList", stuAttend);

		return new ModelAndView("STU002", "stubean", astulist);

	}

	@RequestMapping(value = "/setupUpdateStudent", method = RequestMethod.GET)
	public ModelAndView setupUpdateStudent(@RequestParam("sid") String sid, ModelMap model) {
		StudentBean astulist = studentService.getStudentById(sid);
		List<CourseBean> stuAttend = courseService.getAllCourse();
		model.addAttribute("courseList", stuAttend);

		return new ModelAndView("STU002-01", "stubean", astulist);
	}

	@RequestMapping(value = "/updatestudent", method = RequestMethod.POST)
	public String updatestudent(@ModelAttribute("stubean") @Validated StudentBean stubean, BindingResult bs,
			ModelMap model) {

		if (bs.hasErrors()) {
			return "STU002";
		}
		/*
		 * if(stubean.equals(null)) { return "STU002"; }
		 */
		studentService.insertStudent(stubean);

		return "redirect:/STU003";
	}

	@RequestMapping(value = "/searchstudent", method = RequestMethod.GET)
	public String searchstudent(@RequestParam("sid") String sid, @RequestParam("sname") String sname,
			@RequestParam("course") String course, ModelMap model) {

		List<StudentBean> stulist = null;
		stulist = studentService.getAllStudentByPara(sid, sname, course);
		if (stulist.size() == 0) {
			stulist = studentService.getAllStudent();
		}
		model.addAttribute("studentlist", stulist);
		return "STU003";
	}

	@RequestMapping(value = "/deleteStudent", method = RequestMethod.GET)
	public String deleteStudent(@RequestParam("sid") String sid, ModelMap model) {
		studentService.delete(sid);
		return "redirect:/STU003";
	}

}