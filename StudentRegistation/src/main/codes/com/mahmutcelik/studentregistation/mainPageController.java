package com.mahmutcelik.studentregistation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * THIS CLASS IS CONTROLLER CLASS THAT GIVES FUNCTIONALITY TO FXML PAGE
 * CONTROLLER CLASSES DON'T HAVE CONSTRUCTOR INSTEAD OF THEY HAVE INITIALIZE METHOD() THAT COMES FROM INITIALIZABLE INTERFACE
 * 1-> databaseProcesses -> TO MAKE DATABASE PROCESS LIKE ADD DELETE EDIT... (CREATED OBJECT FROM CLASS AND USED THAT)
 * 2-> students -> IT'S A OBSERVABLELIST TO STORE STUDENTS FROM DATABASE. WE NEED THAT BECAUSE IN GUI I USED TABLEVIEW AND IT WORKS WITH OBSERVABLELIST
 * */
public class mainPageController implements Initializable {
    private DatabaseConnection databaseProcesses = new DatabaseConnection();
    static ObservableList<Student> students = null;

    @FXML
    private TableView<Student> tableView;
    @FXML
    private TableColumn<Student,Integer> idListView;
    @FXML
    private TableColumn<Student,String> nameListView,mobileListView,courseListView;


    @FXML
    private TextField studentNameField , mobileNumberField, courseNameField;
    @FXML
    private Button addButton,deleteButton,clearButton;
    @FXML
    private Label warningMessages;

    @FXML
    private Button closeButton;


    /**
     *  WE CAN CALL IT CONTROLLERS CONSTRUCTOR. BEGINNING OF PROGRAM IT WORKS SPONTANEOUSLY.
     *  fillListViewBeginning() -> IN THE BEGINNING MAYBE THERE ARE STUDENTS IN DATABASE FROM BEFORE PROGRAMS.
     *  IT TAKES AND ADD THEM INTO OBSERVABLEARRAYLIST.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         students = FXCollections.observableArrayList();

        fillListViewBeginning(students);

        //THIS PART SETS COLUMNS OF TABLEVIEW. IN TABLEVIEW IT STORES STUDENT AND COLUMNS TAKE STUDENT'S FIELDS
        idListView.setCellValueFactory(new PropertyValueFactory<Student,Integer>("studentID"));
        nameListView.setCellValueFactory(new PropertyValueFactory<Student,String>("studentName"));
        mobileListView.setCellValueFactory(new PropertyValueFactory<Student,String>("studentMobileNumber"));
        courseListView.setCellValueFactory(new PropertyValueFactory<Student,String>("studentCourseName"));

        tableView.setItems(students);


        disableButton();    //BEGINNING OF PROGRAM IT DISABLES BUTTON UNTIL ALL INPUTS ENTER


    }

    public void fillListViewBeginning(ObservableList<Student> students){
        students.addAll(databaseProcesses.getDataFromTable());
    }



    /**
     * ALLOWS US TO MAKE ALL BUTTONS DISABLE UNTIL WHOLE TEXTFIELDS ARE FULL
     * */
    @FXML
    public void disableButton(){
        disablingButtons(studentNameField.getText().trim().isEmpty() || mobileNumberField.getText().trim().isEmpty() || courseNameField.getText().trim().isEmpty());
    }
    @FXML
    private void disablingButtons(boolean valueToMake){
        addButton.setDisable(valueToMake);
        deleteButton.setDisable(valueToMake);
    }


    /**
     * BELOW METHOD CONTROLS INPUTS FROM NAME TEXTFIELD AND PHONENUMBER TEXTFIELD. IF REGULAR EXPRESSIONS RETURN FALSE METHOD RETUNS FALSE
     * */
    private boolean inputController(){
        //IF MOBILENUMBERFIELD CONTAINS LETTER IT WILL DISABLE BUTTON
        String regex = "[0-9]+";
        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(mobileNumberField.getText());
        if(!m.matches()){
            warningMessages.setText("ENTER ONLY DIGITS IN MOBILE\nNUMBER FIELD");
            return false;
        }

        //IF STUNDETNAMEFIELD CONTAINS DIGIT IT WILL DISABLE BUTTON
        String regex2 = "[A-Z]+";
        Pattern p2 = Pattern.compile(regex2);

        Matcher m2 = p2.matcher(studentNameField.getText().trim().toUpperCase());
        if(!m2.matches()) {
            warningMessages.setText("ENTER ONLY LETTER IN\nSTUDENT NAME FIELD");
            return false;
        }

        warningMessages.setText("");
        return true;
    }

    /**I USED BELOW METHOD TO ADD STUDENT TO THE DATABASE. WHEN ADD BUTTON CLICKED FROM MOUSE (MOUSE RELEASED)
     * THIS METHOD WORKS.
     */
    @FXML
    public void addToDatabase(){
        if(!inputController()) return;

        Student studentToAdd = new Student(students.size()+1, studentNameField.getText().trim().toUpperCase(),mobileNumberField.getText().trim().toUpperCase(),courseNameField.getText().trim().toUpperCase());
        boolean isProcessHappened = databaseProcesses.addStudentToDatabase(students,studentToAdd);
        if (isProcessHappened){
            warningMessages.setText("");
            students.clear();
            students.addAll(databaseProcesses.getDataFromTable());
        }
        else{
            warningMessages.setText("THIS USER ALREADY EXIST IN LIST");
        }
    }

    /**
     * I USED BELOW METHOD TO DELETE STUDENT FROM THE DATABASE. WHEN DELETE BUTTON CLICKED THIS METHOD WORKS
     * */
    @FXML
    public void deleteFromDatabase(){
        if(!inputController()) return;

        String nameToDelete = studentNameField.getText().trim().toUpperCase();
        String numberToDelete = mobileNumberField.getText().trim().toUpperCase();
        String courseToDelete = courseNameField.getText().trim().toUpperCase();

        boolean isProcessHappened = databaseProcesses.deleteProcessFromConnection(students,nameToDelete,numberToDelete,courseToDelete);

        if(isProcessHappened){
            warningMessages.setText("");
            students.clear();
            students.addAll(databaseProcesses.getDataFromTable());
        }
        else{
            warningMessages.setText("THIS USER IS NOT EXIST IN LIST");
        }
    }

    //IT CLEAR TABLE HELD IN DATABASE
    @FXML
    public void clearFromDatabase(){
        boolean isProcessHappened = databaseProcesses.clearTableElements(students.size());

        if(isProcessHappened){
            warningMessages.setText("");
            students.clear();
        }
        else {
            warningMessages.setText("EMPTY LIST");
        }


    }
    //CLOSE BUTTON CONTROLLER
    @FXML
    private void closeProgram(){
        System.exit(0);
    }
}
