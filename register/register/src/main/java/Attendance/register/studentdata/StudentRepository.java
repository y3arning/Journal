package Attendance.register.studentdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUsername(String username);
}
