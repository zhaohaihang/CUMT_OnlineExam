package com.cumt.onlineexam.web.teacher;

import com.cumt.onlineexam.po.Chapter;
import com.cumt.onlineexam.po.Point;
import com.cumt.onlineexam.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/teacher")
public class PointController {

    @Autowired
    private PointService pointService;

    //知识点列表
    @GetMapping("/tea-point")
    public String point(@PageableDefault(size =20,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",pointService.listPoint(pageable));
        return "teacher/tea-point";
    }

    //点击新增，进入新增页
    @GetMapping("/tea-point/input")
    public String input(Model model){
        model.addAttribute("point",new Point());
        return "teacher/tea-point-input";
    }

    //点击编辑，进入编辑页
    @GetMapping("/tea-point/{id}/input")
    public  String  editInput(@PathVariable Long id , Model model){
        model.addAttribute("point",pointService.getPointById(id));
        return "teacher/tea-point-input";
    }

    //点击删除
    @GetMapping("/tea-point/{id}/delete")
    public  String deletePoint(@PathVariable Long id,RedirectAttributes attributes){
        pointService.deletePoint(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/teacher/tea-point";
    }


    //提交新增内容
    @PostMapping("/tea-point-input")
    public String post(@Valid Point point, BindingResult result, RedirectAttributes attributes){
        Point p1 = pointService.getPointByName(point.getName());
        if (p1!=null){
            result.rejectValue("name","nameError","分类名称不能重复");
        }
        if (result.hasErrors()){
            return "teacher/tea-point-input";
        }

         Point p= pointService.savePoint(point);

        if (p==null){
            attributes.addFlashAttribute("message","添加失败");
        }else{
            attributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/teacher/tea-point";
    }

    //提交更新内容
    @PostMapping("/tea-point-input/{id}")
    public String editPost(@Valid Point point, BindingResult result,@PathVariable Long id ,RedirectAttributes attributes){

        Point p1=pointService.getPointByName(point.getName());

        if (p1!=null){
            result.rejectValue("name","nameError","分类名称不能重复");
        }
        if (result.hasErrors()){
            return "teacher/tea-point-input";
        }
        Point p = pointService.updatePoint(id,point);

        if (p==null){
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/teacher/tea-point";
    }
}
