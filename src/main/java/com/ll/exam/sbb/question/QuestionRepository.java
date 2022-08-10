package com.ll.exam.sbb.question;

import com.ll.exam.sbb.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    public Question findBySubjectAndContent(String subject, String content);
    public Question findBySubject(String q);
}
