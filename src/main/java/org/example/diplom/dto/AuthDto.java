package org.example.diplom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDto {

    @NotNull
    private String login;

    @NotNull
    private String password;
//
//    @NotNull
//    private String code;
}
