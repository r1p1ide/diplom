package org.example.diplom.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthWithoutCodeDto {

    @NotNull
    private String login;

    @NotNull
    private String password;

    @JsonIgnore
    private String code;
}
