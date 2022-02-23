package com.madirex.javafxmenu.controller;

import com.madirex.javafxmenu.model.Persona;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ContactController {
    @FXML private Label nameLabel;
    @FXML private Label genderlabel;
    @FXML private Label countryLabel;
    @FXML private Label emailLabel;
    @FXML private Label phoneLabel;
    @FXML private Label ageLabel;
    @FXML private ImageView imageProfile;
    private Persona persona;

    @FXML
    public void initialize() {
        persona = new Persona();
        setPicture();
        addBindsAndListeners();
    }

    public void setPersona(Persona persona){
        this.persona = persona;
        setPicture();
        addBindsAndListeners();
    }

    private void setPicture() {
        if (persona.getPicture() != null){
            Image image = new Image(persona.getPicture());
            imageProfile.setImage(image);
        }
    }

    private void addBindsAndListeners() {
        nameLabel.textProperty().bind(persona.nameProperty());
        genderlabel.textProperty().bind(persona.genderProperty());
        countryLabel.textProperty().bind(persona.countryProperty());
        emailLabel.textProperty().bind(persona.emailProperty());
        phoneLabel.textProperty().bind(persona.phoneProperty());
        ageLabel.textProperty().bind(persona.ageProperty().asString());

        persona.pictureProperty().addListener((observableValue, s, t1) -> {
            setPicture();
        });
    }
}