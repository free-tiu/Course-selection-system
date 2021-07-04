package com.course.dao;

import com.course.entity.Student;

/**
 * 功能描述：
 *
 * @author: Zhao
 * @Description: com.course.dao
 * @date: 2021/4/25
 */

public interface IStudentDao {
    /* 添加学生信息 */
    public void insert(Student student);

    /*更新学生信息*/
    public void update(Student student);

    /*根据学号删除学生信息*/
    public void delete(String id);

    /*根据id查找学生*/
    public Student selectById(String id);
}
