package com.process.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "config")
@Getter
@Setter
@ToString
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long categoryId;
    private String name;
    private Integer status;
    private String value;
    private int orderBy;

    public enum CategoryType {
        MEAL_TYPE,
        INGREDIENT_TYPE
    }
}
