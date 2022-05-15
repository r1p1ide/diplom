package org.example.diplom.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChangePasswordDto {

    @NotNull
    private String login;

    @NotNull
    private String oldPassword;

    @NotNull
    private String newPassword;

    @NotNull
    private String repeatPassword;
}
