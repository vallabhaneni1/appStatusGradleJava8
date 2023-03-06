package com.wf.appstatus.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.wf.appstatus.springboot.model.ApplicationGroup;
import com.wf.appstatus.springboot.repository.ApplicationRepository;

@Service
public class ApplicationGroupServiceImpl implements ApplicationGroupService {

	@Autowired
	private ApplicationRepository applicationRepository;

	@Override
	public List<ApplicationGroup> getAllApplicationGroups() {
		return applicationRepository.findAll();
	}

	@Override
	public void saveApplicationGroup(ApplicationGroup applicationGroup) {
		this.applicationRepository.save(applicationGroup);
	}

	@Override
	public ApplicationGroup getApplicationGroupById(long id) {
		Optional<ApplicationGroup> optional = applicationRepository.findById(id);
		ApplicationGroup applicationGroup = null;
		if (optional.isPresent()) {
			applicationGroup = optional.get();
		} else {
			throw new RuntimeException(" ApplicationGroup not found for id :: " + id);
		}
		return applicationGroup;
	}

	@Override
	public void deleteApplicationGroupById(long id) {
		this.applicationRepository.deleteById(id);
	}

	@Override
	public Page<ApplicationGroup> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.applicationRepository.findAll(pageable);
	}
}
