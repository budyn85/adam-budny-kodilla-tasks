package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;


@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private CompanyConfig companyConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    private TaskRepository taskRepository;

    public String buildTrelloCardEmail(String message) {
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888");
        context.setVariable("button", "Visit website");
        //context.setVariable("admin_name", adminConfig.getAdminName());
        //context.setVariable("company_name", adminConfig.getCompanyName());
        context.setVariable("goodbye_message", "Have a nice day. :)");
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        context.setVariable("preview_message", "Trello Card Created");
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildDailyMail(String message) {
        List<Task> taskList = taskRepository.findAll();

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("is_schedule_message", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("goodbye_message", "Have a nice day. :)");
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("schedule_message", "Schedule message");
        context.setVariable("task_list", taskList);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

}
