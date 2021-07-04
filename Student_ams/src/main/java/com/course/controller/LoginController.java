package com.course.controller;

import com.course.entity.Student;
import com.course.service.IAdminService;
import com.course.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
/**
 * 功能描述：
 *      控制层
 *      系统用户登录
 * @author: Zhao
 * @Description: com.course.controller.LoginController
 * @date: 2021/4/20
 */

/*注解*/
@Controller
public class LoginController {

    /* 通过注解注入adminService 和 studentService */
    @Autowired
    private IAdminService adminService;
    @Autowired
    private IStudentService studentService;

    /* 系统用户登录（相当于管理员） */
    @RequestMapping(value="/adminLogin")     /*  , method = RequestMethod.GET  */
    public String adminLogin(@RequestParam String username,@RequestParam String pwd,HttpServletRequest req){
        /*判断用户名和密码输入是否符合规范*/
        if (username.length() > 0 && username.length() < 20 && pwd.length() > 0 && pwd.length() < 20) {
            /* 传入参数：登录名和密码 */
            if(adminService.login(username, pwd)){
                /* 输入正确的话，将用户名保存到session里 */
                req.getSession().setAttribute("id",username);
                /* 重定向到后台首页 */
                return "redirect:adminIndex";
            }
        }
        /* 判断为false的话返回 */
        return "adminLogin";
    }

    /*学生登陆*/
    @RequestMapping("/login")
    public String userLogin(@RequestParam String id, @RequestParam String pwd, HttpServletRequest req) {
        Student student = null;
        if (id.length() > 0 && id.length() < 20 && pwd.length() > 0 && pwd.length() < 20) {
            student = studentService.login(id, pwd);
        }
        if (student != null) {
            req.getSession().setAttribute("user", student.getName());
            req.getSession().setAttribute("userId", student.getId());
        } else {
            req.getSession().setAttribute("msg", "登录失败!用户名或密码错误!");
        }
        return "redirect:/index";
    }
    /*退出登录*/
    @RequestMapping("/logout")
    public String userLogout(HttpServletRequest req) {
        req.getSession().setAttribute("user", null);
        req.getSession().setAttribute("userId", null);
        return "redirect:/index";
    }

    /*修改密码*/
    @RequestMapping("/changePwd")
    public String changePwd(HttpServletRequest req, @RequestParam String old,
                            @RequestParam String newpwd, @RequestParam String newagain) {

        String stuId = (String) req.getSession().getAttribute("userId");
        if (newpwd.equals(newagain) && studentService.changePwd(stuId, old, newpwd)) {
            req.getSession().setAttribute("msg", "修改成功!");
        } else {
            req.getSession().setAttribute("msg", "修改失败!");
        }
        return "redirect:/index";
    }
}
