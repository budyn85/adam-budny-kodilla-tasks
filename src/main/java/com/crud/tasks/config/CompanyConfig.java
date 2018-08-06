package com.crud.tasks.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@ConfigurationProperties(prefix = "info.company")
public class CompanyConfig {

    //@Value("${info.company.name}")
    private String companyName;

    //@Value("${info.company.email")
    private String companyEmail;

   // @Value("${info.company.webside}")
    private String companyWebside;

    @Override
    public String toString() {
        return String.format("%s | %s | %s", companyName, companyEmail, companyWebside);
    }
}
