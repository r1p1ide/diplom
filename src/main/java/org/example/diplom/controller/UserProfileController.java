package org.example.diplom.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class UserProfileController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    @GetMapping("/page")
    public String getAccountPage() {
        return "page";
    }

    @GetMapping
    public String logout() {
        return "logout";
    }
}
