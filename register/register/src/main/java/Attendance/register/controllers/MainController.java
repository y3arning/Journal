package Attendance.register.controllers;

import Attendance.register.studentdata.AttendanceDAO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;


@Controller
public class MainController {

    @GetMapping("/")
    public String home() {
        AttendanceDAO attendanceDAO = new AttendanceDAO();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (Objects.equals(attendanceDAO.getUserRole(username), "admin")){
            return "home";
        }
        return "user-home";
    }

    @GetMapping("/login")
    public String add() {
        return "login";
    }

}
