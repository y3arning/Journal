package Attendance.register.studentdata;

import java.sql.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class AttendanceDAO {
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    public static void setURL(String URL) {
        AttendanceDAO.URL = URL;
    }

    public static void setUSER(String USER) {
        AttendanceDAO.USER = USER;
    }

    public static void setPASSWORD(String PASSWORD) {
        AttendanceDAO.PASSWORD = PASSWORD;
    }

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


    public void incrementSkipCount(String studentName, String tag, String subject) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String studentIdSql = "SELECT id FROM students WHERE username = ? AND tag = ?";
            try (PreparedStatement studentIdStatement = connection.prepareStatement(studentIdSql)) {
                studentIdStatement.setString(1, studentName);
                studentIdStatement.setString(2, tag);
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


    public void decrementSkipCount(String studentName, String tag, String subject) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String studentIdSql = "SELECT id FROM students WHERE username = ? AND tag = ?";
            try (PreparedStatement studentIdStatement = connection.prepareStatement(studentIdSql)) {
                studentIdStatement.setString(1, studentName);
                studentIdStatement.setString(2, tag);
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

    public String findStudent(String username, String tag) {
        String selectedTag = "";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT tag FROM students WHERE username = ? AND tag = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, tag);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        selectedTag = resultSet.getString("tag");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return selectedTag;
    }

    public String getUserRole(String username) {
        String role = "";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT role FROM usr WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        role = resultSet.getString("role");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }
}