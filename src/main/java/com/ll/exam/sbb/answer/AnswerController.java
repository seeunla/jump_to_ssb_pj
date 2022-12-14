package com.ll.exam.sbb.answer;

import com.ll.exam.sbb.exception.DataNotFoundException;
import com.ll.exam.sbb.question.QuestionForm;
import com.ll.exam.sbb.question.QuestionService;
import com.ll.exam.sbb.question.Question;
import com.ll.exam.sbb.user.SiteUser;
import com.ll.exam.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(
            Principal principal, Model model, @PathVariable("id") long id, @Valid AnswerForm answerForm, BindingResult bindingResult) {
        Question question = this.questionService.getQuestion(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }

        SiteUser siteUser = userService.getUser(principal.getName());

        this.answerService.create(question, answerForm.getContent(), siteUser);
        return  "redirect:/question/detail/%d".formatted(id);
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answerModify(Principal principal, AnswerForm answerForm, @PathVariable("id") Long id) {
        Answer answer = answerService.getAnswer(id);

        if ( answer == null) {
            throw new DataNotFoundException("???????????? ????????????.");
        }

        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "?????? ????????? ????????????.");
        }

        answerForm.setContent(answer.getContent());

        return "answer_form";

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(Principal principal, @Valid AnswerForm answerForm, BindingResult bindingResult,
    @PathVariable("id") Long id) {
        if ( bindingResult.hasErrors()) {
            return "answer_form";
        }

        Answer answer =answerService.getAnswer(id);
        if(!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "??????????????? ????????????.");
        }

        answerService.modify(answer, answerForm.getContent());

        return "redirect:/question/detail/%d".formatted(id);
    }

}
