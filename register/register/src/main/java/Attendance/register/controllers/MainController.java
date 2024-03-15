package Attendance.register.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String add() {
        return "login";
    }

    @GetMapping("/information")
    public String information() {
        return "information";
    }






}
