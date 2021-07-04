package com.course.service;

import com.course.entity.StudyInfo;
import com.course.utils.Page;

/**
 * 功能描述：
 *
 * @author: Zhao
 * @Description: com.course.service
 * @date: 2021/4/25
 */

public interface IStudyService {

    public Page<StudyInfo> getAllClzByStuId(String stuId, Integer page, Integer rows);

    //查看选择该课程的所有学生列表
    public Page<StudyInfo> getAllStuByClzId(Integer clzId, Integer page, Integer rows);

    public void delCourse(String stuId,Integer clzId);
}
