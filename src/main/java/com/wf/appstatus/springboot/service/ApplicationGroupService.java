package com.wf.appstatus.springboot.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.wf.appstatus.springboot.model.ApplicationGroup;

public interface ApplicationGroupService {
	List<ApplicationGroup> getAllApplicationGroups();

	void saveApplicationGroup(ApplicationGroup applicationGroup);

	ApplicationGroup getApplicationGroupById(long id);

	void deleteApplicationGroupById(long id);

	Page<ApplicationGroup> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
