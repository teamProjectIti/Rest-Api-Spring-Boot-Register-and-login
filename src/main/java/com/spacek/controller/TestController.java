package com.spacek.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/test/")
public class TestController {
    @GetMapping("/")
    public Object test() {
        return "this is test page";
    }
}
