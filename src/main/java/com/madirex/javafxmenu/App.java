package com.madirex.javafxmenu;

import com.madirex.javafxmenu.model.UserStorageAndConfiguration;
import com.madirex.javafxmenu.util.Util;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    private static Stage stage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        App.stage = stage;
        startStage();
    }

    public void startStage() throws IOException {
        stage.getIcons().add(new Image(Objects.requireNonNull(App.class.getResourceAsStream("/images/icon.png"))));
        UserStorageAndConfiguration.loadData(); //Cargar configuración del usuario
        Parent root = Util.getParentRoot("main-view"); //View asignada con el idioma del usuario
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle(Util.getString("text.menuAppMsg"));
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    public void reloadStage() throws IOException {
        stage.close();
        startStage();
    }
}