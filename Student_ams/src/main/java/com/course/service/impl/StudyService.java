package com.course.service.impl;

import com.course.dao.IStudyInfoDao;
import com.course.entity.StudyInfo;
import com.course.service.IStudyService;
import com.course.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能描述：
 *
 * @author: Zhao
 * @Description: com.course.service.impl
 * @date: 2021/4/25
 */

@Service
public class StudyService implements IStudyService {

    @Autowired
    private IStudyInfoDao studyInfoDao;

    @Override
    public Page<StudyInfo> getAllClzByStuId(String stuId,Integer page,Integer rows) {
        return studyInfoDao.selectByStuId(stuId,page,rows);
    }

    /**
     * 功能描述：查看选择该课程的所有学生列表
     */
    @Override
    public Page<StudyInfo> getAllStuByClzId(Integer clzId,Integer page,Integer rows) {
        return studyInfoDao.selectByClzId(clzId,page,rows);
    }

    @Override
    public void delCourse(String stuId, Integer clzId) {
        studyInfoDao.delete(stuId,clzId);
    }
}