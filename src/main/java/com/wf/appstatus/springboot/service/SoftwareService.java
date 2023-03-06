package com.wf.appstatus.springboot.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.wf.appstatus.springboot.model.Software;

public interface SoftwareService {
	List<Software> getAllSoftware();

	void saveSoftware(Software software);

	Software getSoftwareById(long id);

	void deleteSoftwareById(long id);

	Page<Software> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
