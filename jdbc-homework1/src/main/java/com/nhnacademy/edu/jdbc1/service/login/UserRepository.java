package com.nhnacademy.edu.jdbc1.service.login;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
}
