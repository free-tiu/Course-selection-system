package com.course.service.impl;

import com.course.dao.IAdminDao;
import com.course.entity.Admin;
import com.course.entity.Course;
import com.course.entity.Student;
import com.course.entity.StudyInfo;
import com.course.service.IAdminService;
import com.course.utils.Page;
import com.course.dao.IClzDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能描述：
 *      系统用户模块业务实现类
 * @author: Zhao
 * @Description: com.course.service.impl.AdminService
 * @date: 2021/4/20
 *
 *  Autowired注解：它可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作。==>自动注入依赖的Bean。
 */

/* 实现类注解 */
@Service
public class AdminService implements IAdminService {
    /* 调用dao层   @Autowired注释：它可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作。==>自动注入依赖的Bean。 */
    @Autowired
    private IAdminDao adminDao;

    @Autowired
    private IClzDao clzDao;

    /* 重写方法 */
    /* 根据用户名和密码实现登录*/
    @Override
    public boolean login(String username, String pwd) {
        Admin admin = adminDao.selectById(username);
        /* 判断输入的用户名是否为空 和 输入的密码是否正确匹配数据表中相应的密码*/
        if (admin != null && admin.getPwd().equals(pwd)){
            return true;
        }
        return false;
    }

    //添加课程信息
    @Override
    public boolean addCourse(Course course) {
        //获取课程名称,使用变量来接收课程名称
        String name = course.getName();
        //对课程名称长度进行限制
        if(name.length()>0&&name.length()<=100){
            String time=course.getTime();
            if(time.length()>0&&time.length()<=20){
                for (int i = 0; i < time.length(); i++) {
                    if (time.charAt(i) > '9' || time.charAt(i) < '0') {
                        if (time.charAt(i) != '(' && time.charAt(i) != ')' && time.charAt(i) != '/') {
                            return false;
                        }
                    }
                }
                String belong=course.getBelong();
                if(belong.length()>0&&belong.length()<=100){
                    String detail=course.getDetail();
                    if(detail.length()>0&&detail.length()<=200){
                        String place=course.getPlace();
                        if(place.length()>0&&place.length()<=30){
                            adminDao.addCourse(course);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    //根据课程id获取课程信息
    @Override
    public Course getCourseById(Integer id) {
        return clzDao.selectById(id);
    }

    //更新课程信息
    @Override
    public boolean updateCourse(Course course) {
        Integer id=course.getId();
        if(id!=null||id>0) {
            String name = course.getName();
            if (name.length() > 0 && name.length() <= 100) {
                String time = course.getTime();
                if (time.length() > 0 && time.length() <= 20) {
                    for (int i = 0; i < time.length(); i++) {
                        if (time.charAt(i) > '9' || time.charAt(i) < '0') {
                            if (time.charAt(i) != '(' && time.charAt(i) != ')' && time.charAt(i) != '/') {
                                return false;
                            }
                        }
                    }
                    String belong = course.getBelong();
                    if (belong.length() > 0 && belong.length() <= 100) {
                        String detail = course.getDetail();
                        if (detail.length() > 0 && detail.length() <= 200) {
                            String place = course.getPlace();
                            if (place.length() > 0 && place.length() <= 30) {
                                if(course.getAmount()>=course.getSelected()) {
                                    clzDao.update(course);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Page<Student> getAllStudents(Integer page,Integer rows) {
        return adminDao.selectAllStudents(page,rows);
    }

    /* 功能描述：获取所有课程*/
    @Override
    public List<Course> getAllCourses() {
        return adminDao.selectAllCourses();
    }

    /* 功能描述：后台分页获取课程管理 */
    @Override
    public Page<Course> getPageAllCourses(Integer page, Integer rows) {
        //返回当前页和每页条数
        return adminDao.getPageAllCourses(page,rows);
    }

    @Override
    public boolean updateStudent(Student student) {
        if (student.getId() != null && student.getId().length() != 0) {
            String name = student.getName().trim();
            if (name != null && name.length() <= 20 && name.length() > 0) {
                String pwd = student.getPwd().trim();
                if (pwd != null && pwd.length() <= 20 && pwd.length() > 0) {
                    String major = student.getMajor().trim();
                    if (major != null && major.length() <= 100 && major.length() > 0) {
                        String year = student.getYear();
                        if (year.length() == 4) {
                            for (int i = 0; i < 4; i++) {
                                if (year.charAt(i) > '9' || year.charAt(i) < '0') {
                                    return false;
                                }
                            }
                            adminDao.updateStudent(student);
                            return true;
                        }

                    }
                }
            }
        }
        return false;
    }

    @Override
    public Student getStudentById(String id) {
        return adminDao.selectStuById(id);
    }

    /*添加学生*/
    @Override
    public boolean addStudent(Student student) {
        if (student.getId() != null && student.getId().length() > 0 && student.getId().length() <= 20) {
            if (adminDao.selectStuById(student.getId()) == null) {
                String name = student.getName().trim();
                if (name != null && name.length() <= 20 && name.length() > 0) {
                    String pwd = student.getPwd().trim();
                    if (pwd != null && pwd.length() <= 20 && pwd.length() > 0) {
                        String major = student.getMajor().trim();
                        if (major != null && major.length() <= 100 && major.length() > 0) {
                            String year = student.getYear();
                            if (year.length() == 4) {
                                for (int i = 0; i < 4; i++) {
                                    if (year.charAt(i) > '9' || year.charAt(i) < '0') {
                                        return false;
                                    }
                                }
                                if(Integer.parseInt(year)>=1900) {
                                    adminDao.addStudent(student);
                                    return true;
                                }
                            }

                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void delStudent(String id) {
        adminDao.delStudent(id);
        adminDao.delStudyInfoBySId(id);
    }

    @Override
    public void delCourse(Integer id) {
        adminDao.delCourse(id);
        adminDao.delStudyInfoByCId(id);
    }

    /* 功能描述：分页获取选课信息 */
    @Override
    public Page<StudyInfo> getAllStudyInfo(Integer page, Integer rows) {
        return adminDao.selectAllStudyInfo(page,rows);
    }

    @Override
    public void addStudyInfo(StudyInfo studyInfo) {
        adminDao.addStudyInfo(studyInfo);
    }

    @Override
    public void delStudyInfo(Integer id) {
        adminDao.delStudyInfo(id);
    }

    @Override
    public StudyInfo getStudyById(Integer id) {
        return adminDao.selectById(id);
    }
}
