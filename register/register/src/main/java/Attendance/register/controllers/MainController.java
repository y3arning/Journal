package Attendance.register.controllers;

import Attendance.register.notificationdata.Notification;
import Attendance.register.notificationdata.NotificationRepository;
import Attendance.register.studentdata.AttendanceDAO;
import Attendance.register.studentdata.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;


@Controller
public class MainController {

    @Autowired
    private NotificationRepository notificationRepository;
    AttendanceDAO attendanceDAO = new AttendanceDAO();
    @GetMapping("/")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (Objects.equals(attendanceDAO.getUserRole(username), "admin")){
            return "home";
        }
        if (Objects.equals(attendanceDAO.getUserRole(username), "headman")){

            if (attendanceDAO.getSeen(attendanceDAO.getHeadmanId(username).toString()) == 0){
                String notSeen = "notSeen";
                model.addAttribute("notSeen", notSeen);
            }
            return "headman-home";
        }
        return "user-home";
    }

    @PostMapping("/notification")
    public String notification(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String id = attendanceDAO.getHeadmanId(username).toString();
        List<String[]> list;
        list = attendanceDAO.getNotification(id);
        model.addAttribute("list", list);
        attendanceDAO.updateSeen(id);
        return "notifications";
    }

    @PostMapping("/deletenotification")
    public String deleteNotification(@RequestParam String studentName, @RequestParam String subject,
                                     @RequestParam String information, Model model){

        Notification notification = notificationRepository.findByStudentNameAndSubjectAndInformation(studentName,
                subject, information);
        if (notification != null) {
            notificationRepository.delete(notification);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String id = attendanceDAO.getHeadmanId(username).toString();
        List<String[]> list;
        list = attendanceDAO.getNotification(id);
        model.addAttribute("list", list);
        return "notifications";
    }


    @GetMapping("/snake")
    public String snake() {
        return "snake";
    }

    @GetMapping("/tetris")
    public String tetris() {
        return "tetris";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
