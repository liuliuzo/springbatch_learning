package com.liuliu.springbatch.learning.practice00.dao;

import java.util.List;

import com.liuliu.springbatch.learning.practice00.entity.User;

public interface UserMapper {
    List<User> listAll();

    int insert(User user);
}
