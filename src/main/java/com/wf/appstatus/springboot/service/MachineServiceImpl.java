package com.wf.appstatus.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.wf.appstatus.springboot.model.Machine;
import com.wf.appstatus.springboot.repository.MachineRepository;

@Service
public class MachineServiceImpl implements MachineService {

	@Autowired
	private MachineRepository machineRepository;

	@Override
	public List<Machine> getAllMachines() {
		return machineRepository.findAll();
	}

	@Override
	public void saveMachine(Machine machine) {
		this.machineRepository.save(machine);
	}

	@Override
	public Machine getMachineById(long id) {
		Optional<Machine> optional = machineRepository.findById(id);
		Machine machine = null;
		if (optional.isPresent()) {
			machine = optional.get();
		} else {
			throw new RuntimeException(" Machine not found for id :: " + id);
		}
		return machine;
	}

	@Override
	public void deleteMachineById(long id) {
		this.machineRepository.deleteById(id);
	}

	@Override
	public Page<Machine> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.machineRepository.findAll(pageable);
	}
}
