package org.example.diplom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "token")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Integer id;


    @Column(name = "auth_id")
    @JsonIgnore
    private Integer auth_id;

    @Column(name = "token")
    private String token;
}
