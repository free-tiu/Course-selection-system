package com.course.dao.impl;

import com.course.dao.IAdminDao;
import com.course.entity.Admin;
import com.course.entity.Course;
import com.course.entity.Student;
import com.course.entity.StudyInfo;
import com.course.utils.Page;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：
 *         系统模块 => 持久层 => 实现类
 * @author: Zhao
 * @Description: com.course.dao.impl.AdminDao
 * @date: 2021/4/20
 */

/*实现类需要加上一个注解*/
@Repository
public class AdminDao extends SqlSessionDaoSupport implements IAdminDao {     /* 继承SqlSessionDaoSupport类，并实现接口类 */
    /* 定义命名空间*/
    private static final String SQL_NAMESPACE = "Admin";
    /* 根据用户名获取用户信息 */
    @Override
    public Admin selectById(String id) {
        /* 通过map进行查询 */
        Map<String,String> map = new HashMap<String, String>();
        map.put("id",id);
        /* 将上面定义的命名空间传进来*/
        return this.getSqlSession().selectOne(SQL_NAMESPACE+".selectById",map);
    }

    //添加课程信息
    @Override
    public void addCourse(Course course) {
        this.getSqlSession().insert(SQL_NAMESPACE+"insertClz",course);
    }

    @Override
    public Page<Student> selectAllStudents(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = this.getSqlSession().selectList(SQL_NAMESPACE + ".selectAllStudents").size();
        //分页处理
        map.put("pageNo", (page-1)*rows);
        map.put("rows", rows);
        List<Student> list = this.getSqlSession().selectList(SQL_NAMESPACE + ".selectPageAllStudents", map);
        Page<Student> result = new Page<Student>();
        result.setPage(page);
        result.setSize(rows);
        result.setRows(list);
        result.setTotal(total);
        return result;
    }

    @Override
    public List<Course> selectAllCourses() {
        return this.getSqlSession().selectList(SQL_NAMESPACE + ".selectAllCourses");
    }

    //功能描述：分页获取课程列表
    @Override
    public Page<Course> getPageAllCourses(Integer page, Integer rows) {
        //Map<key,value>
        Map<String, Object> map = new HashMap<String, Object>();
        //查找命名空间里面的所有课程
        int total = this.getSqlSession().selectList(SQL_NAMESPACE + ".selectAllCourses").size();
        //分页处理
        map.put("pageNo", (page-1)*rows);
        map.put("rows", rows);
        List<Course> list = this.getSqlSession().selectList(SQL_NAMESPACE + ".selectPageAllCourses", map);
        Page<Course> result = new Page<Course>();
        result.setPage(page);
        result.setSize(rows);
        result.setRows(list);
        result.setTotal(total);
        return result;
    }

    @Override
    public void updateStudent(Student student) {
        Map<String, Student> map = new HashMap<String, Student>();
        map.put("s", student);
        this.getSqlSession().update(SQL_NAMESPACE + ".updateStudent", map);
    }

    @Override
    public Student selectStuById(String id) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        return this.getSqlSession().selectOne(SQL_NAMESPACE + ".selectStuById", map);
    }

    @Override
    public void addStudent(Student student) {
        this.getSqlSession().insert(SQL_NAMESPACE + ".insertStudent", student);
    }

    @Override
    public void delStudent(String id) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        this.getSqlSession().delete(SQL_NAMESPACE + ".delStudent", map);
    }

    @Override
    public void delCourse(Integer id) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("id", id);
        this.getSqlSession().delete(SQL_NAMESPACE + ".delCourse", map);
    }

    @Override
    public Page<StudyInfo> selectAllStudyInfo(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<String, Object>();
        int total =  this.getSqlSession().selectList(SQL_NAMESPACE + ".selectAllStudyInfo").size();
        //分页处理
        map.put("pageNo", (page-1)*rows);
        map.put("rows", rows);
        List<StudyInfo> list = this.getSqlSession().selectList(SQL_NAMESPACE + ".selectPageAllStudyInfo", map);
        Page<StudyInfo> result = new Page<StudyInfo>();
        result.setPage(page);
        result.setSize(rows);
        result.setRows(list);
        result.setTotal(total);
        return result;
    }

    @Override
    public void addStudyInfo(StudyInfo studyInfo) {
        this.getSqlSession().insert(SQL_NAMESPACE + ".insertStudyInfo", studyInfo);
    }

    @Override
    public void delStudyInfo(Integer id) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("id", id);
        this.getSqlSession().delete(SQL_NAMESPACE + ".delStudyInfo", map);
    }

    @Override
    public StudyInfo selectById(Integer id) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("id", id);
        return this.getSqlSession().selectOne(SQL_NAMESPACE + ".selectByStudyId", map);
    }

    @Override
    public void delStudyInfoByCId(Integer id) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("id", id);
        this.getSqlSession().delete(SQL_NAMESPACE + ".delStudyInfoByCId", map);
    }

    @Override
    public void delStudyInfoBySId(String id) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        this.getSqlSession().delete(SQL_NAMESPACE + "delStudyInfoBySId", map);
    }
}
