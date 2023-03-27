package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PostingDTO {
    private Long matDoc;
    private Boolean isAuthorized;
    private String accountName;
    private List<ItemDTO> items = new ArrayList<>();
    private String docDate;
    private String postingDate;
}
