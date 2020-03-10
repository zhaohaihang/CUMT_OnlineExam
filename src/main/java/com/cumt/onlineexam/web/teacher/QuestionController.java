package com.cumt.onlineexam.web.teacher;

import com.cumt.onlineexam.po.Question;
import com.cumt.onlineexam.po.Teacher;
import com.cumt.onlineexam.service.ChapterService;
import com.cumt.onlineexam.service.PointService;
import com.cumt.onlineexam.service.QuestionService;
import com.cumt.onlineexam.service.TeacherService;
import com.cumt.onlineexam.vo.QuestionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/teacher")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private PointService pointService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private TeacherService teacherService;

    //进入题目列表
    @GetMapping("/tea-question")
    public String question (@PageableDefault(size = 20,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,  Model model ){
        model.addAttribute("points",pointService.listPoint());
        model.addAttribute("chapters",chapterService.listChapter());
        model.addAttribute("teachers",teacherService.listTeacher());
        Page<Question> questions= questionService.listQuestion(pageable);
        for(Question question :questions){
            question.init();
        }
        model.addAttribute("page",questions);
        return "teacher/tea-question";
    }

    //点击新增，进入新增页
    @GetMapping("/tea-question/input")
    public String input(Model model){
        model.addAttribute("points",pointService.listPoint());
        model.addAttribute("chapters",chapterService.listChapter());
        model.addAttribute("question",new Question());
        return "teacher/tea-question-input";
    }

    //点击编辑，进入编辑页
    @GetMapping("/tea-question/{id}/input")
    public  String  editInput(@PathVariable Long id , Model model){
        model.addAttribute("points",pointService.listPoint());
        model.addAttribute("chapters",chapterService.listChapter());
        Question question =questionService.getQuestionById(id);
        question.init();
        model.addAttribute("question",question);
        return "teacher/tea-question-input";
    }

    //点击删除
    @GetMapping("/tea-question/{id}/delete")
    public  String deletePoint(@PathVariable Long id,RedirectAttributes attributes){
        questionService.deleteQuestion(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/teacher/tea-question";
    }
    //新增页提交
    @PostMapping("/tea-question-input")
    public String post(Question question, RedirectAttributes attributes, HttpSession session){
        question.setTeacher((Teacher)session.getAttribute("teacher"));
        question.setChapter(chapterService.getChapterById(question.getChapter().getId()));
        question.setPoints(pointService.listPoint(question.getPointIds()));

        Question q;
        if (question.getId()==null){
            q=questionService.saveQuestion(question);
        }else{
            q=questionService.updateQuestion(question.getId(),question);
        }
        if (q==null){
            attributes.addFlashAttribute("message","操作失败");
        }else{
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/teacher/tea-question";
    }

    //组合查询
    @PostMapping("/tea-question/search")
    public String search(@PageableDefault(size=20,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable , QuestionQuery question, Model model){
        model.addAttribute("page",questionService.listQuestion(pageable,question));
        return "teacher/tea-question :: questionList";
    }


}
