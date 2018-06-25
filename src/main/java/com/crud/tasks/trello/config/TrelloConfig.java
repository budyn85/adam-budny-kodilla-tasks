package com.crud.tasks.trello.config;

import lombok.Getter;
import lombok.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class TrelloConfig {

    @org.springframework.beans.factory.annotation.Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;
    @org.springframework.beans.factory.annotation.Value("${trello.app.key}")
    private String trelloAppKey;
    @org.springframework.beans.factory.annotation.Value("${trello.app.token}")
    private String trelloAppToken;

    public Object getTrelloUserName() {
        return trelloUserName;
    }
}
