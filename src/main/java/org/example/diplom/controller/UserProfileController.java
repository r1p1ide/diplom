package org.example.diplom.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserProfileController {

    @GetMapping("/api/hello")
    public String hello(){
        return "Hello";
    }
}
