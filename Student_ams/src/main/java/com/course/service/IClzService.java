package com.course.service;

import com.course.entity.Course;
import com.course.utils.Page;

import java.util.List;

/**
 * 功能描述：
 *
 * @author: Zhao
 * @Description: com.course.service
 * @date: 2021/4/25
 */

public interface IClzService {

    public List<Course> getAllClz();

    public Course getClzById(Integer id);

    public void update(Course course);

    //分页获取所有课程
    public Page<Course> getAllPageClz(Integer page, Integer rows);
}
