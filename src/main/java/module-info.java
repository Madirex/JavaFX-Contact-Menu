module com.madirex.javafxmenu {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires lombok;
    requires javax.json.api;

    opens com.madirex.javafxmenu to javafx.fxml;
    exports com.madirex.javafxmenu;
    exports com.madirex.javafxmenu.controller;
    exports com.madirex.javafxmenu.model;
    opens com.madirex.javafxmenu.controller to javafx.fxml;
    exports com.madirex.javafxmenu.dto;
}