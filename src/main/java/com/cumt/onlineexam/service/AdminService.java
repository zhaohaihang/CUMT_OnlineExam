package com.cumt.onlineexam.service;

import com.cumt.onlineexam.po.Admin;
import com.cumt.onlineexam.po.Student;
import org.springframework.stereotype.Service;


public interface AdminService {
    Admin checkAdmin(String username,String password);
    Admin updateAdmin(Long id,Admin admin);
    Admin getOneById(Long id);
}
