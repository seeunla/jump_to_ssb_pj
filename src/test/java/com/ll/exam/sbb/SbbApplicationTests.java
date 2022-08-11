package com.ll.exam.sbb;

import com.ll.exam.sbb.question.Question;
import com.ll.exam.sbb.question.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.ConstraintMode;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {
	@Autowired
	private QuestionRepository questionRepository;
	private int lastSampleDataId;

	@Test
	void contextLoads() {
	}

	@BeforeEach
	void beforeEach() {
		clearData();
		createSampleData();
	}

	private void createSampleData() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		questionRepository.save(q2);

		lastSampleDataId = q2.getId();
	}

	private void clearData() {
		questionRepository.disableForeignKeyChecks();
		questionRepository.beforeAll();
		questionRepository.enableForeignKeyChecks();
	}

	@Test
	private void 저장() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		questionRepository.save(q2);

		assertThat(q1.getId()).isEqualTo(lastSampleDataId +1);
		assertThat(q2.getId()).isEqualTo(lastSampleDataId +2);
	}

	@Test
	private void 삭제() {
		assertThat(questionRepository.count()).isEqualTo(lastSampleDataId);

		Question q = this.questionRepository.findById(1).get();
		questionRepository.delete(q);

		assertThat(questionRepository.count()).isEqualTo(lastSampleDataId - 1);
	}

	@Test
	void 수정() {
		Question q = this.questionRepository.findById(1).get();
		q.setSubject("수정된 제목");
		questionRepository.save(q);

		q = this.questionRepository.findById(1).get();

		assertThat(q.getSubject()).isEqualTo("수정된 제목");
	}

	@Test
	void findAll() {
		List<Question> all = questionRepository.findAll();
		assertThat(all.size()).isEqualTo(lastSampleDataId);

		Question q = all.get(0);
		assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
	}

	@Test
	void findBySubject() {
		Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
		assertThat(q.getId()).isEqualTo(1);
	}

	@Test
	void findBySubjectAndContent() {
		Question q = questionRepository.findBySubjectAndContent(
				"sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		assertThat(q.getId()).isEqualTo(1);
	}

	@Test
	void findBySubjectLike() {
		List<Question> qList = questionRepository.findBySubjectLike("sbb%");
		Question q = qList.get(0);

		assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
	}

}
