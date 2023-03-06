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

import com.wf.appstatus.springboot.model.ApplicationGroup;
import com.wf.appstatus.springboot.service.ApplicationGroupService;

@Controller
public class ApplicationGroupController {

	@Autowired
	private ApplicationGroupService applicationGroupService;

	// display list of applicationGroup
	@GetMapping("/applicationGroup/")
	public String viewApplicationGroupHomePage(Model model) {
		return findPaginated(1, "applicationGroupName", "asc", model);
	}

	@GetMapping("/applicationGroup/showNewApplicationGroupForm")
	public String showNewApplicationGroupForm(Model model) {
		// create model attribute to bind form data
		ApplicationGroup applicationGroup = new ApplicationGroup();
		model.addAttribute("applicationGroup", applicationGroup);
		return "application_group_new";
	}

	@PostMapping("/applicationGroup/saveApplicationGroup")
	public String saveApplicationGroup(@ModelAttribute("applicationGroup") ApplicationGroup applicationGroup) {
		// save applicationGroup to database
		applicationGroupService.saveApplicationGroup(applicationGroup);
		return "redirect:/applicationGroup/";
	}

	@GetMapping("/applicationGroup/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

		// get applicationGroup from the service
		ApplicationGroup applicationGroup = applicationGroupService.getApplicationGroupById(id);

		// set applicationGroup as a model attribute to pre-populate the form
		model.addAttribute("applicationGroup", applicationGroup);
		return "application_group_update";
	}

	@GetMapping("/applicationGroup/deleteApplicationGroup/{id}")
	public String deleteApplicationGroup(@PathVariable(value = "id") long id) {

		// call delete applicationGroup method
		this.applicationGroupService.deleteApplicationGroupById(id);
		return "redirect:/applicationGroup/";
	}

	@GetMapping("/applicationGroup/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, Model model) {
		int pageSize = 10;

		Page<ApplicationGroup> page = applicationGroupService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<ApplicationGroup> listApplicationGroups = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("listApplicationGroups", listApplicationGroups);
		return "application_group_index";
	}
}
