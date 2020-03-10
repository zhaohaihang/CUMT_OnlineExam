package com.cumt.onlineexam.dao;

import com.cumt.onlineexam.po.Point;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point,Long> {
    Point findByName(String name);
}
