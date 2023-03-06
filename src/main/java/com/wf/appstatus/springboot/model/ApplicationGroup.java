package com.wf.appstatus.springboot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "applicationGroup")
public class ApplicationGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "application_group_name")
	private String applicationGroupName;

	@Column(name = "application_group_email")
	private String applicationGroupEmail;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getApplicationGroupName() {
		return applicationGroupName;
	}

	public void setApplicationGroupName(String applicationGroupName) {
		this.applicationGroupName = applicationGroupName;
	}

	public String getApplicationGroupEmail() {
		return applicationGroupEmail;
	}

	public void setApplicationGroupEmail(String applicationGroupEmail) {
		this.applicationGroupEmail = applicationGroupEmail;
	}

}
