package Attendance.register.dbproperties;

import Attendance.register.studentdata.AttendanceDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public AttendanceDAO attendanceDAO() {
        AttendanceDAO attendanceDAO = new AttendanceDAO();
        AttendanceDAO.setURL(url);
        AttendanceDAO.setUSER(username);
        AttendanceDAO.setPASSWORD(password);
        return attendanceDAO;
    }

}