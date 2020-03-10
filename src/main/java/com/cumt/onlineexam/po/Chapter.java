package com.cumt.onlineexam.po;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_chapter")
public class Chapter {
    @Id
    @GeneratedValue
    private  Long id;
    private String chapterNum;
    private String chapterName;

    @OneToMany(mappedBy = "chapter")
    private List<Question> questions=new ArrayList<>();

    public Chapter() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChapterNum() {
        return chapterNum;
    }

    public void setChapterNum(String chapterNum) {
        this.chapterNum = chapterNum;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "id=" + id +
                ", chapterNum='" + chapterNum + '\'' +
                ", chapterName='" + chapterName + '\'' +
                ", questions=" + questions +
                '}';
    }
}
