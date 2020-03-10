package com.cumt.onlineexam.service;

import com.cumt.onlineexam.NotFoundException;
import com.cumt.onlineexam.dao.AdminRepository;
import com.cumt.onlineexam.po.Admin;
import com.cumt.onlineexam.utils.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin checkAdmin(String username, String password) {
        Admin admin =adminRepository.findByAdminNumAndPassword(username,password);
        return admin;
    }

    @Override
    public Admin updateAdmin(Long id, Admin admin) {
        Admin a =adminRepository.getOne(id);
        if (a==null){
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(admin,a, MyBeanUtils.getNullPropertyNames(admin));
        return adminRepository.save(a);
    }

    @Override
    public Admin getOneById(Long id) {
        return adminRepository.getOne(id);
    }
}
