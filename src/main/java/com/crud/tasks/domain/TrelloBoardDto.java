package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Generated;
import java.util.List;

@AllArgsConstructor
@Getter
public class TrelloBoardDto {
    private String name;
    private String id;
}