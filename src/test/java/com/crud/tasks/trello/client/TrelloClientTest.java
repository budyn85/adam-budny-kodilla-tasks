package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.mapper.CreatedTrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTest {

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    @Before
    public  void init(){
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloAppToken()).thenReturn("test");
    }

    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException {
        //Given
        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());

        URI uri = new URI("http://test.com/members/kodillauser/boards?key=test&token=test&fields=name,id&lists=all");
        System.out.println(uri);
        when(restTemplate.getForObject(uri,TrelloBoardDto[].class)).thenReturn(trelloBoards);
        //When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();
        //Then
        assertEquals(1,fetchedTrelloBoards.size());
        assertEquals("test_id",fetchedTrelloBoards.get(0).getId());
        assertEquals("test_board",fetchedTrelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }
    @Test
    public void shouldCreateCard() throws URISyntaxException {
        // Given
        final TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Test task",
                "Test Description",
                "top",
                "test_id"
        );
        final URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20task&desc=Test%20Description&pos=top&idList=test_id");
        System.out.println(uri);
        final CreatedTrelloCardDto createdTrelloCard = new CreatedTrelloCardDto(
                "1",
                "Test task",
                "http://test.com"
                );
         when(restTemplate.postForObject(uri, null, CreatedTrelloCardDto.class)).thenReturn(createdTrelloCard);
         // When
        final CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);
        // Then
        assertEquals("1",newCard.getId());
        assertEquals("Test task", newCard.getName());
        assertEquals("http://test.com",newCard.getShortUrl());

        }
    @Test
    public void shouldReturnEmptyList() throws URISyntaxException {
        // Given
        final URI uri = new URI("http://test.com/members/adambudny/boards?key=test&token=test&fields=name,id&lists=all");
        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(null);
        // When
        final List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();
        // Then
        assertNotNull(fetchedTrelloBoards);
    }
}