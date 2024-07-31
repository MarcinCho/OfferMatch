package com.marcincho.projects_sm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class testCotrolle {

    @GetMapping("/test")
    public String getMethodName() {
        return "test";
    }

}
