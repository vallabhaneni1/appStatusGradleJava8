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

import com.wf.appstatus.springboot.model.Software;
import com.wf.appstatus.springboot.service.SoftwareService;

@Controller
public class SoftwareController {

	@Autowired
	private SoftwareService softwareService;

	// display list of software
	@GetMapping("/software/")
	public String viewSoftwareHomePage(Model model) {
		return findPaginated(1, "softwareName", "asc", model);
	}

	@GetMapping("/software/showNewSoftwareForm")
	public String showNewSoftwareForm(Model model) {
		// create model attribute to bind form data
		Software software = new Software();
		model.addAttribute("software", software);
		return "software_new";
	}

	@PostMapping("/software/saveSoftware")
	public String saveSoftware(@ModelAttribute("software") Software software) {
		// save software to database
		softwareService.saveSoftware(software);
		return "redirect:/software/";
	}

	@GetMapping("/software/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

		// get software from the service
		Software software = softwareService.getSoftwareById(id);

		// set software as a model attribute to pre-populate the form
		model.addAttribute("software", software);
		return "software_update";
	}

	@GetMapping("/software/deleteSoftware/{id}")
	public String deleteSoftware(@PathVariable(value = "id") long id) {

		// call delete software method
		this.softwareService.deleteSoftwareById(id);
		return "redirect:/software/";
	}

	@GetMapping("/software/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, Model model) {
		int pageSize = 10;

		Page<Software> page = softwareService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Software> listSoftwares = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("listSoftwares", listSoftwares);
		return "software_index";
	}
}
