package com.course.dao;

import com.course.entity.StudyInfo;
import com.course.utils.Page;

/**
 * 功能描述：
 *
 * @author: Zhao
 * @Description: com.course.dao
 * @date: 2021/4/25
 */

public interface IStudyInfoDao {
    //查看选择该课程的所有学生列表
    public Page<StudyInfo> selectByClzId(Integer clzId, Integer page, Integer rows);

    //分页获取我的选课列表
    public Page<StudyInfo> selectByStuId(String stuId,Integer page,Integer rows);

    public StudyInfo check(String stuId,Integer clzId);

    public void insert(StudyInfo studyInfo);

    public void delete(String stuId,Integer clzId);
}
