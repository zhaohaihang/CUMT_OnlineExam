package com.cumt.onlineexam.web.teacher;

import com.cumt.onlineexam.po.Teacher;
import com.cumt.onlineexam.service.ChapterService;
import com.cumt.onlineexam.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private  TeacherService teacherService;

    @GetMapping("/tea-index")
    public String chapter(){
        return "teacher/tea-index";
    }

    @PostMapping("/tea-login")
    public String tealogin(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes){

        Teacher teacher=teacherService.checkTeacher(username,password);
        if (teacher!=null){
            teacher.setPassword(null);
            session.setAttribute("teacher",teacher);
            return "teacher/tea-index";
        }else{
            redirectAttributes.addFlashAttribute("message","用户名密码错误");
            return "redirect:/index";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("teacher");
        return "redirect:/index";
    }

    @GetMapping("/update-pass")
    public String  updatepass(){
        return "teacher/tea-update-pass";
    }

    @PostMapping("/updatepass")
    public String stulogin(@RequestParam  String oldpassword, @RequestParam String newpassword1, @RequestParam String newpassword2
            , HttpSession httpSession, RedirectAttributes redirectAttributes, Model model){
        Teacher teacher = teacherService.getOneById(((Teacher) httpSession.getAttribute("teacher")).getId());
        if(newpassword1.equals(newpassword2)){
            if (teacher.getPassword().equals(oldpassword)){
                teacher.setPassword(newpassword1);
                teacherService.updateTeacher(teacher.getId(),teacher);
                httpSession.removeAttribute("teacher");
                redirectAttributes.addFlashAttribute("message","修改成功，重新登陆");
                return "redirect:/index";
            }else {
                model.addAttribute("message","密码错误");
                return "teacher/tea-update-pass";
            }
        }else{
            model.addAttribute("message","两次输入不一致");
            return "teacher/tea-update-pass";
        }
    }

}
