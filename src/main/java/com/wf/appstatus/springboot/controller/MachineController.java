package com.wf.appstatus.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wf.appstatus.springboot.model.Machine;
import com.wf.appstatus.springboot.service.MachineService;

@Controller
public class MachineController {

	@Autowired
	private MachineService machineService;

	// display list of machine
	@GetMapping("/machine/")
	public String viewMachineHomePage(Model model) {
		return findPaginated(1, "machineName", "asc", model);
	}

	@GetMapping("/machine/showNewMachineForm")
	public String showNewMachineForm(Model model) {
		// create model attribute to bind form data
		Machine machine = new Machine();
		model.addAttribute("machine", machine);
		return "machine_new";
	}

	@PostMapping("/machine/saveMachine")
	public String saveMachine(@ModelAttribute("machine") Machine machine) {
		// save machine to database
		machineService.saveMachine(machine);
		return "redirect:/machine/";
	}

	@GetMapping("/machine/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

		// get machine from the service
		Machine machine = machineService.getMachineById(id);

		// set machine as a model attribute to pre-populate the form
		model.addAttribute("machine", machine);
		return "machine_update";
	}

	@GetMapping("/machine/deleteMachine/{id}")
	public String deleteMachine(@PathVariable(value = "id") long id) {

		// call delete machine method
		this.machineService.deleteMachineById(id);
		return "redirect:/machine/";
	}

	@GetMapping("/machine/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, Model model) {
		int pageSize = 10;

		Page<Machine> page = machineService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Machine> listMachines = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("listMachines", listMachines);
		return "machine_index";
	}
}
