package com.mahmutcelik.studentregistation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * APPLICATIONSTARTER CLASS STARTS JAVAFX GUI PANE.
 *
 *         stage.initStyle(StageStyle.UNDECORATED);
 *         USING ABOVE CODE I CLOSED WINDOW PART AFTER THAT I'VE ADDED A BUTTON MYSELF TO CLOSE PROGRAM
 *
 * */

public class aplicationStarter extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(aplicationStarter.class.getResource("mainPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);

        stage.initStyle(StageStyle.UNDECORATED);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

