package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.MailType;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    public static final String SUBJECT = "Tasks: Once a day email";

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AdminConfig adminConfig;

    @Scheduled(cron = "0 0 10 * * *")
    private void sendInformationEmail() {
        String correctWord;
        long size = taskRepository.count();

        if(size==1){
            correctWord= "task";
        }else{
            correctWord= "tasks";
        }
         //String wordCondition = size==1 ? "task": "tasks";
         simpleEmailService.send(new Mail(
                 adminConfig.getAdminMail(),
                 SUBJECT,
                 "Currently in database you got: " + size + correctWord),
                 MailType.DAILYDATAUPDATE
         );
        }
}

