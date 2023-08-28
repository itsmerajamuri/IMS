package com.ims.service;

import com.ims.helperclass.Email;

public interface EmailService {

		// Method
		// To send a simple email
		String sendSimpleMail(Email details);

		// Method
		// To send an email with attachment
		String sendMailWithAttachment(Email details);
	}


