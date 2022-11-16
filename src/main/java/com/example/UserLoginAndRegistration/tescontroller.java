package com.example.UserLoginAndRegistration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class tescontroller {
    @GetMapping
    public String hello(){
        return "hello world";
    }
}
