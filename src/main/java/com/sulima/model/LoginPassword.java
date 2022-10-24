package com.sulima.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class LoginPassword {

    @NotBlank(message = "Логин не заполнен.")
    @Size(min = 4, message = "Логин. Минимум 4 знака.")
    private String login;

    @NotBlank(message = "Пароль. Не заполнен.")
    private String pass;
}