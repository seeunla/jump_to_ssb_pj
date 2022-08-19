package com.ll.exam.sbb;

import com.ll.exam.sbb.answer.AnswerRepository;
import com.ll.exam.sbb.question.QuestionRepository;
import com.ll.exam.sbb.user.SiteUser;
import com.ll.exam.sbb.user.UserRepository;
import com.ll.exam.sbb.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerRepository answerRepository;

    @BeforeEach
    void beforeEach() {
        clearData();
        createSampleData();
    }

    private void clearData() {
        clearData(userRepository, answerRepository, questionRepository);
    }

    private static void clearData(UserRepository userRepository, AnswerRepository answerRepository, QuestionRepository questionRepository) {
        AnswerRepositoryTests.clearData(answerRepository, questionRepository);
        QuestionRepositoryTests.clearData(questionRepository);
        userRepository.deleteAll();
        userRepository.truncate();
    }

    public static void createSampleData(UserService userService) {
        userService.create("admin","admin@test.com","1234");
        userService.create("user1","user1@test.com","1234");
    }

    private void createSampleData() {
        createSampleData(userService);
    }


    @Test
    @DisplayName("회원가입이 가능하다.")
    public void t1() {
        userService.create("user2", "user2@email.com", "1234");
    }


}
