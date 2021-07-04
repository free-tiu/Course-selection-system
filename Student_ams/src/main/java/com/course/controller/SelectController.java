package com.course.controller;

import com.course.entity.Course;
import com.course.service.IClzService;
import com.course.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 功能描述：
 *
 * @author: Zhao
 * @Description: com.course.controller
 * @date: 2021/4/25
 */

@Controller
public class SelectController {
    @Autowired
    private IStudentService studentService;
    @Autowired
    private IStudentService studyService;
    @Autowired
    private IClzService clzService;

    @RequestMapping("/selectClz")
    public String selectClz(HttpServletRequest req) {
        String sId = (String) req.getSession().getAttribute("userId");
        Course c = (Course) req.getSession().getAttribute("course");
        Integer cId = c.getId();
        String msg = null;
        int rst = studentService.selectCource(sId, cId);
        if (rst==0) {
            msg = "选课成功!";
        } else if(rst==1){
            msg = "已经选过此课!";
        } else if(rst==2){
            msg="该课程已选满!";
        }else{
            msg="未知错误!";
        }
        req.getSession().setAttribute("msg", msg);
        return "detail";
    }

    @RequestMapping("/delCourse")
    public synchronized String delClz(HttpServletRequest req){
        try {
            String stuId = (String) req.getSession().getAttribute("userId");
            Integer clzId =Integer.parseInt(req.getParameter("id"));
            studyService.delCourse(stuId,clzId);
            Course c=clzService.getClzById(clzId);
            c.setSelected(c.getSelected()-1);
            clzService.update(c);
        }catch (NumberFormatException e){
            e.printStackTrace();
            return "404";
        }
        req.getSession().setAttribute("msg","删除成功!");
        return "redirect:/showMyClasses";
    }
}
