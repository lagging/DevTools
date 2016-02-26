package com.securitymanager.service;

import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class EmailService {
	public void sendMail(String mailReceiver , String tempPassword){
		String username="shubham.iit.bhu@gmail.com";
		String password="kjfhv";
	    Properties props = System.getProperties();
	    props.put("mail.smtp.starttls.enable", "true"); // added this line
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.user", username);
	    props.put("mail.smtp.password", password);
	    props.put("mail.smtp.port", "25");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtps.**ssl.enable", "false");
        props.setProperty("mail.smtps.**ssl.required", "false");

        

	    Session session = Session.getInstance(props,null);
	    MimeMessage message = new MimeMessage(session);
	    // Create the email addresses involved
	    try {
	        InternetAddress from = new InternetAddress(username);
	        message.setSubject("Vault Admin Password Updated");
	        message.setFrom(from);
	        message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(mailReceiver));

	        Multipart multipart = new MimeMultipart("alternative");
	        BodyPart messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setText("Your new password is : "+ tempPassword);

	        // Add the text part to the multipart
	        multipart.addBodyPart(messageBodyPart);
	        message.setContent(multipart);

	        Transport transport = session.getTransport("smtp");
	        transport.connect("smtp.gmail.com", username, password);
	        transport.sendMessage(message, message.getAllRecipients());


	    } catch (AddressException e) {
	        e.printStackTrace();
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	}
	}