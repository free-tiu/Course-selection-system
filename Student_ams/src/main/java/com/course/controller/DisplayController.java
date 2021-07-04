package com.course.controller;

import com.alibaba.fastjson.JSON;

import com.course.entity.Course;
import com.course.entity.Student;
import com.course.entity.StudyInfo;
import com.course.service.IAdminService;
import com.course.service.IClzService;
import com.course.service.IStudyService;
import com.course.utils.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
/**
 * 功能描述：
 *      处理index.jsp发送过来的请求
 * @author: Zhao
 * @Description: com.course.controller
 * @date: 2021/4/25
 */

@Controller
public class DisplayController {

    @Autowired
    private IClzService clzService;
    @Autowired
    private IStudyService studyService;
    @Autowired
    private IAdminService adminService;


    /*功能描述：前台首页        RequestMapping注解类型用于映射一个请求或一个方法，默认属性 */
    @RequestMapping("/index")           /*@RequestMapping(value="")*/
    public String displayAll(HttpServletRequest req) {
        List<Course> clzs = clzService.getAllClz();
        req.getSession().setAttribute("clzs", clzs);
        return "index";
    }

    /* 功能描述：课程详情 */
    @RequestMapping("/showDetail")      /* 等同于 @RequestMapping(value="/showDetail") */
    public String showDetail(@RequestParam String id, HttpServletRequest req) {
        Course course = clzService.getClzById(Integer.parseInt(id));
        req.getSession().setAttribute("course", course);
        return "detail";
    }

    /**
     * 功能描述：查看已选该门课程的所有学生列表
     * @param req
     * @return
     */
    @RequestMapping("/showStudent")
    public String showStudents(@RequestParam(defaultValue="1")Integer page,
                               @RequestParam(defaultValue="5")Integer rows, HttpServletRequest req, Model model) {
        try {
            Integer clzId = Integer.parseInt(req.getParameter("id"));
            Page<StudyInfo> students = studyService.getAllStuByClzId(clzId,page,rows);

            req.getSession().setAttribute("students", students.getRows());
            model.addAttribute("page", students);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "404";
        }

        return "stulist";

    }

    /*分页获取所有课程*/
    @RequestMapping("/showAllClasses")
    public String showAllClzs(@RequestParam(defaultValue="1")Integer page,/*显示页和行*/
                              @RequestParam(defaultValue="5")Integer rows,HttpServletRequest req,Model model) {
        Page<Course> courses = clzService.getAllPageClz(page,rows);
        req.getSession().setAttribute("courses", courses.getRows());
        //req.getSession().setAttribute("page", courses);
        model.addAttribute("page", courses);
        return "clzList";
    }

    /*分页获取我的选课列表*/
    @RequestMapping("/showMyClasses")
    public String showMyClzs(@RequestParam(defaultValue="1")Integer page,
                             @RequestParam(defaultValue="5")Integer rows,HttpServletRequest req,Model model) {
        String id = (String) req.getSession().getAttribute("userId");
        Page<StudyInfo> studyInfos=null;
        if(id!=null){
            studyInfos = studyService.getAllClzByStuId(id,page,rows);
            req.getSession().setAttribute("clzs", studyInfos.getRows());
            model.addAttribute("page", studyInfos);
        }else{
            req.getSession().setAttribute("clzs", null);
        }
        return "myClzs";
    }

    /*学生管理，分页获取学生列表*/
    @RequestMapping("/studentManage")
    public String studentManage(@RequestParam(defaultValue="1")Integer page,
                                @RequestParam(defaultValue="5")Integer rows,HttpServletRequest req,Model model){
        if(req.getSession().getAttribute("id")==null){
            return "adminLogin";     /*123adminLogin*/
        }
        Page<Student> students=adminService.getAllStudents(page,rows);
        req.getSession().setAttribute("students", students.getRows());
        model.addAttribute("page", students);
        return "allStudents";
    }

    /*后台课程管理*/
    @RequestMapping("/courseManage")        /*映射路径*/
    public String courseManage(@RequestParam(defaultValue="1")Integer page,
                               @RequestParam(defaultValue="5")Integer rows,HttpServletRequest req,Model model){
        if(req.getSession().getAttribute("id")==null){
            return "adminLogin";
        }
        /* 分页获取所有的课程     page为当前页，rows为每页条数 */
        Page<Course> courses=adminService.getPageAllCourses(page,rows);
        /* 将获取的数据放到request里 */
        req.getSession().setAttribute("courses", courses.getRows());
        //返回当前页
        model.addAttribute("page", courses);
        //所有课程页面
        return "allCourses";
    }

    /*管理员后台首页       获取后台管理列表*/
    @RequestMapping("/adminIndex")
    public String showChart(HttpServletRequest req){
        if(req.getSession().getAttribute("id")==null){
            return "adminLogin";
        }
        List<Course> courses=adminService.getAllCourses();
        List<String> listX=new ArrayList<String>();
        List<Integer> listSelected=new ArrayList<Integer>();
        List<Integer> listLeft=new ArrayList<Integer>();
        for(Course course:courses){
            listX.add(course.getName());
            listSelected.add(course.getSelected());
            listLeft.add(course.getAmount()-course.getSelected());
        }
        req.getSession().setAttribute("listX", JSON.toJSONString(listX));
        req.getSession().setAttribute("listSelected",JSON.toJSONString(listSelected));
        req.getSession().setAttribute("listLeft",JSON.toJSONString(listLeft));
        return "admin";
    }

    @RequestMapping("/404")
    public String pageNotFount(){
        return "404";
    }
}