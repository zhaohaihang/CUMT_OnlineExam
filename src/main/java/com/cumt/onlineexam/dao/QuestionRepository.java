package com.cumt.onlineexam.dao;

import com.cumt.onlineexam.po.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QuestionRepository extends JpaRepository<Question,Long >, JpaSpecificationExecutor<Question> {

}
