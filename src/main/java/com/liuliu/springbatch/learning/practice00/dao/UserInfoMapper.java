package com.liuliu.springbatch.learning.practice00.dao;

import java.util.List;

import com.liuliu.springbatch.learning.practice00.entity.UserInfo;

public interface UserInfoMapper {
    List<UserInfo> listAll();

    int insert(UserInfo userInfo);
}
