package com.wf.appstatus.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.wf.appstatus.springboot.model.ApplicationGroup;
import com.wf.appstatus.springboot.model.Software;
import com.wf.appstatus.springboot.model.SoftwareUpdateReport;
import com.wf.appstatus.springboot.model.SoftwareUpdateStatus;
import com.wf.appstatus.springboot.service.ApplicationGroupService;
import com.wf.appstatus.springboot.service.ReportsService;
import com.wf.appstatus.springboot.service.SoftwareService;
import com.wf.appstatus.springboot.service.SoftwareUpdateStatusService;

@Controller
public class ReportsController {

	@Autowired
	private ReportsService reportsService;
	@Autowired
	private SoftwareUpdateStatusService softwareUpdateStatusService;
	@Autowired
	private ApplicationGroupService applicationGroupService;
	@Autowired
	private SoftwareService softwareService;

	// display report
	@GetMapping("/reports/")
	public String viewSoftwareHomePage(Model model) {
		return findPaginated(1, "id", "asc", model);
	}

	// filter report
	@GetMapping("/reports/filter/")
	public String viewSoftwareHomePageFilter(Model model, @RequestParam("keyword") String keyword) {
		return findPaginatedFiltered(1, "id", "asc", keyword, model);
	}

	@GetMapping("/reports/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, Model model) {
		int pageSize = 5;

		Page<SoftwareUpdateReport> page = reportsService.findPaginated(pageNo, pageSize, sortField, sortDir);

		List<SoftwareUpdateReport> listReports = getAllReportList();

//		List<SoftwareUpdateReport> listReports = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("listReports", listReports);
		return "reports_index";
	}

	@GetMapping("/reports/filter/{pageNo}")
	public String findPaginatedFiltered(@PathVariable(value = "pageNo") int pageNo,
			@RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir,
			@RequestParam("keyword") String keyword, Model model) {
		int pageSize = 5;

		Page<SoftwareUpdateReport> page = reportsService.findPaginated(pageNo, pageSize, sortField, sortDir);

		List<SoftwareUpdateReport> listReports = getAllReportList().stream()
				.filter(k -> k.getSoftwareName().contains(keyword)).collect(Collectors.toList());

//		List<SoftwareUpdateReport> listReports = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("listReports", listReports);
		return "reports_index";
	}

	private List<SoftwareUpdateReport> getAllReportList() {

		List<SoftwareUpdateReport> listReports = new ArrayList<>();

		for (SoftwareUpdateStatus suStatus : softwareUpdateStatusService.getAllSoftwareUpdateStatus()) {

			SoftwareUpdateReport newSoftwareUpdateReport = new SoftwareUpdateReport();

			ApplicationGroup group = applicationGroupService.getApplicationGroupById(suStatus.getApplicationGroupId());
			newSoftwareUpdateReport.setApplicationGroupName(group.getApplicationGroupName());
			newSoftwareUpdateReport.setApplicationGroupEmail(group.getApplicationGroupEmail());

			Software soft = softwareService.getSoftwareById(suStatus.getSoftwareId());
			newSoftwareUpdateReport.setSoftwareName(soft.getSoftwareName());
			newSoftwareUpdateReport.setSoftwareDesc(soft.getSoftwareDesc());
			newSoftwareUpdateReport.setSoftwareVer(soft.getSoftwareVer());
			newSoftwareUpdateReport.setDueDate(soft.getDueDate());

			newSoftwareUpdateReport.setApplicable(suStatus.getApplicable());
			newSoftwareUpdateReport.setAppStatus(suStatus.getUpdateStatus());
			newSoftwareUpdateReport.setCompletedDate(suStatus.getCompletedDate());

			listReports.add(newSoftwareUpdateReport);
		}

		return listReports;
	}
}