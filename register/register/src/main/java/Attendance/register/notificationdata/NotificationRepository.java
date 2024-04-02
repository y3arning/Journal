package Attendance.register.notificationdata;

import org.springframework.data.jpa.repository.JpaRepository;



public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Notification findByStudentNameAndSubjectAndInformation(String studentName, String subject, String information);
}

