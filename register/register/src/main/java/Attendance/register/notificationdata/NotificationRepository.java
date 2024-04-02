package Attendance.register.notificationdata;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Notification findByStudentNameAndSubjectAndInformation(String studentName, String subject, String information);
}

