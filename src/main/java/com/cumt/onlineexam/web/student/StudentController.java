package com.cumt.onlineexam.web.student;

import com.cumt.onlineexam.po.Student;
import com.cumt.onlineexam.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;


    @GetMapping("/stu-index")
    public String studnet(){
        return "student/stu-index";
    }

    @PostMapping("/stu-login")
    public String stulogin(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes){
        Student student=studentService.checkStudent(username,password);
        if (student!=null){
            student.setPassword(null);
            session.setAttribute("student",student);
            return "student/stu-index";
        }else{
            redirectAttributes.addFlashAttribute("message","用户名密码错误");
            return "redirect:/index";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("student");
        return "redirect:/index";
    }

    @GetMapping("/update-pass")
    public String  updatepass(){
        return "student/stu-update-pass";
    }

    @PostMapping("/updatepass")
    public String stulogin(@RequestParam  String oldpassword, @RequestParam String newpassword1, @RequestParam String newpassword2
            , HttpSession httpSession, RedirectAttributes redirectAttributes, Model model){
        Student student = studentService.getOneById(((Student) httpSession.getAttribute("student")).getId());
        if(newpassword1.equals(newpassword2)){
            if (student.getPassword().equals(oldpassword)){
                student.setPassword(newpassword1);
                studentService.updateStudent(student.getId(),student);
                httpSession.removeAttribute("student");
                redirectAttributes.addFlashAttribute("message","修改成功，重新登陆");
                return "redirect:/index";
            }else {
                model.addAttribute("message","密码错误");
                return "student/stu-update-pass";
            }
        }else{
            model.addAttribute("message","两次输入不一致");
            return "student/stu-update-pass";
        }


    }

}
