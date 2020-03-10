package com.cumt.onlineexam.service;

import com.cumt.onlineexam.po.Point;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PointService {
    List<Point> listPoint();
    List<Point> listPoint(String ids);
    Page <Point> listPoint(Pageable pageable);
    Point getPointByName(String name);
    Point savePoint(Point point);
    Point getPointById(Long id);
    Point updatePoint(Long id,Point point);
    void deletePoint(Long id);
}
