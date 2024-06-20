package it.bicocca.laboratoriodiprogettazione_java.controller;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ImpostazioniController {
    public void CloseApplication(MouseEvent mouseEvent) {
        Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.hide();
    }

    public void MinimizeApplication(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}
