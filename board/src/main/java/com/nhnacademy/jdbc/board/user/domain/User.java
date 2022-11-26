package com.nhnacademy.jdbc.board.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@ToString
public class User {
    private final long id;
    private final String username;
    private final String password;
    private final UserRole userRole;
    private final LocalDateTime createdAt;
}
