package com.ll.exam.sbb.question;

import com.ll.exam.sbb.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    public Question findById(int id);
}
