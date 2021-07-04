package com.course.entity;

import org.springframework.stereotype.Service;

/**
 * 功能描述：
 *          系统用户实体类
 * @author : zhao
 * @date : 2021/4/9
 * @Description : com.course.entity
 * @version : 1.0
 */
@Service
public class Admin {

    private String id;
    private String pwd;

    /*
        生成getter和setter方法
        右键，generate ...
    */

    public String getUsername() {
        return id;
    }

    public void setUsername(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
