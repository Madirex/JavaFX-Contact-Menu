package com.madirex.javafxmenu.controller;

import com.madirex.javafxmenu.model.UserStorageAndConfiguration;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.util.Objects;

public class MainController {
    @FXML private Pane background;
    @FXML private Menu menu;
    @FXML private Button menuButton;
    @FXML private Pane mainPanel;
    @FXML private VBox contentPanel;
    @FXML private VBox panelMenu;
    @FXML private VBox contactsView;
    @FXML private VBox settings;
    @FXML private VBox panelTable;
    @FXML private VBox panelCharts;
    @FXML private ContactsViewController contactsViewController;
    @FXML private SettingsController settingsController;
    @FXML private PanelTableController panelTableController;
    @FXML private PanelChartsController panelChartsController;

    private TranslateTransition animMenu;
    private boolean onMenu;

    private int margin;

    public MainController() {
    }

    @FXML
    public void initialize() {
        background.getStylesheets().add(Objects.requireNonNull(this.getClass()
                .getResource("/style/themes/style_" +
                        UserStorageAndConfiguration.getInstance().getActualTheme().toLowerCase() + ".css")).toExternalForm());
        margin = 10;
        animMenu = new TranslateTransition(Duration.millis(100), mainPanel);
        onMenu = false;
        panelMenu.setTranslateX(panelMenu.getTranslateX() - panelMenu.getPrefWidth() - margin);

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        panelMenu.setPrefHeight(screenBounds.getHeight());
    }

    @FXML
    protected void onMenuShowButtonClick() {
        showMenu(!onMenu);
        onMenu = !onMenu;
    }

    @FXML
    protected void onContactsViewButtonClick(){
        resetPanelState();
        contactsView.setVisible(true);
        contactsView.setDisable(false);
    }

    @FXML
    protected void onSettingsButtonClick(){
        resetPanelState();
        settings.setVisible(true);
        settings.setDisable(false);
    }

    @FXML
    protected void onPanelTableButtonClick(){
        resetPanelState();
        panelTable.setVisible(true);
        panelTable.setDisable(false);
    }

    @FXML
    protected void onPanelChartsButtonClick(){
        resetPanelState();
        panelChartsController.initCharts();
        panelCharts.setVisible(true);
        panelCharts.setDisable(false);
    }

    @FXML
    protected void exitProgram() {
        Platform.exit();
        System.exit(0);
    }

    private void resetPanelState(){
        contactsView.setVisible(false);
        contactsView.setDisable(true);
        settings.setVisible(false);
        settings.setDisable(true);
        panelTable.setVisible(false);
        panelTable.setDisable(true);
        panelCharts.setVisible(false);
        panelCharts.setDisable(true);
    }

    private void showMenu(boolean show){
        animMenu.setFromX(show ? 0 : panelMenu.getWidth() + margin);
        animMenu.setToX(show ? panelMenu.getWidth() + margin : 0);
        animMenu.play();
    }

}