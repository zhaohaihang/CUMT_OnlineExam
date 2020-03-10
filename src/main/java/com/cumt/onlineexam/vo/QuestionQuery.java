package com.cumt.onlineexam.vo;

public class QuestionQuery {
    private String type;
    private Long pointId;
    private  Long chapterId;
    private Long diffcult;
    private Long teacherId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPointId() {
        return pointId;
    }

    public void setPointId(Long pointId) {
        this.pointId = pointId;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public Long getDiffcult() {
        return diffcult;
    }

    public void setDiffcult(Long diffcult) {
        this.diffcult = diffcult;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}
