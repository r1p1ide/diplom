package org.example.diplom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.diplom.dto.UserDto;

import javax.persistence.*;

@Entity
@Table(name = "user_info")
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Integer id;

    @JsonIgnore
    @Column(name = "auth_id")
    private Integer auth_id;

    @Column(name = "login")
    private String login;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "role")
    private String role;

    public void setAuth_id(Integer auth_id) {
        this.auth_id = auth_id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserInfo(UserDto userDto) {
        this.login = userDto.getLogin();
        this.firstName = userDto.getFirstName();
        this.lastName = userDto.getLastName();
        this.email = userDto.getEmail();
        this.phone = userDto.getPhone();
        this.role = userDto.getRole();
    }

}
