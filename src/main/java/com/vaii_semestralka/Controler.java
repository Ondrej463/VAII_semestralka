package com.vaii_semestralka;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
class Controler {
    @GetMapping("/")
    @ResponseBody
    public String getIndex() {
        return "index.html";
    }
}
