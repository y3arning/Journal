package Attendance.register.controllers;

import Attendance.register.studentdata.AttendanceDAO;
import Attendance.register.studentdata.Student;
import Attendance.register.studentdata.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Objects;


@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;
    @GetMapping("/student-add")
    public String student(){
        return "student-add";
    }

    @PostMapping("/st-add-form")
    public String addStudent(Student student, @RequestParam String tag, @RequestParam String username) {

        AttendanceDAO attendanceDAO = new AttendanceDAO();
        String studentTag = attendanceDAO.findStudent(username);

        if (Objects.equals(studentTag, tag)) {
            System.out.println("exist");
            return "student-add";
        }

        if(student.getUsername() == null || student.getTag() == null){
            return "student-add";
        }

        student.setSkip(0);
        studentRepository.save(student);

        return "redirect:/student-add";
    }

    @GetMapping("/information")
    public String information() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        return "information";
    }


    @GetMapping("/attendance")
    public String attendance() {
        return "attendance";
    }


    @PostMapping("/stud-info")
    public String studInfo(@RequestParam String tag, Model model){
        AttendanceDAO attendanceDAO = new AttendanceDAO();
        Map<String, Integer> attendanceData = attendanceDAO.getAttendanceByTag(tag);
        model.addAttribute("attendanceData", attendanceData);
        model.addAttribute("tag", tag);
        return "attendance";
    }

    @PostMapping("/insertskip")
    public String insertSkipCount(@RequestParam String studentName, Model model, String tag) { //todo 2 students with equals names in diff groups
        Student student = studentRepository.findByUsername(studentName);
        AttendanceDAO attendanceDAO = new AttendanceDAO();
        if (student != null) {
            attendanceDAO.incrementSkipCountByName(studentName);
            Map<String, Integer> attendanceData = attendanceDAO.getAttendanceByTag(tag);
            model.addAttribute("attendanceData", attendanceData);
            model.addAttribute("tag", tag);
        }
        return "attendance";
    }

    @PostMapping("/deleteskip")
    public String deleteSkipCount(@RequestParam String studentName, Model model, String tag){ //todo 2 students with equals names in diff groups
        Student student = studentRepository.findByUsername(studentName);
        AttendanceDAO attendanceDAO = new AttendanceDAO();
        if (student != null) {
            attendanceDAO.decrementSkipCountByName(studentName);
            Map<String, Integer> attendanceData = attendanceDAO.getAttendanceByTag(tag);
            model.addAttribute("attendanceData", attendanceData);
            model.addAttribute("tag", tag);
        }
        return "attendance";
    }


}

