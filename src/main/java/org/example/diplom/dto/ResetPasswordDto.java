package org.example.diplom.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ResetPasswordDto {

    @NotNull
    private String login;
}
