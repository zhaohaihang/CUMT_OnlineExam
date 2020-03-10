package com.cumt.onlineexam.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class ImportTeacher {

    @Excel(name = "姓名", orderNum = "0")
    private String name;

    @Excel(name = "工号", orderNum = "1")
    private String teacherNum;

    @Excel(name = "密码", orderNum = "2")
    private String password;

    public ImportTeacher() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacherNum() {
        return teacherNum;
    }

    public void setTeacherNum(String teacherNum) {
        this.teacherNum = teacherNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
