package Attendance.register.studentdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class AttendanceDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/register";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";

    public Map<String, Integer> getAttendanceByTag(String tag, String subjectName) {
        Map<String, Integer> att = new TreeMap<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT s.username, sub." + subjectName + " AS attendance_count " +
                    "FROM students s " +
                    "INNER JOIN subject sub ON s.id = sub.student_id " +
                    "WHERE s.tag = ? AND sub." + subjectName + " IS NOT NULL";
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


    public void incrementSkipCount(String studentName, String subject) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String studentIdSql = "SELECT id FROM students WHERE username = ?";
            try (PreparedStatement studentIdStatement = connection.prepareStatement(studentIdSql)) {
                studentIdStatement.setString(1, studentName);
                try (ResultSet resultSet = studentIdStatement.executeQuery()) {
                    if (resultSet.next()) {
                        UUID studentId = resultSet.getObject("id", UUID.class);

                        String updateSql = "UPDATE subject SET " + subject + " = " + subject + " + 1 WHERE student_id = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                            updateStatement.setObject(1, studentId);
                            updateStatement.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void decrementSkipCount(String studentName, String subject) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String studentIdSql = "SELECT id FROM students WHERE username = ?";
            try (PreparedStatement studentIdStatement = connection.prepareStatement(studentIdSql)) {
                studentIdStatement.setString(1, studentName);
                try (ResultSet resultSet = studentIdStatement.executeQuery()) {
                    if (resultSet.next()) {
                        UUID studentId = resultSet.getObject("id", UUID.class);

                        String updateSql = "UPDATE subject SET " + subject + " = CASE WHEN " + subject + " > 0 THEN "
                                + subject + " - 1 ELSE " + subject + " END WHERE student_id = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                            updateStatement.setObject(1, studentId);
                            updateStatement.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String findStudent(String username) {
        String tag = "";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT tag FROM students WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        tag = resultSet.getString("tag");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tag;
    }



}