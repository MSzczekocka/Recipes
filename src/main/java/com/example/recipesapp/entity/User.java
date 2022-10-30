package com.example.recipesapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    private static final String regex = "(?i)[\\w!#$%&'*+/=?`{|}~^-]+" +
            "(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-z0-9-]+\\.)+[a-z]{2,6}";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @NotNull
    @NotBlank
    @Email(regexp = regex)
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 8)
    private String password;

}
