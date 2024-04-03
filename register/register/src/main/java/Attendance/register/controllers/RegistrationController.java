package Attendance.register.controllers;

import Attendance.register.accessingdata.User;
import Attendance.register.accessingdata.UserRepository;
import Attendance.register.studentdata.AttendanceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    AttendanceDAO attendanceDAO = new AttendanceDAO();

    @GetMapping("/registration")
    public String registration() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (Objects.equals(attendanceDAO.getUserRole(username), "admin")){
            return "registration";
        }
        return "redirect:/";
    }

    @PostMapping("/reg-form")
    public synchronized String addUser(User user, Model model) {

        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.addAttribute("error_login", "Пользователь с таким именем уже существет.");
            return "registration";
        }

        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            model.addAttribute("error_null", "Поля логин и пароль должны быть заполнены");
            return "registration";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setActive(true);
        user.setRole("headman");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "redirect:/";
    }

}