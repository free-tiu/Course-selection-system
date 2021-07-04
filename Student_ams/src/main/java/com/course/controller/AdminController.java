package com.course.controller;

import com.course.entity.Course;
import com.course.entity.Student;
import com.course.entity.StudyInfo;
import com.course.service.IAdminService;
import com.course.service.IStudentService;
import com.course.service.IStudyService;
import com.course.utils.Page;
import com.course.utils.Tools;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述：
 *      处理adminlogin.jsp发送过来的请求
 * @author: Zhao
 * @Description: com.course.controller
 * @date: 2021/4/20
 */

@Controller
public class AdminController {
    @Autowired
    //注入service层
    private IAdminService adminService;
    //按年月创建文件夹来存放图片
    public static SimpleDateFormat df = new SimpleDateFormat("yyyyMM");

    @Autowired
    private IStudentService studentService;
    @Autowired
    private IStudyService studyService;

    /*
     * 添加课程
     */
    //添加映射条件
    @RequestMapping(value = "/addCouse", method = RequestMethod.POST)
    @ResponseBody       /*@ResponseBody的作用其实是将java对象转为json格式的数据。*/
    //定义新增课程方法，传入参数为request，接收上传图片的名称
    public String addCourse(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        //接收消息
        String msg = null;
        //按年月创建文件夹来存放图片
        String year_mouth = df.format(new Date());
        try {
            //设置编码格式
            request.setCharacterEncoding("url-8");
            Course course = new Course();
            //判断上传的图片是否存在
            if (!file.isEmpty()) {
                //设置文件存放路径
                ServletContext sc = request.getSession().getServletContext();
                String dir = sc.getRealPath("/upload/imgurl" + year_mouth);
                //获取文件名称
                String filename = file.getOriginalFilename();
                //临时文件名+扩展名
                String tmpfilename = Tools.getRndFilename() + Tools.getFileExtName(filename);
                //将文件存放到指定目录
                FileUtils.writeByteArrayToFile(new File(dir, tmpfilename), file.getBytes());
                //将图片路径保存到数据库里
                course.setImgurl("/upload/imgurl" + year_mouth + "/" + tmpfilename);
            }
            //获取课程名称
            course.setName(request.getParameter("name"));
            //获取选课人数
            course.setSelected(0);
            //课程总人数
            course.setAmount(Integer.parseInt(request.getParameter("amount")));
            //课程所属专业
            course.setBelong(request.getParameter("belong"));
            //课程对应学分
            course.setCredit(Integer.parseInt(request.getParameter("credit")));
            //上课地点
            course.setPlace(request.getParameter("place"));
            //课程详情
            course.setDetail(request.getParameter("detail"));
            //课程学时
            course.setTime(request.getParameter("time"));

            if (adminService.addCourse(course)) {
                msg = "添加课程成功";
            } else {
                msg = "添加课程失败";
            }
        } catch (Exception e) {
            msg = "添加课程失败";
            e.printStackTrace();
        } finally {
            //将消息保存起来
            request.getSession().setAttribute("msg", msg);
            //重定向，跳转到另外一个方法 课程管理模块 里
            return "redirect:/courseManage";
        }
    }

    /**
     * 根据id获取课程信息
     */
    @RequestMapping(value = "/getCourseById", method = RequestMethod.GET)
    public String getCourseById(@RequestParam Integer id, HttpServletRequest request) {
        Course course = adminService.getCourseById(id);
        request.getSession().setAttribute("course", course);
        return "editCourse";
    }

    /*编辑课程*/
    @RequestMapping(value = "/editCourse", method = RequestMethod.POST)
    public String editCourse(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        String msg = null;
        String year_moth = df.format(new Date());

        try {
            request.setCharacterEncoding("utf-8");
            Integer id = (Integer) request.getSession().getAttribute("id");
            Course course = adminService.getCourseById(id);
            if (!file.isEmpty()) {
                //设置文件存放路径
                ServletContext sc = request.getSession().getServletContext();
                String dir = sc.getRealPath("/upload/imgurl" + year_moth);
                //获取文件名称
                String filename = file.getOriginalFilename();
                //临时文件名+扩展名
                String tmpfilename = Tools.getRndFilename() + Tools.getFileExtName(filename);
                //将文件存放到指定目录
                FileUtils.writeByteArrayToFile(new File(dir, tmpfilename), file.getBytes());
                //将图片路径保存到数据库里
                course.setImgurl("/upload/imgurl" + year_moth + "/" + tmpfilename);
                //获取课程名称
                course.setName(request.getParameter("name"));
                //课程总人数
                course.setAmount(Integer.parseInt(request.getParameter("amount")));
                //课程所属专业
                course.setBelong(request.getParameter("belong"));
                //课程对应学分
                course.setCredit(Integer.parseInt(request.getParameter("credit")));
                //上课地点
                course.setPlace(request.getParameter("place"));
                //课程详情
                course.setDetail(request.getParameter("detail"));
                //课程学时
                course.setTime(request.getParameter("time"));

                //更新课程方法
                if (adminService.updateCourse(course)) {
                    msg = "课程更新成功";
                } else {
                    msg = "课程更新失败";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "课程更新失败";
        } finally {
            request.getSession().setAttribute("msg",msg);
            return "rediect:/courseManage";
        }
    }

    /*更新学生信息*/
    @RequestMapping(value = "/changeStudent", method = RequestMethod.GET)
    public String changeStu(@RequestParam String id, HttpServletRequest req) {
        Student student = adminService.getStudentById(id);
        req.getSession().setAttribute("student", student);
        return "changeStu";
    }

    @RequestMapping(value = "/changeStudent", method = RequestMethod.POST)
    public String changeStudent(HttpServletRequest req) {
        try {
            req.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Student student = new Student();
        student.setId(req.getParameter("id"));
        student.setName(req.getParameter("name"));
        student.setPwd(req.getParameter("pwd"));
        student.setMajor(req.getParameter("major"));
        student.setYear(req.getParameter("year"));
        student.setSex(req.getParameter("sex").charAt(0));
        String msg = null;
        if (adminService.updateStudent(student)) {
            msg = "更新成功";
        } else {
            msg = "更新失败";
        }
        req.getSession().setAttribute("msg", msg);
        return "redirect:/studentManage";
    }

    @RequestMapping(value = "addStudent", method = RequestMethod.POST)
    public String addStudent(HttpServletRequest req) {
        try {
            req.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Student student = new Student();
        student.setId(req.getParameter("id"));
        student.setName(req.getParameter("name"));
        student.setPwd(req.getParameter("pwd"));
        student.setMajor(req.getParameter("major"));
        student.setYear(req.getParameter("year"));
        student.setSex(req.getParameter("sex").charAt(0));
        String msg = null;
        if (adminService.addStudent(student)) {
            msg = "添加成功";
        } else {
            msg = "添加失败";
        }
        req.getSession().setAttribute("msg", msg);
        return "redirect:/studentManage";
    }

    @RequestMapping("delStudent")
    public String delStudent(@RequestParam String id, HttpServletRequest req) {
        adminService.delStudent(id);
        req.getSession().setAttribute("msg", "删除成功");
        return "redirect:/studentManage";
    }

    @RequestMapping("adminDelCourse")
    public String delCourse(@RequestParam String id, HttpServletRequest req) {
        adminService.delCourse(Integer.parseInt(id));
        req.getSession().setAttribute("msg", "删除成功");
        return "redirect:/courseManage";
    }

    @RequestMapping(value = "/changeCourse", method = RequestMethod.GET)    /*指定该方法用于处理哪种类型的请求方式*/
    public String changeCourse(@RequestParam String id, HttpServletRequest req) {
        Course course = adminService.getCourseById(Integer.parseInt(id));
        req.getSession().setAttribute("course", course);
        return "changeClz";
    }

    @RequestMapping(value = "/changeCourse", method = RequestMethod.POST)
    public String changeCourse(HttpServletRequest req,@RequestParam("file") MultipartFile file) {
        String msg = null;
        String year_moth = df.format(new Date());
        try {
            req.setCharacterEncoding("utf-8");
            Integer id = (Integer) req.getSession().getAttribute("id");
            Course course = adminService.getCourseById(id);
            if(!file.isEmpty()){
                ServletContext sc = req.getSession().getServletContext();
                String dir = sc.getRealPath("/upload/imgurl/"+year_moth+"");    //设定文件保存的目录
                String filename = file.getOriginalFilename();    //得到上传时的文件名
                String tempfilename = Tools.getRndFilename()+Tools.getFileExtName(filename);
                try {
                    FileUtils.writeByteArrayToFile(new File(dir,tempfilename), file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                course.setImgurl("/upload/imgurl/"+year_moth+"/"+tempfilename); //设置图片所在路径
            }
            course.setName(req.getParameter("name"));
            course.setAmount(Integer.parseInt(req.getParameter("amount")));
            course.setBelong(req.getParameter("belong"));
            course.setCredit(Integer.parseInt(req.getParameter("credit")));
            course.setPlace(req.getParameter("place"));
            course.setDetail(req.getParameter("detail"));
            course.setTime(req.getParameter("time"));

            if (adminService.updateCourse(course)) {
                msg = "更新成功";
            } else {
                msg = "更新失败";
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            msg = "更新失败";
            e.printStackTrace();
        } finally {
            req.getSession().setAttribute("msg", msg);
            return "redirect:/courseManage";
        }
    }

    /*选课管理*/
    @RequestMapping("/chooseManage")
    public String chooseManage(@RequestParam(defaultValue="1")Integer page,
                               @RequestParam(defaultValue="5")Integer rows,HttpServletRequest req,Model model) {
        Page<StudyInfo> records = adminService.getAllStudyInfo(page,rows);
        req.getSession().setAttribute("records", records.getRows());
        model.addAttribute("page", records);
        return "allChoose";
    }

    /**/
    @RequestMapping("/delStudyInfo")
    public String delStudyInfo(HttpServletRequest req, @RequestParam String id) {
        String msg = null;
        try {
            Integer stdId = Integer.parseInt(id);
            StudyInfo info = adminService.getStudyById(stdId);
            Course c = adminService.getCourseById(info.getC_id());
            c.setSelected(c.getSelected() - 1);
            adminService.updateCourse(c);
            adminService.delStudyInfo(stdId);
            msg = "删除成功";
        } catch (Exception e) {
            msg = "删除失败";
            e.printStackTrace();
        } finally {
            req.getSession().setAttribute("msg", msg);
            return "redirect:/chooseManage";
        }
    }

    /**/
    @RequestMapping("/addChoose")
    public String addChoose(HttpServletRequest req, @RequestParam String stuId, @RequestParam String clzId) {
        String msg = null;
        try {
            Integer cId = Integer.parseInt(clzId);
            Student s = adminService.getStudentById(stuId);
            Course c = adminService.getCourseById(cId);
            if (s != null && c != null) {
                if(c.getAmount()>c.getSelected()) {
                    int rst = studentService.selectCource(stuId, cId);
                    if (rst==0) {
                        msg = "添加成功";
                    } else if(rst==1){
                        msg = "已经选过此课!";
                    } else if(rst==2){
                        msg="该课程已选满!";
                    }else{
                        msg="未知错误!";
                    }
                }
            }else{
                msg="添加失败";
            }
        } catch (Exception e) {
            msg = "添加失败";
            e.printStackTrace();
        } finally {
            req.getSession().setAttribute("msg", msg);
            return "redirect:/chooseManage";
        }
    }
}
