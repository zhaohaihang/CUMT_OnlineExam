package com.cumt.onlineexam.service;

import com.cumt.onlineexam.NotFoundException;
import com.cumt.onlineexam.dao.ChapterRepository;
import com.cumt.onlineexam.po.Chapter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterRepository chapterRepository;

    @Transactional
    @Override
    public Page<Chapter> listChapter(Pageable pageable) {
        return chapterRepository.findAll(pageable);
    }

    @Override
    public List<Chapter> listChapter() {
        return chapterRepository.findAll();
    }

    @Transactional
    @Override
    public Chapter getChapterByChapterName(String chaptername) {
        return chapterRepository.findByChapterName(chaptername);
    }

    @Override
    public Chapter saveChapter(Chapter chapter) {
        return chapterRepository.save(chapter);
    }

    @Override
    public Chapter getChapterById(Long id) {
        return chapterRepository.getOne(id);
    }

    @Transactional
    @Override
    public Chapter updateChapter(Long id, Chapter chapter) {

        Chapter c=chapterRepository.getOne(id);
        if (c==null){
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(chapter,c);
        return chapterRepository.save(c);
    }

    @Override
    public void deleteChapter(Long id) {
        chapterRepository.deleteById(id);
    }
}
