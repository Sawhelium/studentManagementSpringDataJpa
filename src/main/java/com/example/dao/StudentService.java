package com.example.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.example.model.StudentBean;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class StudentService {
	@Autowired
	StudentRepository studentRepository;

	// add new student
	public void insertStudent(StudentBean stubean) {
		studentRepository.save(stubean);
	}

	// show all student
	public List<StudentBean> getAllStudent() {
		List<StudentBean> studentlist = (List<StudentBean>) studentRepository.findAll();
		return studentlist;
	}

	// report test
	public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
		
		String path = "C:\\Users\\user\\Desktop\\report";

		List<StudentBean> student = (List<StudentBean>) studentRepository.findAll();

		// load file and compile
		File file = ResourceUtils.getFile("classpath:student.jrxml");

		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(student);

		 Map<String,Object>  parameters=new HashMap<>();
		 parameters.put("createdBy", "Admin");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

		if (reportFormat.equalsIgnoreCase("html")) {
			JasperExportManager.exportReportToHtmlFile(jasperPrint,path+"\\student.html");
		}
		if (reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint,path+"\\student.pdf");
		}
		
		return "report generated in path:"+path;
	}

	// get student by student id
	public StudentBean getStudentById(String sid) {
		return studentRepository.findBySid(sid);
	}

	// get student by path parameter
	public List<StudentBean> getAllStudentByPara(String sid, String sname, String course) {
		String id = sid.isBlank() ? "@#$(" : sid;
		String name = sname.isBlank() ? "@#$(" : sname;
		String cou = course.isBlank() ? "@#$(" : course;
		return studentRepository.findDistinctBySidOrSnameContainingOrStuAttend_CnameContaining(id, name, cou);
	}

	// delete student data
	public void delete(String sid) {
		studentRepository.deleteById(sid);
	}

}
