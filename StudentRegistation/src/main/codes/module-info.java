module com.mahmutcelik.studentregistation {
    //TO USE JAVAFX AND MYSQL BELOW REQUIRES MUST BE IMPLEMENTED BEOFRE START
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.mahmutcelik.studentregistation to javafx.fxml;
    exports com.mahmutcelik.studentregistation;
}