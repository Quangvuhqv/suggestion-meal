package com.process.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
@Getter
@Setter
@ToString
public class MenuDTO {
    private Long id;
    private LocalDate effectDate;
    private Long mealType;
    private String mealName;
    private String menu;
    private Integer status;
}
