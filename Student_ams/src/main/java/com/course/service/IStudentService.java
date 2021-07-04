package com.course.service;

import com.course.entity.Student;

/**
 * 功能描述：
 *
 * @author: Zhao
 * @Description: com.course.service
 * @date: 2021/4/25
 */

public interface IStudentService {

    public Student login(String id, String pwd);
    public boolean update(Student student);
    public boolean delCourse(String id, Integer clzId);

    /**
     *  @return 0 成功,1 已经选过此课程,2 已选满,-1 未知错误
     */
    public int selectCource(String stuId, Integer clzId);

    public boolean changePwd(String stuId, String old,String newpwd);
}
