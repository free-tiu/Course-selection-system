package com.course.service;

import com.course.entity.Course;
import com.course.entity.Student;
import com.course.entity.StudyInfo;
import com.course.utils.Page;

import java.util.List;

/**
 * 功能描述：
 *        系统用户模块  业务接口
 * @author: Zhao
 * @Description: com.course.service.IAdminService
 * @date: 2021/4/20
 */

public interface IAdminService {
    //系统用户登录
    public boolean login(String username, String pwd);
    //分页获取学生列表
    public Page<Student> getAllStudents(Integer page, Integer rows);

    public List<Course> getAllCourses();

    public boolean updateStudent(Student student);

    public Student getStudentById(String id);

    public boolean addStudent(Student student);

    public void delStudent(String id);

    public boolean addCourse(Course course);
    //更新课程信息
    public boolean updateCourse(Course course);
    //删除课程
    public void delCourse(Integer id);
    //根据课程id获取课程信息
    public Course getCourseById(Integer id);
    //分页获取学神
    public Page<StudyInfo> getAllStudyInfo(Integer page, Integer rows);

    public void addStudyInfo(StudyInfo studyInfo);

    public void delStudyInfo(Integer id);

    public StudyInfo getStudyById(Integer id);

    //后台分页获取获取课程列表
    public Page<Course> getPageAllCourses(Integer page, Integer rows);

}
