package com.cumt.onlineexam.po;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_paper")
public class Paper {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    private String grade;

    @Transient
    private int totalQue;
    @Transient
    private int totalScore;

    private String startTime;

    private String endTime;

    @Transient
    private String status;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date updateTime;

    @ManyToMany
    private List<Question> questions=new ArrayList<>();

    //创建人
    @ManyToOne
    private Teacher teacher;

    @OneToMany(mappedBy = "paper")
    private List<Student_Paper> student_papers=new ArrayList<>();

    @OneToMany(mappedBy = "paper")
    private List<Student_Paper_Question> student_paper_questions =new ArrayList<>();

    public Paper() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getTotalQue() {
        return totalQue;
    }

    public void setTotalQue(int totalQue) {
        this.totalQue = totalQue;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Student_Paper> getStudent_papers() {
        return student_papers;
    }

    public void setStudent_papers(List<Student_Paper> student_papers) {
        this.student_papers = student_papers;
    }

    public List<Student_Paper_Question> getStudent_paper_questions() {
        return student_paper_questions;
    }

    public void setStudent_paper_questions(List<Student_Paper_Question> student_paper_questions) {
        this.student_paper_questions = student_paper_questions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void init() throws Exception{
        this.status=initstatus();
        this.totalQue=initQue(this.getQuestions());
        this.totalScore=initScore(this.getQuestions());
    }
    public int initQue(List<Question> questions){
       return questions.size();
    }
    public int initScore(List<Question> questions){
        int sum=0;
        for(Question question : questions){
            sum+=question.getScore();
        }
        return sum;
    }

    public String initstatus() throws Exception{
        String starttime=this.getStartTime();
        String endtime=this.getEndTime();

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date st = sdf2.parse(starttime);
        Date et =sdf2.parse(endtime);
        Date nt =new Date();
        if(nt.before(st)){
            return "未开始";
        }else if (nt.after(st)&&nt.before(et)){
            return "正在进行";
        }else{
            return "已结束";
        }
    }

    @Override
    public String toString() {
        return "Paper{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                ", totalQue=" + totalQue +
                ", totalScore=" + totalScore +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", questions=" + questions +
                ", teacher=" + teacher +
                ", student_papers=" + student_papers +
                ", student_paper_questions=" + student_paper_questions +
                '}';
    }
}
