package com.wf.appstatus.springboot.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.data.domain.Page;

import com.wf.appstatus.springboot.model.EmailRecord;
import com.wf.appstatus.springboot.model.Software;

public interface EmailService {

	void sendEmail();

	void sendEmailWithAttachment() throws MessagingException, IOException;

	List<EmailRecord> getAllEmailRecords();

	void saveEmailRecord(EmailRecord emailRecord);

	EmailRecord getEmailRecordById(long id);

	void deleteEmailRecordById(long id);

	Page<EmailRecord> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

	void sendEmail(Software software) throws MessagingException, IOException;

}
