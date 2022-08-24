package com.ll.exam.sbb.question;

import com.ll.exam.sbb.answer.AnswerForm;
import com.ll.exam.sbb.user.SiteUser;
import com.ll.exam.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    @RequestMapping("/list")
    // 이 자리에 @ResponseBody가 없으면 resources/question_list/question_list.html 파일을 뷰로 삼는다.
    public String list(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Question> paging = questionService.getList(page);

        // 미래에 실행된 question_list.html 에서
        // questionList 라는 이름으로 questionList 변수를 사용할 수 있다.
        model.addAttribute("paging", paging);
        return "question_list";
    }


    @RequestMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") long id, AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(Principal principal, @Valid QuestionForm questionForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        SiteUser siteUser = userService.getUser(principal.getName());

        questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
        return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
    }
}
