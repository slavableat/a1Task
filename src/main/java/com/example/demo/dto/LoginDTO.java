package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoginDTO {
    private String applicationTitle;
    private String accountName;
    private Boolean isActive;
    private String jobTitle;
    private String departmentTitle;
}
