package com.wf.appstatus.springboot.controller;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wf.appstatus.springboot.model.Software;
import com.wf.appstatus.springboot.service.EmailService;
import com.wf.appstatus.springboot.service.SoftwareService;

@RestController
public class EmailController {

	@Autowired
	EmailService emailService;
	@Autowired
	private SoftwareService softwareService;

	@RequestMapping(value = "/sendemail")
	public String sendEmail() {

		// sendEmail
		emailService.sendEmail();

		// sendEmailWithAttachment
		// emailService.sendEmailWithAttachment();

		return "redirect:/software/";
	}

	@GetMapping("/sendemail/processEmailForm/{id}")
	public String processEmailForm(@PathVariable(value = "id") long id, Model model) {

		// get employee from the service
		Software software = softwareService.getSoftwareById(id);

		try {
			emailService.sendEmail(software);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/software/";
	}
}
