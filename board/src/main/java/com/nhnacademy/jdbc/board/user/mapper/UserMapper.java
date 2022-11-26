package com.nhnacademy.jdbc.board.user.mapper;

import com.nhnacademy.jdbc.board.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> selectUser(@Param("username") String username, @Param("password") String password);

    Optional<User> findByUsername(@Param("username") String username);
}
