package com.cumt.onlineexam.vo;

public class PaperQuery {
    private String paperName;
    private Long teacherId;

    public PaperQuery() {
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}
