package com.cumt.onlineexam.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class ImportStudent {
    @Excel(name = "姓名", orderNum = "0")
    private String name;

    @Excel(name = "学号", orderNum = "1")
    private String studentNum;

    @Excel(name = "年级", orderNum = "2")
    private String grade;

    @Excel(name = "密码", orderNum = "3")
    private String password;

    @Excel(name = "老师", orderNum = "4")
    private String teacher;


    public ImportStudent() {
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
