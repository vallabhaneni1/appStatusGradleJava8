package com.wf.appstatus.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppStatusController {

	// display list of options for employees software management system
	@GetMapping("/")
	public String viewHomePage() {
		return "app_status_index";
	}
}
