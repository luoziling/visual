package com.wzb.visual.service.impl;

import com.github.pagehelper.PageHelper;
import com.wzb.visual.dao.UserMapper;
import com.wzb.visual.model.User;
import com.wzb.visual.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements BaseService<User> {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void save(User user) {
        userMapper.save(user);
    }

    @Override
    public void delete(Object id) {
        userMapper.delete((Integer) id);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public User get(Object id) {
        return userMapper.get((Integer) id);
    }

    public User getByUsername(String username) {
        return userMapper.getByUsername(username);
    }

    @Override
    public Map<String, Object> getUsers(int pageNum, int pageSize) {
        Map<String, Object> data = new HashMap<>();
        int count = userMapper.getCount();
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("users", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("users", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.getUsers();
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("users", users);
        return data;
    }


}
