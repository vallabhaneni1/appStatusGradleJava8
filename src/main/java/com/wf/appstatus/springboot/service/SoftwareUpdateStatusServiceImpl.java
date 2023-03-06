package com.wf.appstatus.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.wf.appstatus.springboot.model.SoftwareUpdateStatus;
import com.wf.appstatus.springboot.repository.SoftwareUpdateStatusRepository;

@Service
public class SoftwareUpdateStatusServiceImpl implements SoftwareUpdateStatusService {

	@Autowired
	private SoftwareUpdateStatusRepository softwareUpdateStatusRepository;

	@Override
	public List<SoftwareUpdateStatus> getAllSoftwareUpdateStatus() {
		return softwareUpdateStatusRepository.findAll();
	}

	@Override
	public SoftwareUpdateStatus saveSoftwareUpdateStatus(SoftwareUpdateStatus software) {
		return this.softwareUpdateStatusRepository.save(software);
	}

	@Override
	public SoftwareUpdateStatus getSoftwareUpdateStatusById(long id) {
		Optional<SoftwareUpdateStatus> optional = softwareUpdateStatusRepository.findById(id);
		SoftwareUpdateStatus software = null;
		if (optional.isPresent()) {
			software = optional.get();
		} else {
			throw new RuntimeException(" SoftwareUpdateStatus not found for id :: " + id);
		}
		return software;
	}

	@Override
	public void deleteSoftwareUpdateStatusById(long id) {
		this.softwareUpdateStatusRepository.deleteById(id);
	}

	@Override
	public Page<SoftwareUpdateStatus> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.softwareUpdateStatusRepository.findAll(pageable);
	}
}
