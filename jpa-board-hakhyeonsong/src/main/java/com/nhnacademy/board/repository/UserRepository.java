package com.nhnacademy.board.repository;

import com.nhnacademy.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);
}
