package com.course.dao;

import com.course.entity.Admin;
import com.course.entity.Course;
import com.course.entity.Student;
import com.course.entity.StudyInfo;
import com.course.utils.Page;

import java.util.List;

/**
 * 功能描述：
 *
 * @author: Zhao
 * @Description: com.course.dao.IAdminDao
 * @date: 2021/4/20
 */

public interface IAdminDao {
    //根据用户名获取用户信息
    public Admin selectById(String id);

    public Page<Student> selectAllStudents(Integer page, Integer rows);

    public List<Course> selectAllCourses();

    public void updateStudent(Student student);

    public Student selectStuById(String id);

    public void addStudent(Student student);

    public void delStudent(String id);
    //添加课程信息
    public void addCourse(Course course);

    public void delCourse(Integer id);

    //分页获取选课信息
    public Page<StudyInfo> selectAllStudyInfo(Integer page, Integer rows);

    public void addStudyInfo(StudyInfo studyInfo);

    public void delStudyInfo(Integer id);

    public StudyInfo selectById(Integer id);

    public void delStudyInfoByCId(Integer id);

    public void delStudyInfoBySId(String id);

    //分页获取所有课程（获取课程列表）
    public Page<Course> getPageAllCourses(Integer page, Integer rows);

}
