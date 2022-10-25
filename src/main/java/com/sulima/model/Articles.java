package com.sulima.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Articles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Заголовок не заполнен.")
    @Size(min = 7, message = "Заголовок. Минимум 7 знаков.")
    private String title;

    @NotBlank(message = "Интро не заполнен.")
    @Size(min = 15, message = "Интро. Минимум 15 знаков.")
    private String intro;

    @NotBlank(message = "Текст не заполнен.")
    @Size(min = 30, message = "Текст. Минимум 30 знаков.")
    private String text;

    private long date;
    private String author;

    @Transient
    private int count;
}
