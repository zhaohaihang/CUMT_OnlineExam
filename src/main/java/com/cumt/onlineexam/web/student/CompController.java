package com.cumt.onlineexam.web.student;

import com.cumt.onlineexam.dao.StudentPaperRepository;
import com.cumt.onlineexam.po.*;
import com.cumt.onlineexam.service.PaperService;
import com.cumt.onlineexam.service.StudentPaperQuestionService;
import com.cumt.onlineexam.service.StudentPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javafx.scene.input.KeyCode.M;

@Controller
@RequestMapping("/student")
public class CompController {


    @Autowired
    private PaperService paperService;

    @Autowired
    private StudentPaperService studentPaperService;

    @Autowired
    private StudentPaperQuestionService studentPaperQuestionService;

    //进入已完成试卷列表
    @GetMapping("/stu-comp-paper")
    public  String getcompPaper(@PageableDefault(size = 20,sort = {"student_id"},direction = Sort.Direction.DESC) Pageable pageable, Model model, HttpSession session) throws Exception {
        Student student= (Student)session.getAttribute("student");
        Page<Student_Paper> SPs=studentPaperService.getStudentPaperScore(pageable,student);
        for(Student_Paper sp :SPs){
            sp.getPaper().init();
        }
        model.addAttribute("page",SPs);
        return "student/stu-comp-paper";
    }

    //点击回顾
    @GetMapping("/{paperid}/stu-comp-paper-review")
    public String compPaperReview(@PathVariable Long paperid ,HttpSession session,Model model) throws Exception{
        Student student= (Student)session.getAttribute("student");
        Paper paper = paperService.getPaperById(paperid);
        paper.init();
        List<Question> questions = paper.getQuestions();

        Student_Paper studentpaper= studentPaperService.getSPByStudentAndPaper(student,paper);

        Map<Question, Student_Paper_Question> selectquestionmap=new HashMap<>();
        Map<Question, Student_Paper_Question> checkquestionmap=new HashMap<>();
        Map<Question, Student_Paper_Question> blankquestionmap=new HashMap<>();

        for (Question question : questions) {

            if (question.getType().equals("选择题")) {
                selectquestionmap.put(question,
                        studentPaperQuestionService.getSPByStudentAndPaperAndQuestion(student,paper,question));
            } else if (question.getType().equals("判断题")) {
                checkquestionmap.put(question,
                        studentPaperQuestionService.getSPByStudentAndPaperAndQuestion(student,paper,question));
            } else {
                blankquestionmap.put(question,
                        studentPaperQuestionService.getSPByStudentAndPaperAndQuestion(student,paper,question));
            }
        }
        model.addAttribute("paper", paper);
        model.addAttribute("studentpaper", studentpaper);
        model.addAttribute("selectquestionmap", selectquestionmap);
        model.addAttribute("checkquestionmap", checkquestionmap);
        model.addAttribute("blankquestionmap", blankquestionmap);
        return "student/stu-comp-paper-review";
    }



}
