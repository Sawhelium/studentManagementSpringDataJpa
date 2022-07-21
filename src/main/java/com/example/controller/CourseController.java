package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.dao.CourseService;
import com.example.model.CourseBean;

@Controller
public class CourseController {
	@Autowired
	private CourseService courseService;

	@ModelAttribute("cbean")
	public CourseBean getCourseBean() {
		return new CourseBean();
	}

	@RequestMapping(value = "setupaddcourse", method = RequestMethod.GET)
	public ModelAndView setupaddcourse() {
		CourseBean cbean = new CourseBean();
		
		  List<CourseBean> courselist = courseService.getAllCourse();
		  if(courselist.size() == 0) {
			  cbean.setCid("C001");
		  }else {
			  int tempId = Integer.parseInt(courselist.get(courselist.size()-1).getCid().substring(1))+1;
			  String cid = String.format("C%03d", tempId);
			  cbean.setCid(cid);
		  }
		  
		return new ModelAndView("BUD003", "cbean", cbean);
	}

	@RequestMapping(value="/addcourse", method=RequestMethod.POST)
	public String addcourse(@ModelAttribute("cbean") CourseBean cbean, BindingResult bs, ModelMap model){
		if(bs.hasErrors()) {
			return "BUD003";
		}
		
		courseService.save(cbean);
		return "BUD003";
	}

}
