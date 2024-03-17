package Attendance.register.studentdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface StudentRepository extends JpaRepository<Student, UUID> {
    Student findByUsername(String username);
}
