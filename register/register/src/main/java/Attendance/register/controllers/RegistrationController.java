package Attendance.register.controllers;

import Attendance.register.accessingdata.User;
import Attendance.register.accessingdata.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/reg-form")
    public String addUser(User user, Model model) {

        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.addAttribute("error_login","Пользователь с таким именем уже существет.");
            return "registration";
        }

        if(user.getUsername().isEmpty() || user.getPassword().isEmpty()){
            model.addAttribute("error_null","Поля логин и пароль должны быть заполнены");
            return "registration";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setActive(true);
        user.setRole("user");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "redirect:/";
    }


}