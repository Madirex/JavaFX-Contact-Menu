package com.madirex.javafxmenu.controller;

import com.madirex.javafxmenu.model.UserStorageAndConfiguration;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class PanelTableController {
    @FXML private TableView table;
    @FXML private TableColumn<?, String> name;
    @FXML private TableColumn<?, String> gender;
    @FXML private TableColumn<?, String> country;
    @FXML private TableColumn<?, String> email;
    @FXML private TableColumn<?, String> phone;
    @FXML private TableColumn<?, String> age;

    @FXML
    public void initialize() {
        table.setEditable(true);

        name.setCellValueFactory(new PropertyValueFactory<>( "name"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());

        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));

        country.setCellValueFactory(new PropertyValueFactory<>("country"));
        country.setCellFactory(TextFieldTableCell.forTableColumn());

        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        email.setCellFactory(TextFieldTableCell.forTableColumn());

        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phone.setCellFactory(TextFieldTableCell.forTableColumn());

        age.setCellValueFactory(new PropertyValueFactory<>("age"));

        table.setItems(UserStorageAndConfiguration.getInstance().getListPersonaData());
    }
}

