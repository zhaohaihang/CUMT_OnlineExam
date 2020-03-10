package com.cumt.onlineexam.po;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(Student_Paper_Question_Key.class)
@Table(name = "t_student_paper_question")
public class Student_Paper_Question implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;

    @Id
    @ManyToOne
    @JoinColumn(name="paper_id")
    private Paper paper;

    @Id
    @ManyToOne
    @JoinColumn(name="question_id")
    private Question question;

    private String answer;
    private Integer correct;

    public Student_Paper_Question() {
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }

    @Override
    public String toString() {
        return "Student_Paper_Question{" +
                "student=" + student +
                ", paper=" + paper +
                ", question=" + question +
                ", answer='" + answer + '\'' +
                ", correct=" + correct +
                '}';
    }
}
