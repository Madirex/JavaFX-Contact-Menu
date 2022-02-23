package com.madirex.javafxmenu.controller;

import com.madirex.javafxmenu.App;
import com.madirex.javafxmenu.model.UserStorageAndConfiguration;
import com.madirex.javafxmenu.util.Util;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SettingsController {
    @FXML private VBox content;

    @FXML
    public void initialize() {
        UserStorageAndConfiguration config = UserStorageAndConfiguration.getInstance();
        //Agregar idiomas disponibles
        List<String> langs = new ArrayList<>(config.getAvailableLanguages());

        //Asignar nombres
        String[] languageNames = new String[langs.size()];
        for (int n = 0; n < langs.size(); n++) {
            languageNames[n] = getLanguageName(langs.get(n).split("_")[0]);
        }

        //Asignar al ChoiceBox
        ChoiceBox cbLanguage = new ChoiceBox(FXCollections.observableArrayList(languageNames));
        cbLanguage.setValue(getLanguageName(config.getActualLanguage().split("_")[0]));

        cbLanguage.setOnAction(event -> {
            for (int n = 0; n < languageNames.length; n++) {
                if (languageNames[n].equalsIgnoreCase(cbLanguage.getValue().toString())){
                    config.setActualLanguage(langs.get(n));
                }
            }
        });

        //Agregar selección de tema
        List<String> themes = new ArrayList<>(config.getAvailableThemes());
        ChoiceBox cbTheme = new ChoiceBox(FXCollections.observableArrayList(themes));
        cbTheme.setValue(config.getActualTheme());

        cbTheme.setOnAction(event -> {
            for (String theme : themes) {
                if (theme.equalsIgnoreCase(cbTheme.getValue().toString())) {
                    config.setActualTheme(theme);
                }
            }
        });


        Label changeLanguageLabel = new Label(Util.getString("title.language") + ":");
        Label changeThemeLabel = new Label(Util.getString("title.theme") + ":");

        Button btnApply = new Button(Util.getString("text.apply"));

        btnApply.setOnAction(a -> {
            try {
                new App().reloadStage();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        content.getChildren().add(changeLanguageLabel);
        content.getChildren().add(cbLanguage);
        content.getChildren().add(changeThemeLabel);
        content.getChildren().add(cbTheme);
        content.getChildren().add(new Label(""));
        content.getChildren().add(btnApply);

        cbTheme.setPrefWidth(200);
        cbLanguage.setPrefWidth(200);

        changeLanguageLabel.setPadding(new Insets(10, 0, 0, 0));
        changeThemeLabel.setPadding(new Insets(10, 0, 0, 0));
    }

    private String getLanguageName(String lng) {
        Locale loc = new Locale(lng);
        String str = loc.getDisplayLanguage(loc);
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
