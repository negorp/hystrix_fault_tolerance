package com.example.test.controller;

import com.example.test.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/test")
public class TestController {
    @Autowired
    TestService testService;

    @GetMapping
    public String testService(@RequestParam("url") String url) {
        return testService.processPage(url);
    }

}
