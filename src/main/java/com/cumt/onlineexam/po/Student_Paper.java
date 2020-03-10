package com.cumt.onlineexam.po;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@IdClass(Student_Paper_Key.class)
@Table(name = "t_student_paper")
public class Student_Paper implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;

    @Id
    @ManyToOne
    @JoinColumn(name="paper_id")
    private Paper paper;
    private Date submitTime;
    private int score;

    public Student_Paper() {
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

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student_Paper{" +
                "student=" + student +
                ", paper=" + paper +
                ", submitTime=" + submitTime +
                ", score=" + score +
                '}';
    }
}
