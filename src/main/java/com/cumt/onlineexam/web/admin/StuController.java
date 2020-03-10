package com.cumt.onlineexam.web.admin;

import com.cumt.onlineexam.po.Student;
import com.cumt.onlineexam.po.Teacher;
import com.cumt.onlineexam.service.StudentService;
import com.cumt.onlineexam.service.TeacherService;
import com.cumt.onlineexam.utils.FileUtil;
import com.cumt.onlineexam.vo.ImportStudent;
import com.cumt.onlineexam.vo.StudentQuery;
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
public class StuController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/adm-student")
    public String student(@PageableDefault(size = 20,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        Page<Student> students = studentService.getAllStudent(pageable);
        model.addAttribute("teachers",teacherService.listTeacher());
        model.addAttribute("grades",studentService.listGrade());
        model.addAttribute("page",students);
        return "admin/adm-student";
    }

    //组合查询
    @PostMapping("/adm-student/search")
    public String search(@PageableDefault(size=20,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable , StudentQuery studentQuery, Model model){
        model.addAttribute("page",studentService.listStudent(pageable, studentQuery));
        return "admin/adm-student :: studentList";
    }
    //点击新增，进入新增页
    @GetMapping("/adm-student/input")
    public String input(Model model){
        List<Teacher> teachers = teacherService.listTeacher();
        model.addAttribute("teachers",teachers);
        model.addAttribute("student",new Student());
        return "admin/adm-student-input";
    }

    //点击编辑，进入编辑页
    @GetMapping("/adm-student/{id}/input")
    public  String  editInput(@PathVariable Long id , Model model){
        Student student =studentService.getOneById(id);
        List<Teacher> teachers = teacherService.listTeacher();
        model.addAttribute("teachers",teachers);
        model.addAttribute("student",student);
        return "admin/adm-student-input";
    }

    //新增页提交
    @PostMapping("/adm-student-input")
    public String post(Student student, RedirectAttributes attributes, HttpSession session){

        Student s;
        if (student.getId()==null){
            s=studentService.saveStudent(student);
        }else{
            s=studentService.updateStudent(student.getId(),student);
        }

        if (s==null){
            attributes.addFlashAttribute("message","操作失败");
        }else{
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/adm-student";
    }


    @PostMapping("/importstudent")
    public String  importExcel (@RequestParam("file") MultipartFile file){
        List<ImportStudent> importStudents = FileUtil.importExcel(file,0,1,ImportStudent.class);
        //TODO 保存数据库
        for (ImportStudent importStudent : importStudents){
            Student student =new Student();
            student.setName(importStudent.getName());
            student.setStudentNum(importStudent.getStudentNum());
            student.setGrade(importStudent.getGrade());
            student.setPassword(importStudent.getPassword());
            student.setTeacher(teacherService.getOneByName(importStudent.getTeacher()));
            studentService.saveStudent(student);
        }
        return "redirect:/admin/adm-student";
    }


}
