package Attendance.register.subjectdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "subject")
public class Subject {
    @Id
    @Column(name = "student_id", nullable = false)
    private UUID studentId;

    private int math;
    private int programming;
    @Column(name = "foreign_language")
    private int foreignLanguage;

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

    public int getMath() {
        return math;
    }

    public void setMath(int math) {
        this.math = math;
    }

    public int getProgramming() {
        return programming;
    }

    public void setProgramming(int programming) {
        this.programming = programming;
    }

    public int getForeignLanguage() {
        return foreignLanguage;
    }

    public void setForeignLanguage(int foreignLanguage) {
        this.foreignLanguage = foreignLanguage;
    }
}
