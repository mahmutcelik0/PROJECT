# ⭐STUDENT REGISTRATION
⚠️JAVA, JAVAFX LIBRARY, FXML and MYSQL

![Untitled](https://github.com/mahmutcelik0/PROJECT/blob/main/IMAGES/FIRST)

## ⚠️PROBLEMS

1→ SQL erişilememesi

Maven archetype lı projede javafx kullanıp sonrasında sql eklemek istediğimde problem aldım. Çözüm

STEP 1- [module-info.java](http://module-info.java) ya requires java.sql;

```java
module com.example.sjsj {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.sjsj to javafx.fxml;
    exports com.example.sjsj;
}
```

STEP 2- OPEN MODULE SETTINGS → MODULES → ADD JARs or DIRECTORIES → Add MySQL java connector jar

![Untitled](https://github.com/mahmutcelik0/PROJECT/blob/main/IMAGES/SEONCD)

![Untitled](https://github.com/mahmutcelik0/PROJECT/blob/main/IMAGES/THIRD)

STEP 3- IMPORT JAVA SQL TO OUR CLASS THAT MAKES CONNECTION BETWEEN PROJECT AND DATABASE

```java
import java.sql.*;

public class makeConnectionClass{...}
```

## PROBLEM -2 (TABLEVIEW KULLANIMI)

ListView kullanımında columnlar birbirinden bağımsız oldukları için görüntü olarak iyi bir biçimde değildi(Slider geldiğinde özellikle). Bunun için listView yerine tableView kullanılması gerekli.

TableView kullanımı için

1→ FXML tasarım ve sütunlara ID ler verilir.

2→ Controller içerisinde dimension <> içerisinde tableView ve tableColumnlar tanımlanır

```java
@FXML
private TableView<Student> tableView
@FXML
private TableColumn <Student,String> secondColumn,thirdColumn,fourthColumn

```

3 → Initializable Interface i implement edilir ve metod içerisinde ObservableList kullanılarak Student classından nesneler depolanır.

```java
@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
ObservableList<Student> students = FXCollections.observableArrayList(
		new Student(1,"MAHMUT","CELIK","JAVA"),
		new Student(1,"MAHMUT","CELIK","JAVA"),
    new Student(1,"MAHMUT","CELIK","JAVA"),
    new Student(1,"MAHMUT","CELIK","JAVA"),
    new Student(1,"MAHMUT","CELIK","JAVA"),
    new Student(1,"MAHMUT","CELIK","JAVA"));
}
```

4→ Her column un Student classından hangi field ları alacakları setCellValueProperty(…) metoduyla belirlenir.

```java
@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    firstColumn.setCellValueFactory(new PropertyValueFactory<Student,Integer>("studentID"));
    secondColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("studentName"));
    thirdColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("studentMobileNumber"));
    fourthColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("studentCourseName"));

    tableView.setItems(students);

}

//FIRSTCOLUMN , GELEN OBS.LIST ICERISINDEKI STUDENTLARDAN "studentID" FIELD INI GOSTERECEK
//...
```

```java
package com.example.dsdsds;

public class Student {
    public int studentID;
    public String studentName;
    public String studentMobileNumber;
    public String studentCourseName;

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
```

![This is an image](https://github.com/mahmutcelik0/PROJECT/blob/main/IMAGES/FOURTH)
