package Attendance.register.studentdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AttendanceDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/register";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";

    public Map<String, Integer> getAttendanceByTag(String tag) {
        Map<String, Integer> att = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT username, skip AS attendance_count " +
                    "FROM students " +
                    "WHERE tag = ? ";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, tag);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String name = resultSet.getString("username");
                        int skipCount = resultSet.getInt("attendance_count");
                        att.put(name, skipCount);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return att;
    }
}