package com.crud.tasks.controller;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/trello")
public class TrelloController {

    @Autowired
    private TrelloClient trelloClient;

//    @Autowired
//    private TrelloBoardDto trelloBoardDto;

 //   @RequestMapping(method = RequestMethod.GET, value = "/boards")
//    public void getTrelloBoards() {
//            final List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();
//            trelloBoards.stream()
//                    .filter(trelloBoardDto -> trelloBoardDto.getName().contains("Kodilla"))
//                    .filter(trelloBoardDto -> !trelloBoardDto.getId().isEmpty())
//                    .forEach(trelloBoardDto -> System.out.println(trelloBoardDto.getId() + " "
//                            + trelloBoardDto.getName()));
//        }
    // GET request

    @RequestMapping(method = RequestMethod.GET, value = "getTrelloboards")
    public void getTrelloBoards() {
    List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

trelloBoards.forEach(trelloBoardDto -> {

    System.out.println(trelloBoardDto.getName() + " - " + trelloBoardDto.getId());

    System.out.println("This board contains lists: ");

    trelloBoardDto.getLists().forEach(trelloList ->
            System.out.println(trelloList.getName() + " - " + trelloList.getId() + " - " + trelloList.isClosed()));
});
    }
}