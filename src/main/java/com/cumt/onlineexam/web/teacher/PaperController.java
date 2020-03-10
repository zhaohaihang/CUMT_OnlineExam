package com.cumt.onlineexam.web.teacher;

import com.cumt.onlineexam.dao.PaperRepository;
import com.cumt.onlineexam.po.Paper;
import com.cumt.onlineexam.po.Question;
import com.cumt.onlineexam.po.Teacher;
import com.cumt.onlineexam.service.*;
import com.cumt.onlineexam.vo.PaperQuery;
import com.cumt.onlineexam.vo.PaperQuestions;
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
public class PaperController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private PaperService paperService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private PointService pointService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private QuestionService questionService;


    //进入试卷列表
    @GetMapping("/tea-paper")
    public String paper(@PageableDefault(size =20,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model )throws Exception {
        model.addAttribute("teachers",teacherService.listTeacher());
        Page<Paper> papers =paperService.listPaper(pageable);
        for (Paper paper : papers){
            paper.init();
        }
        model.addAttribute("page",papers);
        return "teacher/tea-paper";
    }

    //点击新增，进入新增页
    @GetMapping("/tea-paper/input")
    public String input(Model model){
        model.addAttribute("grades",studentService.listGrade());
        model.addAttribute("paper",new Paper());
        return "teacher/tea-paper-input";
    }

    //点击编辑，进入编辑页
    @GetMapping("/tea-paper/{id}/input")
    public  String  editInput(@PathVariable Long id , Model model){
        model.addAttribute("grades",studentService.listGrade());
        model.addAttribute("paper",paperService.getPaperById(id));
        return "teacher/tea-paper-input";
    }
    //点击题目，进入题目详情页
    @GetMapping("/tea-paper/{id}/question-input")
    public  String  question(@PathVariable Long id ,Model model)  throws Exception{

        //model.addAttribute("paperId",paperid);
        Paper paper = paperService.getPaperById(id);
        paper.init();
        model.addAttribute("paper",paper);
        return "teacher/tea-paper-question-input";
    }

    //点击删除
    @GetMapping("/tea-paper/{id}/delete")
    public  String deletePaper(@PathVariable Long id, RedirectAttributes attributes){
        paperService.deletePaper(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/teacher/tea-paper";
    }

    //点击添加题目，弹出窗口
    @GetMapping("/tea-paper/{id}/tea-paper-question")
    public  String addquestion(@PathVariable Long id, Model model){

        return "redirect:/teacher/tea-paper-question/"+id.toString();
    }

    //显示窗口中的题目列表
    @GetMapping("/tea-paper-question/{paperid}")
    public  String getquestion( @PathVariable Long paperid,@PageableDefault(size = 20,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,Model model){

        model.addAttribute("paperquestions",new PaperQuestions());
        model.addAttribute("paperId",paperid);
        model.addAttribute("points",pointService.listPoint());
        model.addAttribute("chapters",chapterService.listChapter());
        model.addAttribute("teachers",teacherService.listTeacher());
        Page<Question> questions= questionService.listQuestion(pageable);
        model.addAttribute("page",questions);
        return "/teacher/tea-paper-question";
    }

    //点击加入，将题目加入试卷
    @PostMapping("/tea-question/{paperid}/addtopaper")
    public String  questionsaddtopaper(PaperQuestions pq, @PathVariable Long paperid){
        Long  pid=pq.getPid();
        String[] qids=pq.getQids().split(",");
        for (String qid : qids){
            if (qid.equals(""))
                continue;
            paperService.addquestion(pid,Long.parseLong(qid));
        }
        return "redirect:/teacher/tea-paper/"+paperid.toString()+"/tea-paper-question";
    }


    //点击删除，将题目从试卷中删除
    @GetMapping("/tea-question/{paperid}/{questionid}/deletetopaper")
    public String  questiondeletetopaper(@PathVariable Long paperid,@PathVariable Long questionid){

        paperService.deletequestion(paperid,questionid);
        //return "redirect:/teacher/tea-paper/"+paperid.toString()+"/tea-paper-question";
        return "redirect:/teacher/tea-paper/"+paperid.toString()+"/question-input";
    }

    //新增页提交
    @PostMapping("/tea-paper-input")
    public String post(Paper paper, RedirectAttributes attributes, HttpSession session){

        paper.setTeacher((Teacher)session.getAttribute("teacher"));
        Paper p;
        if (paper.getId()==null){
            p=paperService.savePaper(paper);
        }else{
            p=paperService.updatePaper(paper.getId(),paper);
        }

        if (p==null){
            attributes.addFlashAttribute("message","操作失败");
        }else{
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/teacher/tea-paper";
    }

    //试卷查询
    @PostMapping("/tea-paper/search")
    public String search(@PageableDefault(size=20,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable ,
                         PaperQuery paperquesry, Model model) throws Exception{
        Page<Paper> papers = paperService.listPaper(pageable,paperquesry);
        for (Paper paper : papers){
            paper.init();
        }
        model.addAttribute("page",papers);
        return "teacher/tea-paper :: paperList";
    }

    //弹窗的题目查询
    @PostMapping("/{paperid}/tea-paper-question/search")
    public String search(@PathVariable Long paperid,@PageableDefault(size=20,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable , QuestionQuery question, Model model){
        model.addAttribute("paperId",paperid);
        model.addAttribute("page",questionService.listQuestion(pageable,question));
        return "teacher/tea-paper-question :: questionList";
    }


}
