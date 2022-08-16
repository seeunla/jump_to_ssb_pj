package com.ll.exam.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    private int i =-1;
    @RequestMapping("/sbb")
    @ResponseBody
    public String index() {
        System.out.println("Hello");
        return "ㅎㅇ";
    }

}

