package com.wf.appstatus.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.wf.appstatus.springboot.model.Software;
import com.wf.appstatus.springboot.repository.SoftwareRepository;

@Service
public class SoftwareServiceImpl implements SoftwareService {

	@Autowired
	private SoftwareRepository softwareRepository;

	@Override
	public List<Software> getAllSoftware() {
		return softwareRepository.findAll();
	}

	@Override
	public void saveSoftware(Software software) {
		this.softwareRepository.save(software);
	}

	@Override
	public Software getSoftwareById(long id) {
		Optional<Software> optional = softwareRepository.findById(id);
		Software software = null;
		if (optional.isPresent()) {
			software = optional.get();
		} else {
			throw new RuntimeException(" Software not found for id :: " + id);
		}
		return software;
	}

	@Override
	public void deleteSoftwareById(long id) {
		this.softwareRepository.deleteById(id);
	}

	@Override
	public Page<Software> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.softwareRepository.findAll(pageable);
	}
}
