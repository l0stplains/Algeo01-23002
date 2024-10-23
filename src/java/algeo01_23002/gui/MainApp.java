package algeo01_23002.gui;

import atlantafx.base.theme.CupertinoLight;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/algeo01_23002/gui/main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 675);
        stage.setTitle("Hello!");
        stage.setMinWidth(1200);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }

    public static void show(){
        launch();
    }
}