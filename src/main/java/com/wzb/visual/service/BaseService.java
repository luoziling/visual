package com.wzb.visual.service;

import java.io.Serializable;
import java.util.Map;

public interface BaseService<T extends Serializable> {
    /**
     * save
     */
    void save(T obj);

    /**
     * delete
     */
    void delete(Object id);

    /**
     * update
     */
    void update(T obj);

    /**
     * get
     */
    T get(Object id);
//    /**
//     * list
//     */
//    List<T> list();

    /**
     * 分页查询
     */
    Map<String, Object> getUsers(int pageNum, int pageSize);
}
