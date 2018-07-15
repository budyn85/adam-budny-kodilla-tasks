package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTestSuite {

    private static List<TrelloListDto> trelloListDto;
    private static List<TrelloList> trelloList;
    private static List<TrelloBoardDto> trelloBoardDto;
    private static List<TrelloBoard> trelloBoard;

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void testMapToList() {
        // Given
        List<TrelloListDto> givenList = trelloListDto;
        List<TrelloList> expectedList = trelloList;

        // When
        List<TrelloList> mapResult = trelloMapper.mapToList(givenList);

        // Then
        assertEquals(expectedList, mapResult);
    }
    @Test
    public void testMapToListDto() {
        // Given
        List<TrelloList> givenList = trelloList;
        List<TrelloListDto> expectedList = trelloListDto;

        // When
        List<TrelloListDto> mapResult = trelloMapper.mapToListDto(givenList);

        // Then
        assertEquals(expectedList, mapResult);
    }
    @Test
    public void testMapToBoards() {
        // Given
        List<TrelloBoardDto> givenList = trelloBoardDto;
        List<TrelloBoard> expectedList = trelloBoard;

        // When
        List<TrelloBoard> mapResult = trelloMapper.mapToBoards(givenList);

        // Then
        assertEquals(expectedList, mapResult);
    }
    @Test
    public void testMapToBoardsDto() {
        // Given
        List<TrelloBoard> givenList = trelloBoard;
        List<TrelloBoardDto> expectedList = trelloBoardDto;

        // When
        List<TrelloBoardDto> mapResult = trelloMapper.mapToBoardDto(givenList);

        // Then
        assertEquals(expectedList, mapResult);
    }
    @Test
    public void testMapToCardDto() {
        // Given
        TrelloCard givenCard1 = new TrelloCard("Name1", "Description1", "Position1", "ListId1");
        TrelloCardDto expectedCardDto1 = new TrelloCardDto("Name1", "Description1", "Position1", "ListId1");
        TrelloCard givenCard2 = new TrelloCard("", "", "", "");
        TrelloCardDto expectedCardDto2 = new TrelloCardDto("", "", "", "");
        TrelloCard givenCard3 = new TrelloCard(null, null, null, null);
        TrelloCardDto expectedCardDto3 = new TrelloCardDto(null, null, null, null);

        // When
        TrelloCardDto mapResult1 = trelloMapper.mapToCardDto(givenCard1);
        TrelloCardDto mapResult2 = trelloMapper.mapToCardDto(givenCard2);
        TrelloCardDto mapResult3 = trelloMapper.mapToCardDto(givenCard3);

        // Then
        assertEquals(expectedCardDto1, mapResult1);
        assertEquals(expectedCardDto2, mapResult2);
        assertEquals(expectedCardDto3, mapResult3);
        assertThat(mapResult3,is(expectedCardDto3));
        }
    @Test
    public void testMapToCard() {
        // Given
        TrelloCardDto givenCardDto1 = new TrelloCardDto("Name1", "Description1", "Position1", "ListId1");
        TrelloCard expectedCard1 = new TrelloCard("Name1", "Description1", "Position1", "ListId1");
        TrelloCardDto givenCardDto2 = new TrelloCardDto("", "", "", "");
        TrelloCard expectedCard2 = new TrelloCard("", "", "", "");
        TrelloCardDto givenCardDto3 = new TrelloCardDto(null, null, null, null);
        TrelloCard expectedCard3 = new TrelloCard(null, null, null, null);

        // When
        TrelloCard mapResult1 = trelloMapper.mapToCard(givenCardDto1);
        TrelloCard mapResult2 = trelloMapper.mapToCard(givenCardDto2);
        TrelloCard mapResult3 = trelloMapper.mapToCard(givenCardDto3);

         // Then
        assertEquals(expectedCard1, mapResult1);
        assertEquals(expectedCard2, mapResult2);
        assertEquals(expectedCard3, mapResult3);
    }
}

