package com.course.dao;

import com.course.entity.Course;
import com.course.utils.Page;

import java.util.List;

/**
 * 功能描述：
 *        课程模块 业务接口
 * @author: Zhao
 * @Description: com.course.dao
 * @date: 2021/4/24
 */

public interface IClzDao {
    public List<Course> selectAll();

    //根据课程id获取课程信息
    public Course selectById(Integer id);

    //编辑课程信息
    public void update(Course course);

    //分页获取所有课程
    public Page<Course> getAllPageClz(Integer page, Integer rows);
}
