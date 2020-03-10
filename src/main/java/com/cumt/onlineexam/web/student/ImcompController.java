package com.cumt.onlineexam.web.student;

import com.cumt.onlineexam.po.*;
import com.cumt.onlineexam.service.*;
import com.cumt.onlineexam.vo.Answer;
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

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/student")
public class ImcompController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private PaperService paperService;

    @Autowired
    private StudentPaperQuestionService studentPaperQuestionService;

    @Autowired
    private StudentPaperService studentPaperService;

    //进入未完成试卷列表
    @GetMapping("/stu-imcomp-paper")
    public String getImcompPaper(@PageableDefault(size = 20, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                                 Model model, HttpSession session) throws Exception {
        Student student = (Student) session.getAttribute("student");
        Page<Paper> papers = paperService.listImcompPaper(student, pageable);
        for (Paper paper : papers) {
            paper.init();
        }
        model.addAttribute("page", papers);
        return "student/stu-imcomp-paper";
    }

    //点击进入，进入试卷页面
    @GetMapping("/{paperid}/stu-imcomp-paper-fill")
    public String fillPaper(@PathVariable Long paperid, Model model, HttpSession session) throws Exception {

        Paper paper = paperService.getPaperById(paperid);
        paper.init();
        List<Question> questions = paper.getQuestions();
        List<Question> selectquestions = new ArrayList<>();
        List<Question> checkquestions = new ArrayList<>();
        List<Question> blankquestions = new ArrayList<>();
        for (Question question : questions) {
            if (question.getType().equals("选择题")) {
                selectquestions.add(question);
            } else if (question.getType().equals("判断题")) {
                checkquestions.add(question);
            } else {
                blankquestions.add(question);
            }
        }
        model.addAttribute("paper", paper);
        model.addAttribute("selectquestions", selectquestions);
        model.addAttribute("checkquestions", checkquestions);
        model.addAttribute("blankquestions", blankquestions);

        return "student/stu-imcomp-paper-fill";
    }

    //点击提交，提交答案
    @PostMapping("/{paperid}/stu-imcomp-paper-fill")
    public String submitanswer(@PathVariable Long paperid, Answer answer, HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        Paper paper = paperService.getPaperById(paperid);
        int sumscore = 0;
        Long s_id = student.getId();
        Long p_id = paperid;

        //选择题答案
        String allanswer = answer.getAllanswer();//20#A,23#B
        System.out.println(allanswer);

        if (allanswer != null && !"".equals(allanswer)) {
            String allanswers[] = allanswer.split(",");
            for (String sa : allanswers) {
                System.out.println(sa);
                if (sa.equals(""))
                    continue;
                String qid_ans[] = sa.split("#");
                Long q_id = Long.parseLong(qid_ans[0]);
                String ans = qid_ans[1];
                Question question = questionService.getQuestionById(q_id);
                String que_type = question.getType();
                String tureans;
                int correct;
                if (que_type.equals("选择题")) {//根据题型得到答案
                    tureans = question.getChoiceAnswer();
                } else if (que_type.equals("判断题")) {
                    tureans = question.getCheckAnswer();
                } else {
                    tureans = question.getAnswerA() + question.getAnswerB() + question.getAnswerC();
                }
                if (tureans.contains(ans)) {//判断对错
                    correct = 1;
                    sumscore+=question.getScore();
                } else {
                    correct = 0;
                }
                studentPaperQuestionService.saveanswer(s_id, p_id, q_id, ans, correct);
            }
            studentPaperService.savesumscore(s_id,p_id,sumscore);
        }
        return "redirect:/student/stu-comp-paper";
    }
}
