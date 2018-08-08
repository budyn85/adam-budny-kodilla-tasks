package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailCreatorService mailCreatorService;

    public void send( Mail mail, MailType mailType) {
        LOGGER.info("Starting email preparation...");
        try {
            if (mailType.equals(MailType.DAILYDATAUPDATE)) {
                javaMailSender.send(createMimeMessage(mail, MailType.DAILYDATAUPDATE));
            } else {
                javaMailSender.send(createMimeMessage(mail, MailType.STANDARD));
            }
            LOGGER.info("Email has been sent");
        }catch(MailException e) {
            LOGGER.error("Failed to process email sending: ", e.getMessage(), e);
        }
    }
    private MimeMessagePreparator createMimeMessage(final Mail mail, final MailType mailType) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(" adam.kodilla@gmail.com");
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());

            switch (mailType) {
                case STANDARD:
                    messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()), true);
                    break;
                case DAILYDATAUPDATE:
                    messageHelper.setText(mailCreatorService.buildDailyMail(mail.getMessage()), true);
                    break;
            }
        };
    }
    private MimeMessagePreparator createDailyDataUpdate(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(" adam.kodilla@gmail.com");
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
        };
    }

//    private MimeMessagePreparator createMimeMessage(final Mail mail) {
//        return mimeMessage -> {
//            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
//            messageHelper.setFrom("adam.kodilla@gmail.com");
//            messageHelper.setTo(mail.getMailTo());
//            messageHelper.setSubject(mail.getSubject());
//            messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()), true);
//        };
    private SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()));
        return mailMessage;
    }
}

