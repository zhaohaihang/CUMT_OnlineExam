package com.cumt.onlineexam.service;

import com.cumt.onlineexam.NotFoundException;
import com.cumt.onlineexam.dao.PointRepository;
import com.cumt.onlineexam.po.Point;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Service
public class PointServiceImpl implements PointService {

    @Autowired
    private PointRepository pointRepository;

    @Transactional
    @Override
    public Page<Point> listPoint(Pageable pageable) {
        return pointRepository.findAll(pageable);
    }

    @Override
    public List<Point> listPoint() {
        return pointRepository.findAll();
    }

    @Override
    public List<Point> listPoint(String ids) {
        return pointRepository.findAllById(converToList(ids));
    }

    private  List<Long> converToList(String ids){
        List<Long> list=new ArrayList<>();
        if (!"".equals(ids) && ids!=null){
            String [] idarray=ids.split(",");
            for (int i=0;i<idarray.length;i++){
                list.add(new Long (idarray[i]));
            }
        }
        return list;
    }

    @Override
    public Point getPointByName(String name) {
        return pointRepository.findByName(name);
    }

    @Transactional
    @Override
    public Point savePoint(Point point) {
        return pointRepository.save(point);
    }

    @Override
    public Point getPointById(Long id) {
        return pointRepository.getOne(id);
    }

    @Override
    public void deletePoint(Long id) {
        pointRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Point updatePoint(Long id, Point point) {
       Point p =pointRepository.getOne(id);
       if (p==null){
           throw new NotFoundException("不存在该标签");
       }
        BeanUtils.copyProperties(point,p);
        return pointRepository.save(p);
    }
}
