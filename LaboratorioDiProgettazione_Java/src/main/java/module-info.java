module it.bicocca.laboratoriodiprogettazione_java {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.datatransfer;
    requires java.desktop;
    requires io;
    requires kernel;
    requires layout;
    requires itextpdf;


    opens it.bicocca.laboratoriodiprogettazione_java to javafx.fxml;
    opens it.bicocca.laboratoriodiprogettazione_java.controller to javafx.fxml;
    opens it.bicocca.laboratoriodiprogettazione_java.entity to javafx.base, javafx.fxml;

    exports it.bicocca.laboratoriodiprogettazione_java;
    exports it.bicocca.laboratoriodiprogettazione_java.controller;
    exports it.bicocca.laboratoriodiprogettazione_java.db;
    opens it.bicocca.laboratoriodiprogettazione_java.common to javafx.base, javafx.fxml;
}
