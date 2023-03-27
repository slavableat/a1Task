package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ItemDTO {
    private String materialDescription;
    private Long quantity;
    private String BUn;
    private String crcy;
    private Double amount;
    private Long ordinalNumber;
}
