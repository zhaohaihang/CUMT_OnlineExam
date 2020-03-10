package com.cumt.onlineexam.dao;

import com.cumt.onlineexam.po.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {

    Admin findByAdminNumAndPassword(String admin_num,String password);
}
