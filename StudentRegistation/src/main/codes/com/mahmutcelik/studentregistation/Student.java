package com.mahmutcelik.studentregistation;

/**
 * CLASS THAT ALLOWS US TO CREATE STUDENT OBJECTS TO STORE IN OBSERVABLEARRAYLIST. THERE ARE 4 FIELDS.
 * */
public class Student {
    private int studentID;
    private String studentName;
    private String studentMobileNumber;
    private String studentCourseName;

    public Student(int studentID, String studentName, String studentMobileNumber, String studentCourseName) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.studentMobileNumber = studentMobileNumber;
        this.studentCourseName = studentCourseName;
    }

    public int getStudentID() {
        return studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentMobileNumber() {
        return studentMobileNumber;
    }

    public String getStudentCourseName() {
        return studentCourseName;
    }
}
