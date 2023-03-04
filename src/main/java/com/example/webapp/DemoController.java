package com.example.webapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DemoController {
    @GetMapping(path = "hello", produces = "text/html")
    public String hello() {
        return "Hello!!!";
    }

//    @GetMapping(path = "success", produces = "text/html")
//    public String success() {
//        return "成功!!!";
//    }

    @GetMapping(path = "page1", produces = "text/html")
    public String page1() {
        return "ページ１";
    }
}