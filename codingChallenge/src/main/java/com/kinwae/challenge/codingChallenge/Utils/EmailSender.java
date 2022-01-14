package com.kinwae.challenge.codingChallenge.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailSender implements Runnable {
    Logger logger = LoggerFactory.getLogger(EmailSender.class);
    private String content;
    private final String SENDER ="noreply@kinwae.com";
    private String recipient;
    private String subject;

    public EmailSender(String content, String recipient, String subject) {
        this.content = content;
        this.recipient = recipient;
        this.subject = subject;
    }

    public void run() {
        logger.warn("Sending Email to" +recipient);
        sendEmail();
        logger.info("Email sent successfully");

    }
    public void sendEmail(){
       logger.info(content);
    }
}
