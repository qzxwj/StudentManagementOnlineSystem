package com.example.studentmanagementonlinesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private Integer id;
    private String name;
    private String type;
    private String message;
}
