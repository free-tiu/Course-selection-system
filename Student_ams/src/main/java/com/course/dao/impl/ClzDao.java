package com.course.dao.impl;

import com.course.dao.IClzDao;
import com.course.entity.Course;
import com.course.utils.Page;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：
 *      接口实现层      课程模块     持久层
 * @author: Zhao
 * @Description: com.course.dao.impl
 * @date: 2021/4/24
 */

@Repository
public class ClzDao extends SqlSessionDaoSupport implements IClzDao {
    //定义命名空间
    private static final String SQL_NAMESPACE = "Clz";

    //根据课程id获取课程信息
    @Override
    public Course selectById(Integer id) {
        return this.getSqlSession().selectOne(SQL_NAMESPACE + ".selectById",id);
    }

    //编辑课程信息
    @Override
    public void update(Course course) {
        this.getSqlSession().update(SQL_NAMESPACE + ".update",course);
    }

    @Override
    public List<Course> selectAll() {
        return this.getSqlSession().selectList(SQL_NAMESPACE + ".selectAll");
    }

    /* 功能描述：分页获取所有课程 */
    @Override
    public Page<Course> getAllPageClz(Integer page, Integer rows) {
        //分页处理
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("pageNo", (page-1)*rows);
        map.put("rows", rows);
        List<Course> list = this.getSqlSession().selectList(SQL_NAMESPACE + ".selectPageAll", map);
        int total = this.getSqlSession().selectList(SQL_NAMESPACE + ".selectAll").size();
        Page<Course> result = new Page<Course>();
        result.setPage(page);
        result.setSize(rows);
        result.setRows(list);
        result.setTotal(total);
        return result;
    }
}
