package com.cumt.onlineexam.service;

import com.cumt.onlineexam.po.Chapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ChapterService {
    List<Chapter> listChapter();
    Page<Chapter> listChapter(Pageable pageable);
    Chapter getChapterByChapterName(String chaptername);
    Chapter saveChapter(Chapter chapter);
    Chapter getChapterById(Long id);
    Chapter updateChapter(Long id ,Chapter chapter);
    void deleteChapter(Long id);
}
