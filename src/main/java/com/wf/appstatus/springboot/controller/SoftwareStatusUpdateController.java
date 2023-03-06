package com.wf.appstatus.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.wf.appstatus.springboot.model.SoftwareUpdateStatus;
import com.wf.appstatus.springboot.service.SoftwareUpdateStatusService;

@Controller
public class SoftwareStatusUpdateController {

	@Autowired
	private SoftwareUpdateStatusService softwareUpdateStatusService;

	// display list of software
	@GetMapping("/softwareUpdateStatus/")
	public String viewSoftwareHomePage(Model model) {
		return findPaginated(1, "id", "asc", model);
	}

//	@GetMapping("/softwareUpdateStatus/showNewSoftwareForm")
//	public String showNewSoftwareForm(Model model) {
//		// create model attribute to bind form data
//		SoftwareUpdateStatus softwareUpdateStatus = new SoftwareUpdateStatus();
//		model.addAttribute("softwareUpdateStatus", softwareUpdateStatus);
//		return "softwareUpdateStatus_new";
//	}
//
//	@PostMapping("/softwareUpdateStatus/saveSoftwareUpdateStatus")
//	public String saveSoftwareUpdateStatus(
//			@ModelAttribute("softwareUpdateStatus") SoftwareUpdateStatus softwareUpdateStatus) {
//		// save softwareUpdateStatus to database
//		softwareUpdateStatusService.saveSoftwareUpdateStatus(softwareUpdateStatus);
//		return "redirect:/softwareUpdateStatus/";
//	}

//	@GetMapping("/softwareUpdateStatus/showFormForUpdate/{id}")
//	public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
//
//		// get softwareUpdateStatus from the service
//		SoftwareUpdateStatus softwareUpdateStatus = softwareUpdateStatusService.getSoftwareUpdateStatusById(id);
//
//		// set softwareUpdateStatus as a model attribute to pre-populate the form
//		model.addAttribute("softwareUpdateStatus", softwareUpdateStatus);
//		return "softwareUpdateStatus_update";
//	}
//	@GetMapping("/softwareUpdateStatus/showFormForUpdateApplicable/{id}")
//	public String showFormForUpdateApplicable(@PathVariable(value = "id") long id, Model model) {
//
//		// get softwareUpdateStatus from the service
//		SoftwareUpdateStatus softwareUpdateStatus = softwareUpdateStatusService.getSoftwareUpdateStatusById(id);
//		softwareUpdateStatus.setApplicable("No");
//		
//        // Update status and compeltion date to NULL if they are already set for any reason		
//		softwareUpdateStatus.setUpdateStatus(null);
//		softwareUpdateStatus.setCompeltedDate(null);
//		
//		softwareUpdateStatusService.saveSoftwareUpdateStatus(softwareUpdateStatus);
//		
//		// set softwareUpdateStatus as a model attribute to pre-populate the form
//		model.addAttribute("softwareUpdateStatus", softwareUpdateStatus);
//		return "softwareUpdateStatus_update";
//	}
//	@GetMapping("/softwareUpdateStatus/showFormForUpdateCompleted/{id}")
//	public String showFormForUpdateCompleted(@PathVariable(value = "id") long id, Model model) {
//
//		// get softwareUpdateStatus from the service
//		SoftwareUpdateStatus softwareUpdateStatus = softwareUpdateStatusService.getSoftwareUpdateStatusById(id);
//		softwareUpdateStatus.setUpdateStatus("Compelted");
//		
//		LocalDate dateObj = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//        String completionDate = dateObj.format(formatter);
//        softwareUpdateStatus.setCompeltedDate(completionDate);
//		
//        // Update to YES if it is already set to not applicable for any reason
//        softwareUpdateStatus.setApplicable("Yes");
//        
//		softwareUpdateStatusService.saveSoftwareUpdateStatus(softwareUpdateStatus);
//		
//		// set softwareUpdateStatus as a model attribute to pre-populate the form
//		model.addAttribute("softwareUpdateStatus", softwareUpdateStatus);
//		return "softwareUpdateStatus_update";
//	}
//
//	@GetMapping("/softwareUpdateStatus/deleteSoftwareUpdateStatus/{id}")
//	public String deleteSoftwareUpdateStatus(@PathVariable(value = "id") long id) {
//
//		// call delete softwareUpdateStatus method
//		this.softwareUpdateStatusService.deleteSoftwareUpdateStatusById(id);
//		return "redirect:/softwareUpdateStatus/";
//	}

	@GetMapping("/softwareUpdateStatus/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, Model model) {
		int pageSize = 5;

		Page<SoftwareUpdateStatus> page = softwareUpdateStatusService.findPaginated(pageNo, pageSize, sortField,
				sortDir);
		List<SoftwareUpdateStatus> listSoftwareUpdateStatus = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("listSoftwareUpdateStatus", listSoftwareUpdateStatus);
		return "softwareUpdateStatus_index";
	}
}
