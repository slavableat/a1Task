package com.example.demo.dto;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoginDTO {
    @CsvBindByPosition(position = 1)
    private String accountName;
    @CsvBindByPosition(position = 2)
    private Boolean isActive;
}
