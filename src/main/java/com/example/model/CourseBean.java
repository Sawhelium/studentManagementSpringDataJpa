package com.example.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="attend")
public class CourseBean {
	@NotEmpty
	@Id
	private String cid;
	@NotEmpty
	private String cname;
	
	@ManyToMany(mappedBy = "stuAttend")
	private List<StudentBean> selectedCourse;

	public List<StudentBean> getSelectedCourse() {
		return selectedCourse;
	}
	public void setSelectedCourse(List<StudentBean> selectedCourse) {
		this.selectedCourse = selectedCourse;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	

}
