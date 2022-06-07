 package in.ashokit.utility;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtility {

	@Autowired
	private JavaMailSender javaMailSender;
	
	private Logger logger = LoggerFactory.getLogger(EmailUtility.class);

	public boolean sendEmail(String to, String subject, String body) {

		boolean isSent = false;

		try {

			MimeMessage mimeMessage = javaMailSender.createMimeMessage();

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(body, true);

			javaMailSender.send(mimeMessage);

			isSent = true;

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} // catch

		return isSent;
		
	}// sendEmail(-,-,-)
}// class
