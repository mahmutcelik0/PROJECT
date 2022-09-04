package com.mahmutcelik.studentregistation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//IN JAVAFX PROJECTS WE HAVE TO IMPORT java.sql.... BEFORE THIS DON'T FORGET ADD JAR INTO MODULES NOT LIBRARY
import java.sql.*;

public class DatabaseConnection {
    //INFORMATIONS ABOUT DATABASE THAT STORES STUDENTS.
    //URL IS SCHEMA LINK. USER IN MYSQK WORKBENCH USER. PASSWORD IS INITIAL PASSWORD IN WORKBENCH
    private final String URL = "jdbc:mysql://localhost:3306/ttt";
    private final String USER = "MAHMUT";
    private final String PASSWORD = "M.celik2001";

    //TO USE IN OTHER METHODS I HAVE DEFINED THESE PARTS ABOUT DATABASE. BECAUSE I USE THESE ARE IN OTHER METHODS AND SCOPES MUST BE LARGER THAN METHOD
    //TO PREVENT NULL.. EXCEPTIONS
    private Connection connection = null; //PROVIDES US TO MAKE CONNECTION PROJECT AND DATABASE
    private PreparedStatement preparedStatement = null; //PROVIDES US TO PREPARE QUERIES WHICH WILL SEND DATABASE
    private ResultSet resultSet = null; //AFTER QUERY AND USAGE OF SELECT QUERY FROM DATABASE WE HAVE TO USE executeQuery() METHOD AND IT RETURNS RESULTSET
    // THIS PART STORAGE RESULTS FROM DATABASE


    /**
     * USING Class.forName(...) METHOD WE ADD SQL DRIVERS INTO PROJECT
     * USING DriverManager.getConnection(...) METHOD IT MAKES CONNECTION DATABASE
     * */
    public DatabaseConnection(){
        try {
            Class.forName("java.sql.Driver");
        }catch (ClassNotFoundException e){
            System.out.println("CLASS NOT FOUND PROBLEM");
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
        }catch (SQLException e){
            System.out.println("CONNECTION PROBLEM WITH DRIVER MANAGER");
            e.printStackTrace();
        }
    }

    /**
     * IN BELOW METHOD IT RETURNS OBSERVABLELIST BECAUSE I WILL USE IN MAINPAGECONTROLLER
     * THIS METHOD SEND QUERY TO DATABASE AND IT GETS DATA FROM DATABASE AND STORES THEM.
     * I HAVE USED THIS PART IN 3 SECTION
     * 1-> BEGINNING OF PROGRAM (TO TAKE DATABASE VALUES WHERE ADDED BEFORE PROGRAMS)
     * 2-> DElETE ELEMENT FROM TABLE
     * 3-> ADD ELEMENT TO TABLE
     * */
    public ObservableList<Student> getDataFromTable(){
        String queryToSend = "SELECT * FROM `ttt`.`fifthtable`";


        ObservableList<Student> obsListToReturn = FXCollections.observableArrayList();
        try {
            preparedStatement = connection.prepareStatement(queryToSend);
            resultSet = preparedStatement.executeQuery();


            while (resultSet.next()){
                obsListToReturn.add(new Student(resultSet.getInt("STUDENTID"),resultSet.getString("STUDENTNAME"),resultSet.getString("STUDENTMOBILENUMBER"),resultSet.getString("STUDENTCOURSENAME")));
            }

        }catch (SQLException e){
            System.out.println("GET DATA FROM TABLE PROBLEM IN SQLEXCEPTION");
            e.printStackTrace();
        }

        return obsListToReturn;
    }

    /**
     * USING BELOW METOD, I ADDED ELEMENTS TO DATABASE. IT CONTROLS STUDENTS OBSERVABLELIST BECAUSE
     * THE SAME STUDENT MAY HAVE BEEN ADDED BEFORE.
     * studentsObsList.stream().filter(...).count -> IF ANY STUDENT IS SAME toAddStudent numberCount
     * WILL BE 1 AND STUDENT WON'T ADD (RETURN FALSE)
     * AFTER CONTROL I CREATED QUERY TO SEND DATABASE. CONSEQUENTLY QUERY SENT DATABASE AND
     * STUDENT ADDED THE OBSLIST
     * */
    public boolean addStudentToDatabase(ObservableList<Student> studentsObsList,Student toAddStudent){
        long isEqualNumber =studentsObsList.stream().filter((studentFromList)->
             toAddStudent.getStudentName().equalsIgnoreCase(studentFromList.getStudentName()) && toAddStudent.getStudentMobileNumber().equalsIgnoreCase(studentFromList.getStudentMobileNumber())
                    && toAddStudent.getStudentCourseName().equalsIgnoreCase(studentFromList.getStudentCourseName()))
                .count();
        if(isEqualNumber == 1){
            return false;
        }
        else{
            String queryToAdd = "INSERT INTO `fifthtable` (STUDENTID,STUDENTNAME,STUDENTMOBILENUMBER,STUDENTCOURSENAME) VALUES (?,?,?,?)";

            try {
                preparedStatement = connection.prepareStatement(queryToAdd);

                preparedStatement.setInt(1,toAddStudent.getStudentID());
                preparedStatement.setString(2,toAddStudent.getStudentName());
                preparedStatement.setString(3,toAddStudent.getStudentMobileNumber());
                preparedStatement.setString(4,toAddStudent.getStudentCourseName());

                preparedStatement.executeUpdate();

            }catch (SQLException e){
                System.out.println("ADD PROBLEM");
                e.printStackTrace();
            }catch (RuntimeException e){
                return false;
            }

            return true;
        }

    }
    /**
     * IN THIS METHOD I HAVE INITIALIZED QUERY AND SENT IT TO DATABASE.
     * IF ANY STUDENT IS NOT SAME SENT STUDENT executeUpdate() WILL RETURN 0. AFTER THE NUMBER 0
     * METHOD RETURN FALSE.
     * */
    public boolean deleteProcessFromConnection(ObservableList<Student> students, String nameToDelete,String numberToDelete, String courseToDelete){

        String queryToSend = "DELETE FROM fifthtable WHERE STUDENTNAME = ? && STUDENTMOBILENUMBER = ? && STUDENTCOURSENAME = ?";
        try {
            preparedStatement = connection.prepareStatement(queryToSend);
            preparedStatement.setString(1,nameToDelete);
            preparedStatement.setString(2,numberToDelete);
            preparedStatement.setString(3,courseToDelete);

            int number = preparedStatement.executeUpdate();
            if(number == 0) return false;
        }catch (SQLException e){
            System.err.println("DELETE PROBLEM");
            return false;
        }
        return true;
    }

    /**
     * MADE QUERY
     * USAGE OF LIMIT -> USING SIZE OF LIST(STUDENT OBSLIST SIZE()) IT DELETES ALL ELEMENTS FROM DATABASE
     * IF executeUpdate() RETURN 0 METHOD WILL RETURN FALSE AND NOTHING WILL HAPPEN .
     * */
    public boolean clearTableElements(int sizeOfList){
        String queryToSend = "DELETE FROM fifthtable LIMIT ?";
        try {
            preparedStatement = connection.prepareStatement(queryToSend);

            preparedStatement.setInt(1,sizeOfList);

            int processResult = preparedStatement.executeUpdate();
            if(processResult == 0) return false;

        }catch (SQLException e){
            System.err.println("CLEAR PROBLEM");
            return false;
        }
        return true;

    }


}
