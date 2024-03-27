package Attendance.register.controllers;

import Attendance.register.studentdata.AttendanceDAO;
import Attendance.register.studentdata.Student;
import Attendance.register.studentdata.StudentRepository;
import Attendance.register.subjectdata.StaticMapHolder;
import Attendance.register.subjectdata.Subject;
import Attendance.register.subjectdata.SubjectRepository;
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
    @Autowired
    private SubjectRepository subjectRepository;

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    @GetMapping("/student-add")
    public String student(){
        return "student-add";
    }

    @PostMapping("/st-add-form")
    public String addStudent(Student student, Model model) {

        AttendanceDAO attendanceDAO = new AttendanceDAO();
        String studentTag = attendanceDAO.findStudent(student.getUsername());

        if (!studentTag.isEmpty() && Objects.equals(studentTag, student.getTag())) {
            model.addAttribute("error_student", "Даннный студент уже добавлен.");
            return "student-add";
        }

        if(student.getUsername().isEmpty() || student.getTag().isEmpty()){
            model.addAttribute("error_null", "Поля группа и ФИО должны быть заполнены.");
            return "student-add";
        }

        studentRepository.save(student);
        model.addAttribute("success", "Студент добавлен.");

        Subject subject = new Subject();
        subject.setStudentId(student.getId());
        subject.setMath(0);
        subject.setProgramming(0);
        subject.setForeignLanguage(0);

        subjectRepository.save(subject);

        return "student-add";
    }

    @GetMapping("/information")
    public String information() {
        return "information";
    }


    @GetMapping("/attendance")
    public String attendance() {
        return "attendance";
    }


    @PostMapping("/stud-info")
    public String studInfo(@RequestParam String tag, @RequestParam String subject, Model model){
        AttendanceDAO attendanceDAO = new AttendanceDAO();

        StaticMapHolder.mapInsert();
        String result = StaticMapHolder.immutableMap.get(subject);

        model.addAttribute("subject", subject);
        Map<String, Integer> attendanceDataStudent = attendanceDAO.getAttendanceByTag(tag, result);
        model.addAttribute("attendanceDataStudent", attendanceDataStudent);
        model.addAttribute("tag", tag);

        return "attendance";
    }

    @PostMapping("/insertskip")
    public String insertSkipCount(@RequestParam String studentName, @RequestParam String tag,
                                  @RequestParam String subject, Model model) { //todo 2 students with equals names in diff groups
        Student student = studentRepository.findByUsername(studentName);
        AttendanceDAO attendanceDAO = new AttendanceDAO();

        if (student != null) {
            StaticMapHolder.mapInsert();
            String result = StaticMapHolder.immutableMap.get(subject);
            model.addAttribute("subject", subject);
            attendanceDAO.incrementSkipCount(studentName, result);
            Map<String, Integer> attendanceDataStudent = attendanceDAO.getAttendanceByTag(tag, result);
            model.addAttribute("attendanceDataStudent", attendanceDataStudent);
            model.addAttribute("tag", tag);
        }
        return "attendance";
    }

    @PostMapping("/deleteskip")
    public String deleteSkipCount(@RequestParam String studentName, @RequestParam String tag,
                                  @RequestParam String subject, Model model) { //todo 2 students with equals names in diff groups
        Student student = studentRepository.findByUsername(studentName);
        AttendanceDAO attendanceDAO = new AttendanceDAO();
        if (student != null) {
            StaticMapHolder.mapInsert();
            String result = StaticMapHolder.immutableMap.get(subject);
            model.addAttribute("subject", subject);
            attendanceDAO.decrementSkipCount(studentName, result);
            Map<String, Integer> attendanceDataStudent = attendanceDAO.getAttendanceByTag(tag, result);
            model.addAttribute("attendanceDataStudent", attendanceDataStudent);
            model.addAttribute("tag", tag);
        }
        return "attendance";
    }


}

