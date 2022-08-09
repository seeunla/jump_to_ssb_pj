package com.ll.exam.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MainController {
    private int i =-1;
    @RequestMapping("/sbb")
    @ResponseBody
    public String index() {
        System.out.println("Hello");
        return "ㅎㅇ";
    }

    @GetMapping("/page1")
    @ResponseBody
    public String showMain() {
        return """
                <form method="POST" action="/page2">
                    <input type="number" name="age" placeholder="나이" />
                    <input type="submit" value="page2로 POST 방식으로 이동" />
                </form>
                """;
    }

    @PostMapping("/page2")
    @ResponseBody
    public String showPage2Post(@RequestParam(defaultValue = "0") int age) {
        return """
                <h1>입력된 나이 : %d</h1>
                <h1>안녕하세요, POST 방식으로 오셨군요.</h1>
                """.formatted(age);
    }

    @GetMapping("/page2")
    @ResponseBody
    public String showPage2Get(@RequestParam(defaultValue = "0") int age) {
        return """
                <h1>입력된 나이 : %d</h1>
                <h1>안녕하세요, Get 방식으로 오셨군요.</h1>
                """.formatted(age);
    }

    @GetMapping("/plus")
    @ResponseBody
    public int plus(int a, int b) {
        return a+b;
    }

    @GetMapping("/minus")
    @ResponseBody
    public int minus(int a, int b) {
        return a-b;
    }


    @GetMapping("/increase")
    @ResponseBody
    public int showIncrease() {
        i++;
        return i;
    }

    @GetMapping("/decrease")
    @ResponseBody
    public int showdecrease() {
        i--;
        return i;
    }

    @GetMapping("/mbti")
    @ResponseBody
    public String mbti(String name) {
        name = "홍길동";
        String mbti = "infp";
        return mbti;
    }

    @GetMapping("/gugudan")
    @ResponseBody
    public String gugudan(Integer dan, Integer limit) {
        if(limit == null ) {
            limit = 9;
        }

        if(dan == null) {
            dan = 9;
        }
        Integer finalDan = dan;
        return IntStream.rangeClosed(1,limit)
                .mapToObj(i -> "%d * %d = %d".formatted(finalDan, i , finalDan * i))
                .collect(Collectors.joining("<br>\n"));
    }

    @GetMapping("/mbti/{name}")
    @ResponseBody
    public String mbti2(@PathVariable String name) {
        return switch (name) {
            case "홍길동" -> "infp";
            case "홍길순" -> "infj";
            case "김세은" -> "intj";
            default -> "모름";
        };
    }

    @GetMapping("/saveSession/{name}/{value}")
    @ResponseBody
    public String saveSession(@PathVariable String name, @PathVariable String value, HttpServletRequest req) {
        HttpSession session = req.getSession();
        // req -> 쿠키가 있다. -> JSESSIONID => 세션을 얻을 수 있다.

        session.setAttribute(name, value);
        return "세션변수 %s의 값이 %s(으)로 설정되었습니다.".formatted(name,value);
    }

    @GetMapping("/getSession/{name}")
    @ResponseBody
    public String save2(@PathVariable String name, HttpSession session) {
        String value = (String) session.getAttribute(name);

        return "세션변수 %s의 값이 %s 입니다.".formatted(name, value);
    }


}
