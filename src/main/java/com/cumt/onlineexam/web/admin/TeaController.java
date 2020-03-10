package com.cumt.onlineexam.web.admin;

import com.cumt.onlineexam.po.Student;
import com.cumt.onlineexam.po.Teacher;
import com.cumt.onlineexam.service.TeacherService;
import com.cumt.onlineexam.utils.FileUtil;
import com.cumt.onlineexam.vo.ImportTeacher;
import com.cumt.onlineexam.vo.TeacherQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TeaController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/adm-teacher")
    public String teacher(@PageableDefault(size = 20,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        Page<Teacher> teachers = teacherService.getAllTeacher(pageable);
        model.addAttribute("page",teachers);
        return "admin/adm-teacher";
    }

    //组合查询
    @PostMapping("/adm-teacher/search")
    public String search(@PageableDefault(size=20,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable , TeacherQuery teacherQuery, Model model){
        model.addAttribute("page",teacherService.listTeacher(pageable, teacherQuery));
        return "admin/adm-teacher :: teacherList";
    }
    //点击新增，进入新增页
    @GetMapping("/adm-teacher/input")
    public String input(Model model){
        model.addAttribute("teacher",new Teacher());
        return "admin/adm-teacher-input";
    }

    //点击编辑，进入编辑页
    @GetMapping("/adm-teacher/{id}/input")
    public  String  editInput(@PathVariable Long id , Model model){
        Teacher teacher =teacherService.getOneById(id);
        model.addAttribute("teacher",teacher);
        return "admin/adm-teacher-input";
    }
    //新增页提交
    @PostMapping("/adm-teacher-input")
    public String post(Teacher teacher, RedirectAttributes attributes, HttpSession session){

        Teacher t;
        if (teacher.getId()==null){
            t=teacherService.saveTeacher(teacher);
        }else{
            t=teacherService.updateTeacher(teacher.getId(),teacher);
        }

        if (t==null){
            attributes.addFlashAttribute("message","操作失败");
        }else{
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/adm-teacher";
    };



    @PostMapping("/importteacher")
    public String  importExcel (@RequestParam("file") MultipartFile file){

        List<ImportTeacher> importTeachers = FileUtil.importExcel(file,0,1,ImportTeacher.class);
        //TODO 保存数据库
        for (ImportTeacher importTeacher : importTeachers){
           Teacher teacher =new Teacher();
            teacher.setName(importTeacher.getName());
            teacher.setTeacherNum(importTeacher.getTeacherNum());
            teacher.setPassword(importTeacher.getPassword());
            teacherService.saveTeacher(teacher);
        }
        return "redirect:/admin/adm-teacher";
    }


}
