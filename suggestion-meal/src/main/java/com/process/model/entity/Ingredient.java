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
@Table(name = "ingredient")
@Getter
@Setter
@ToString
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long ingredientType;
    private String name;
    private String description;
    private Integer status;
    public enum Status {
        ACTIVE(1),
        INACTIVE(0);
        public final Integer value;

        Status(int i) {
            value = i;
        }
    }


}
