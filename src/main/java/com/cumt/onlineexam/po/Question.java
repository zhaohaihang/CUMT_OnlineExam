package com.cumt.onlineexam.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_question")
public class Question {
    @Id
    @GeneratedValue
    private Long id;
    private String type;
    private String content;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private String choiceAnswer;
    private String checkAnswer;
    private String answerA;
    private String answerB;
    private String answerC;
    private String explan;
    private int diffcult;
    private int score;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @ManyToOne
    private Chapter chapter;

    @ManyToMany()
    private List<Point> points=new ArrayList<>();

    @ManyToOne
    private Teacher teacher;

    @ManyToMany(mappedBy = "questions")
    private List<Paper> papers =new ArrayList<>();

    @OneToMany(mappedBy = "question")
    private List<Student_Paper_Question> student_paper_questions =new ArrayList<>();

    @Transient
    private String pointIds;

    public Question() {
    }

    public String getPointIds() {
        return pointIds;
    }

    public void setPointIds(String pointIds) {
        this.pointIds = pointIds;
    }
    public void init(){
        this.pointIds=pointsToIds(this.getPoints());
    }

    private String pointsToIds(List<Point> points){
        if (!points.isEmpty()){
            StringBuffer ids=new StringBuffer();
            boolean flag=false;
            for (Point point : points){
                if (flag){
                    ids.append(",");
                }else{
                    flag=true;
                }
                ids.append(point.getId());
            }
            return  ids.toString();
        }else{
            return pointIds;
        }
    }

    public String getChoiceAnswer() {
        return choiceAnswer;
    }

    public void setChoiceAnswer(String choiceAnswer) {
        this.choiceAnswer = choiceAnswer;
    }



    public String getCheckAnswer() {
        return checkAnswer;
    }

    public void setCheckAnswer(String checkAnswer) {
        this.checkAnswer = checkAnswer;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChoiceA() {
        return choiceA;
    }

    public void setChoiceA(String choiceA) {
        this.choiceA = choiceA;
    }

    public String getChoiceB() {
        return choiceB;
    }

    public void setChoiceB(String choiceB) {
        this.choiceB = choiceB;
    }

    public String getChoiceC() {
        return choiceC;
    }

    public void setChoiceC(String choiceC) {
        this.choiceC = choiceC;
    }

    public String getChoiceD() {
        return choiceD;
    }

    public void setChoiceD(String choiceD) {
        this.choiceD = choiceD;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getExplan() {
        return explan;
    }

    public void setExplan(String explan) {
        this.explan = explan;
    }

    public int getDiffcult() {
        return diffcult;
    }

    public void setDiffcult(int diffcult) {
        this.diffcult = diffcult;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Paper> getPapers() {
        return papers;
    }

    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }

    public List<Student_Paper_Question> getStudent_paper_questions() {
        return student_paper_questions;
    }

    public void setStudent_paper_questions(List<Student_Paper_Question> student_paper_questions) {
        this.student_paper_questions = student_paper_questions;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", choiceA='" + choiceA + '\'' +
                ", choiceB='" + choiceB + '\'' +
                ", choiceC='" + choiceC + '\'' +
                ", choiceD='" + choiceD + '\'' +
                ", choiceAnswer='" + choiceAnswer + '\'' +
                ", checkAnswer='" + checkAnswer + '\'' +
                ", answerA='" + answerA + '\'' +
                ", answerB='" + answerB + '\'' +
                ", answerC='" + answerC + '\'' +
                ", explan='" + explan + '\'' +
                ", diffcult=" + diffcult +
                ", score=" + score +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", chapter=" + chapter +
                ", points=" + points +
                ", teacher=" + teacher +
                ", papers=" + papers +
                ", student_paper_questions=" + student_paper_questions +
                ", pointIds='" + pointIds + '\'' +
                '}';
    }
}
