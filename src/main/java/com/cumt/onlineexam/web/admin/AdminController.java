package com.cumt.onlineexam.web.admin;

import com.cumt.onlineexam.po.Admin;
import com.cumt.onlineexam.service.AdminService;
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
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @GetMapping("/adm-index")
    public String admin(){
        return "admin/adm-index";
    }

    @PostMapping("/adm-login")
    public String admlogin(@RequestParam  String username,@RequestParam String password,HttpSession httpSession,RedirectAttributes redirectAttributes){
        Admin admin=adminService.checkAdmin(username,password);
        if (admin!=null){
            admin.setPassword(null);
            httpSession.setAttribute("admin",admin);
            return "admin/adm-index";
        }else {
            redirectAttributes.addFlashAttribute("message","用户名密码错误");
            return "redirect:/index";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("admin");
        return "redirect:/index";
    }

    @GetMapping("/update-pass")
    public String  updatepass(){
        return "admin/adm-update-pass";
    }

    @PostMapping("/updatepass")
    public String admlogin(@RequestParam  String oldpassword, @RequestParam String newpassword1, @RequestParam String newpassword2
            , HttpSession httpSession, RedirectAttributes redirectAttributes, Model model){
        Admin admin = adminService.getOneById(((Admin) httpSession.getAttribute("admin")).getId());
        if (newpassword1.equals(newpassword2)){
            if (admin.getPassword().equals(oldpassword)){
                admin.setPassword(newpassword1);
                adminService.updateAdmin(admin.getId(),admin);
                httpSession.removeAttribute("admin");
                redirectAttributes.addFlashAttribute("message","修改成功，重新登陆");
                return "redirect:/index";
            }else {
                model.addAttribute("message","密码错误");
                return "admin/adm-update-pass";
            }
        }else{
            model.addAttribute("message","两次输入不一致");
            return "admin/adm-update-pass";
        }

    }

}
