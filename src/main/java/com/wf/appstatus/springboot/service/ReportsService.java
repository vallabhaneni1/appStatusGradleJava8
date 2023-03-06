package com.wf.appstatus.springboot.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.wf.appstatus.springboot.model.SoftwareUpdateReport;

public interface ReportsService {
	List<SoftwareUpdateReport> getAllReports();

	Page<SoftwareUpdateReport> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
