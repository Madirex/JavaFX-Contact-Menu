package com.madirex.javafxmenu.controller;

import com.madirex.javafxmenu.model.Persona;
import com.madirex.javafxmenu.model.UserStorageAndConfiguration;
import com.madirex.javafxmenu.util.Util;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class ContactsViewController {
    @FXML private Button loadButton;
    @FXML private Pane contact;
    @FXML private ContactController contactController;
    @FXML private VBox contactsSection;
    @FXML private Button menuBackBtn;
    @FXML private ListView<Persona> listPersona;
    @FXML private StackPane mainStackPane;
    private BooleanProperty loading;

    @FXML
    public void initialize() {
        listPersona.setItems(UserStorageAndConfiguration.getInstance().getListPersonaData());
        listPersona.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listPersona.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                loadUser(newValue); //Cargar usuario
                Platform.runLater(() -> listPersona.getSelectionModel().clearSelection()); //Resetear la selección
            }
        });

        loading = new SimpleBooleanProperty();
        ProgressIndicator pi = new ProgressIndicator();
        int circleSize = 40;
        pi.setMinWidth(circleSize);
        pi.setMinHeight(circleSize);
        StackPane loadingPane = new StackPane();
        loadingPane.getChildren().add(pi);
        mainStackPane.getChildren().add(loadingPane);
        loadingPane.setVisible(false);
        loadingPane.setDisable(true);

        //Loading listener
        loading.addListener(e -> {
            if (loading.get()){
                loadingPane.setVisible(true);
                loadingPane.setDisable(false);
                contactsSection.setDisable(true);
            }else{
                loadingPane.setVisible(false);
                loadingPane.setDisable(true);
                contactsSection.setDisable(false);
            }
        });
    }

    @FXML
    protected void loadUser(Persona persona){
        contact.setVisible(true);
        contact.setDisable(false);

        contactController.setPersona(persona);

        contactsSection.setVisible(false);
        contactsSection.setDisable(true);

        menuBackBtn.setVisible(true);
        menuBackBtn.setDisable(false);
    }

    @FXML
    protected void onMenuBackClick() {
        contact.setVisible(false);
        contact.setDisable(true);

        contactsSection.setVisible(true);
        contactsSection.setDisable(false);

        menuBackBtn.setVisible(false);
        menuBackBtn.setDisable(true);
    }

    @FXML
    protected void loadAnotherUser(){
            if (!loading.get()){
                    Task<Void> task = new Task<>() {
                        @Override
                        protected Void call() {
                            loading.set(true);
                            Optional<Persona> opt = Util.getRandomUser();
                            opt.ifPresent(persona -> UserStorageAndConfiguration.getInstance()
                                    .getListPersonaData().add(persona));
                            loading.set(false);
                            return null;
                        }
                    };
                new Thread(task).start();
                Thread.setDefaultUncaughtExceptionHandler((t, e) -> {}); //Deshabilito error de excepción (que es ocasionado por estar fuera del Thread de Java FX - Not on FX application thread; currentThread
            }
    }
}