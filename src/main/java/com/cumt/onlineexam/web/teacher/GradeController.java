package com.cumt.onlineexam.web.teacher;

import com.cumt.onlineexam.po.*;
import com.cumt.onlineexam.service.PaperService;
import com.cumt.onlineexam.service.StudentPaperQuestionService;
import com.cumt.onlineexam.service.StudentPaperService;
import com.cumt.onlineexam.service.StudentService;
import com.cumt.onlineexam.vo.GradeQuery;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher")
public class GradeController {

    @Autowired
    private StudentPaperService studentPaperService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private PaperService paperService;
    @Autowired
    private StudentPaperQuestionService studentPaperQuestionService;


    @GetMapping("/tea-grade")
    public String getgrade(@PageableDefault(size =20,sort = {"student_id"},direction = Sort.Direction.DESC) Pageable pageable
            , Model model) throws Exception{
        GradeQuery gradeQuery =new GradeQuery();
        gradeQuery.setStuname("");
        gradeQuery.setStugrade("");
        gradeQuery.setPapername("");
        gradeQuery.setStunum("");
        Page<Student_Paper> SPs =studentPaperService.getStudentPaperScore(pageable,gradeQuery);
        for(Student_Paper sp :SPs){
            sp.getPaper().init();
        }
        model.addAttribute("page",SPs);
        return "teacher/tea-grade";
    }

    @PostMapping("/tea-grade/search")
    public String search(@PageableDefault(size = 20,sort = {"student_id"},direction = Sort.Direction.DESC) Pageable pageable ,
                         GradeQuery gradeQuery, Model model) throws Exception{
        Page<Student_Paper> SPs =studentPaperService.getStudentPaperScore(pageable,gradeQuery);
        for(Student_Paper sp :SPs){
            sp.getPaper().init();
        }
        model.addAttribute("page",SPs);
        return "teacher/tea-grade :: gradeList";
    }

    @GetMapping("/{studentid}/{paperid}/tea-grade-see")
    public String compPaperReview(@PathVariable Long studentid,@PathVariable Long paperid , HttpSession session, Model model) throws Exception{
        Student student= studentService.getById(studentid);
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
        return "teacher/tea-grade-see";
    }


}
