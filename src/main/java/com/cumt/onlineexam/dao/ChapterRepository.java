package com.cumt.onlineexam.dao;

import com.cumt.onlineexam.po.Chapter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter,Long> {
    Chapter findByChapterName(String chaptername);
}
