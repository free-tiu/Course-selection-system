package com.course.service.impl;

import com.course.dao.IClzDao;
import com.course.entity.Course;
import com.course.service.IClzService;
import com.course.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能描述：
 *
 * @author: Zhao
 * @Description: com.course.service.impl
 * @date: 2021/4/25
 */

@Service
public class ClzService implements IClzService {
    @Autowired
    private IClzDao clzDao;

    @Override
    public List<Course> getAllClz() {
        return clzDao.selectAll();
    }

    @Override
    public Course getClzById(Integer id) {
        return clzDao.selectById(id);
    }

    @Override
    public void update(Course course) {
        clzDao.update(course);
    }

    /**
     * 功能描述：分页获取所有课程
     */
    @Override
    public Page<Course> getAllPageClz(Integer page, Integer rows) {

        return clzDao.getAllPageClz(page,rows);
    }
}
