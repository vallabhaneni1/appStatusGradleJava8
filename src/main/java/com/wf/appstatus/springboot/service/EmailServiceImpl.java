package com.wf.appstatus.springboot.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.wf.appstatus.springboot.model.ApplicationGroup;
import com.wf.appstatus.springboot.model.EmailRecord;
import com.wf.appstatus.springboot.model.Software;
import com.wf.appstatus.springboot.model.SoftwareUpdateStatus;
import com.wf.appstatus.springboot.repository.EmailRecordRepository;

@Service
public class EmailServiceImpl implements EmailService {

	private static final String TO_EMAIL1 = "kshivva@gmail.com";
	// private static final String TO_EMAIL2 = "kshivva@gmail.com";
	// private static final String TO_EMAIL3 = "kshivva@gmail.com";

	@Autowired
	private EmailRecordRepository emailRecordRepository;
	@Autowired
	private ApplicationGroupService applicationGroupService;
	@Autowired
	private SoftwareUpdateStatusService softwareUpdateStatusService;
	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendEmail() {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(TO_EMAIL1);
		// msg.setTo(TO_EMAIL1, TO_EMAIL2,TO_EMAIL3);
		msg.setSubject("Testing from Spring Boot");
		msg.setText("Hello World \n Spring Boot Email");
		javaMailSender.send(msg);
	}

	@Override
	public void sendEmail(Software software) throws MessagingException, IOException {

		for (ApplicationGroup applicationGroup : applicationGroupService.getAllApplicationGroups()) {

			// save the basic status record
			SoftwareUpdateStatus softwareUpdateStatus = new SoftwareUpdateStatus();
			softwareUpdateStatus.setApplicationGroupId(applicationGroup.getId());
			softwareUpdateStatus.setSoftwareId(software.getId());
			softwareUpdateStatus = softwareUpdateStatusService.saveSoftwareUpdateStatus(softwareUpdateStatus);

			// send email
			MimeMessage msg = javaMailSender.createMimeMessage();
			// true = multipart message
			MimeMessageHelper helper = new MimeMessageHelper(msg, true);
			helper.setTo(applicationGroup.getApplicationGroupEmail()); // TO_EMAIL1); // TODO replace TO_EMAIL with
																		// applicationGroup.getApplicationGroupEmail()

			helper.setSubject("Software Update Notice: " + software.getSoftwareName());

			StringBuilder emailContent = new StringBuilder();
			emailContent.append("\n").append("<BR>")
					.append("Hello <B>" + applicationGroup.getApplicationGroupName() + "</B> team,");
			emailContent.append("\n").append("<BR>");
			emailContent.append("\n").append("<BR>").append("<B>Software update notice</B>");
			emailContent.append("\n").append("<BR>");
			emailContent.append("\n").append("<BR>")
					.append("Please update the following software for your application.");
			emailContent.append("\n").append("<BR>");
			emailContent.append("\n").append("<BR>").append("Software Name: ").append(software.getSoftwareName());
			emailContent.append("\n").append("<BR>").append("Software Description: ")
					.append(software.getSoftwareDesc());
			emailContent.append("\n").append("<BR>").append("Software Version: ").append(software.getSoftwareVer());
			emailContent.append("\n").append("<BR>").append("Update by : ").append(software.getDueDate());
			emailContent.append("\n").append("<BR>");
			emailContent.append("\n").append("<BR>")
					.append("Click the below link once the software update is complete.");
			emailContent.append("\n").append("<BR>");
			emailContent.append("\n").append("<BR>")
					.append("<a href=\"http://localhost:8080/softwareUpdateStatus/showFormForUpdateCompleted/"
							+ softwareUpdateStatus.getId() + "\" >Software Update Complete</a>");
			emailContent.append("\n").append("<BR>");
			emailContent.append("\n").append("<BR>").append("Click the below link if not applicable.");
			emailContent.append("\n").append("<BR>");
			emailContent.append("\n").append("<BR>")
					.append("<a href=\"http://localhost:8080/softwareUpdateStatus/showFormForUpdateApplicable/"
							+ softwareUpdateStatus.getId() + "\" >Software Update Not Applicable</a>");

			helper.setText(emailContent.toString(), true);
			javaMailSender.send(msg);
		}
	}

	@Override
	public void sendEmailWithAttachment() throws MessagingException, IOException {
		MimeMessage msg = javaMailSender.createMimeMessage();
		// true = multipart message
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setTo(TO_EMAIL1);
		helper.setSubject("Testing from Spring Boot");
		// default = text/plain
		// helper.setText("Check attachment for image!");
		// true = text/html
		helper.setText("<h1>Check attachment for image!</h1>", true);
		// hard coded a file path
		// FileSystemResource file = new FileSystemResource(new
		// File("path/android.png"));
		helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));
		javaMailSender.send(msg);
	}

	@Override
	public List<EmailRecord> getAllEmailRecords() {
		return emailRecordRepository.findAll();
	}

	@Override
	public void saveEmailRecord(EmailRecord emailRecord) {
		this.emailRecordRepository.save(emailRecord);
	}

	@Override
	public EmailRecord getEmailRecordById(long id) {
		Optional<EmailRecord> optional = emailRecordRepository.findById(id);
		EmailRecord emailRecord = null;
		if (optional.isPresent()) {
			emailRecord = optional.get();
		} else {
			throw new RuntimeException(" EmailRecord not found for id :: " + id);
		}
		return emailRecord;
	}

	@Override
	public void deleteEmailRecordById(long id) {
		this.emailRecordRepository.deleteById(id);
	}

	@Override
	public Page<EmailRecord> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.emailRecordRepository.findAll(pageable);
	}

}
