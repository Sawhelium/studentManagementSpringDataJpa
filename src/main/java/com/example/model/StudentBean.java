package com.example.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "student")
public class StudentBean {

	@Id
	@NotEmpty
	private String sid;
	@NotEmpty
	private String sname;
	@NotEmpty
	private String sgender;
	@NotEmpty
	private String sdob;
	@NotEmpty
	private String sphone;
	@NotEmpty
	private String education;

	@ManyToMany
	@JoinTable(
			name="attened_course",
			joinColumns = @JoinColumn(name="sid"),
			inverseJoinColumns = @JoinColumn(name = "cid")
	)
	private List<CourseBean> stuAttend;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getSgender() {
		return sgender;
	}

	public void setSgender(String sgender) {
		this.sgender = sgender;
	}

	public String getSdob() {
		return sdob;
	}

	public void setSdob(String sdob) {
		this.sdob = sdob;
	}

	public String getSphone() {
		return sphone;
	}

	public void setSphone(String sphone) {
		this.sphone = sphone;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public List<CourseBean> getStuAttend() {
		return stuAttend;
	}

	public void setStuAttend(List<CourseBean> stuAttend) {
		this.stuAttend = stuAttend;
	}

}
