package Attendance.register.controllers;

import Attendance.register.studentdata.Student;
import Attendance.register.studentdata.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;
    @GetMapping("/student-add")
    public String student(){
        return "student-add";
    }

    @PostMapping("/st-add-form")
    public String addStudent(Student student, @RequestParam String tag, @RequestParam String name) {

        student.setSkip(0);
        student.setTag(tag);
        student.setUsername(name);
        studentRepository.save(student);

//        Student studentFromDb = studentRepository.findByUsername(student.getUsername());
//
//        if (studentFromDb != null) {
//
//            return "student-add";
//        }
//
//        if(student.getUsername().isEmpty() || student.getTag().isEmpty()){
//
//            return "student-add";
//        }
//
//        student.setSkip(0);
//        studentRepository.save(student);

        return "redirect:/student-add";
    }
}

