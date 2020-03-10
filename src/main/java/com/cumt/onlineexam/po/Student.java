package com.cumt.onlineexam.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_student")
public class Student {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String studentNum;
    private String password;
    private String grade;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @ManyToOne
    private Teacher teacher;

    @OneToMany(mappedBy = "student")
    private List<Student_Paper> student_papers=new ArrayList<>();

    @OneToMany(mappedBy = "student")
    private List<Student_Paper_Question> student_paper_questions=new ArrayList();

    public Student() {
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

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
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

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", studentNum='" + studentNum + '\'' +
                ", password='" + password + '\'' +
                ", grade='" + grade + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", teacher=" + teacher +
                ", student_papers=" + student_papers +
                ", student_paper_questions=" + student_paper_questions +
                '}';
    }
}
