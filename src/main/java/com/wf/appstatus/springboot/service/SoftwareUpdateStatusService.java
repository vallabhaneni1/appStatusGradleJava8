package com.wf.appstatus.springboot.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.wf.appstatus.springboot.model.SoftwareUpdateStatus;

public interface SoftwareUpdateStatusService {
	List<SoftwareUpdateStatus> getAllSoftwareUpdateStatus();

	SoftwareUpdateStatus saveSoftwareUpdateStatus(SoftwareUpdateStatus softwareUpdateStatus);

	SoftwareUpdateStatus getSoftwareUpdateStatusById(long id);

	void deleteSoftwareUpdateStatusById(long id);

	Page<SoftwareUpdateStatus> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
