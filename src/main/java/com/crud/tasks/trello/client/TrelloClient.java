package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;
    @Value("${trello.app.key}")
    private String trelloAppKey;
    @Value("${trello.app.token}")
    private String trelloAppToken;
    @Value("${trello.app.username}")
    private String trelloUserName;

    public List<TrelloBoardDto> getTrelloBoards() {
             final TrelloBoardDto[] boardsResponse = restTemplate.getForObject(buildURIForGetBoards(), TrelloBoardDto[].class);
        return Optional.ofNullable(boardsResponse)
                .map(Arrays::asList)
                .orElseGet(Collections::emptyList);
    }
    private URI buildURIForGetBoards() {
        return UriComponentsBuilder.fromHttpUrl(String.format("%s/members/%s/boards", trelloApiEndpoint, trelloUserName))
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloAppToken)
                .queryParam("fields", "name,id")
                             .queryParam("lists", "all")
                            .build().encode().toUri();
           }

}
