package com.wf.appstatus.springboot.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.wf.appstatus.springboot.model.Machine;

public interface MachineService {
	List<Machine> getAllMachines();

	void saveMachine(Machine machine);

	Machine getMachineById(long id);

	void deleteMachineById(long id);

	Page<Machine> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
