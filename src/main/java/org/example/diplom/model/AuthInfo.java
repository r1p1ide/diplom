package org.example.diplom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.diplom.dto.AuthDto;

import javax.persistence.*;

@Entity
@Table(name = "auth_info")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Integer id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    //private String code;

}
