package com.wzb.visual.dao;

import com.wzb.visual.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserMapper {

    void save(@Param("user") User user);

    void delete(@Param("id") Integer id);

    void update(@Param("user") User user);

    User get(@Param("id") Integer id);

    User getByUsername(@Param("username") String username);

    int getCount();

    List<User> getUsers();
}
