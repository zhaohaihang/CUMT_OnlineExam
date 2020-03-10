package com.cumt.onlineexam.web.teacher;

import com.cumt.onlineexam.po.Chapter;
import com.cumt.onlineexam.po.Teacher;
import com.cumt.onlineexam.service.ChapterService;
import com.cumt.onlineexam.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/teacher")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    //进入列表界面
    @GetMapping("/tea-chapter")
    public String chapter(@PageableDefault(size = 20,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        Page<Chapter> chapterPage=chapterService.listChapter(pageable);
        model.addAttribute("page",chapterPage);
        return "teacher/tea-chapter";
    }

    //点击新增，进入空白新增页面
    @GetMapping("/tea-chapter/input")
    public String input( Model model){
        model.addAttribute("chapter",new Chapter());
        return "teacher/tea-chapter-input";
    }

    //点击编辑，进入带有数据的编辑页
    @GetMapping("/tea-chapter/{id}/input")
    public  String  editInput(@PathVariable Long id , Model model){
        model.addAttribute("chapter",chapterService.getChapterById(id));
        return "teacher/tea-chapter-input";
    }

    //点击删除
    @GetMapping("/tea-chapter/{id}/delete")
    public String deleteChapter(@PathVariable Long id ,RedirectAttributes attributes){
        chapterService.deleteChapter(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/teacher/tea-chapter";
    }

    //提交新增内容
    @PostMapping("/tea-chapter-input")
    public String post(@Valid Chapter chapter, BindingResult result, RedirectAttributes attributes){

        Chapter c1=chapterService.getChapterByChapterName(chapter.getChapterName());
        if (c1!=null){
            result.rejectValue("chapterName","nameError","分类名称不能重复");
        }
        if (result.hasErrors()){
            return "teacher/tea-chapter-input";
        }
        Chapter c= chapterService.saveChapter(chapter);

        if (c==null){
            attributes.addFlashAttribute("message","添加失败");
        }else{
            attributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/teacher/tea-chapter";
    }

    //提交更新内容
    @PostMapping("/tea-chapter-input/{id}")
    public String editPost(@Valid Chapter chapter, BindingResult result,@PathVariable Long id ,RedirectAttributes attributes){

        Chapter c1=chapterService.getChapterByChapterName(chapter.getChapterName());

        if (c1!=null){
            result.rejectValue("chapterName","nameError","分类名称不能重复");
        }
        if (result.hasErrors()){
            return "teacher/tea-chapter-input";
        }
        Chapter c = chapterService.updateChapter(id,chapter);

        if (c==null){
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/teacher/tea-chapter";
    }















}
