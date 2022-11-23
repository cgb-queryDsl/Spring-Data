package com.nhnacademy.edu.jdbc1.service.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@ToString
@AllArgsConstructor
public class User {
    private final long id;
    private final String username;
    private final String password;
    private final Timestamp createdAt;
}
