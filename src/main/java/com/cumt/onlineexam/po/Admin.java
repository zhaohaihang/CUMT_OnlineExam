package com.cumt.onlineexam.po;

import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_admin")
public class Admin {
    @Id
    @GeneratedValue
    private Long id;
    private String adminNum;
    private String password;

    public Admin() {
    }

    public String getAdminNum() {
        return adminNum;
    }

    public void setAdminNum(String adminNum) {
        this.adminNum = adminNum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", admin='" + adminNum + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
