package com.wzb.visual.model;

import java.io.Serializable;
import java.sql.Date;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer ID;
    private String username;
    private String password;
    private Integer permission;//可以单独建立一张权限表来表示权限数字对应的权限
    private Date birthday;
    //数据库中的tinyint(1)应该表示成什么
    private Boolean gender;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", permission=" + permission +
                ", birthday=" + birthday +
                ", gender=" + gender +
                '}';
    }
}
