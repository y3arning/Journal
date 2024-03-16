package Attendance.register.controllers;

import Attendance.register.accessingdata.Role;
import Attendance.register.accessingdata.User;
import Attendance.register.accessingdata.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



import java.util.Collections;


@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/reg-form")
    public String addUser(User user) {

        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {

            return "registration";
        }

        if(user.getUsername().isEmpty() || user.getPassword().isEmpty()){

            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return "redirect:/";
    }


}