package com.madirex.javafxmenu.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

public class Contactos {
    private ObservableSet<Persona> personas;

    public Contactos() {
        this.personas = FXCollections.observableSet();
    }
}
