package it.bicocca.laboratoriodiprogettazione_java.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La classe {@code ItemTireController} gestisce l'interfaccia utente per un singolo elemento pneumatico all'interno dell'applicazione.
 * Offre funzionalità per mostrare i dettagli dello pneumatico e interagire con esso attraverso eventi di click.
 *
 * Attraverso l'interfaccia grafica, l'utente può selezionare uno pneumatico specifico, visualizzarne i dettagli e, potenzialmente, modificarli.
 * Questa classe gestisce le azioni di click sugli pneumatici, preparando e mostrando una nuova finestra per la modifica dei dettagli dello pneumatico selezionato.
 */
public class ItemTireController {

    @FXML
    public Label nomeLabel;

    @FXML
    AnchorPane button;

    @FXML
    Stage stage;

    static AnchorPane anchorPaneCliccato;

    public String name, nameAnchor;

    /**
     * Imposta i dati dello pneumatico da visualizzare nell'interfaccia utente.
     *
     * @param name Il nome dello pneumatico da visualizzare.
     */
    public void setData(String name) {
        this.name = name;
        nomeLabel.setText(name);
    }

    /**
     * Imposta il nome identificativo dell'elemento pneumatico.
     *
     * @param name Il nome identificativo dell'elemento.
     */
    public void setName(String name) {
        this.nameAnchor = name;
    }

    /**
     * Restituisce il nome identificativo dell'elemento pneumatico.
     *
     * @return Il nome identificativo dell'elemento.
     */
    public String getName() {
        return nameAnchor;
    }

    /**
     * Gestisce l'evento di click sull'elemento pneumatico. Questo metodo apre una nuova finestra
     * per la modifica dello pneumatico selezionato, mostrando l'interfaccia utente corrispondente.
     *
     * @param mouseEvent L'evento di click che ha scatenato il metodo.
     */
    public void click(javafx.scene.input.MouseEvent mouseEvent) {

        //Otteniamo l'id dell'anchorPane che è stata cliccata
        Node source = (Node)mouseEvent.getSource();
        AnchorPane anchorPane = (AnchorPane) source;
        String id = anchorPane.getId();
        System.out.println(id);

        anchorPaneCliccato = anchorPane;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("02_ModificaPneumatici.fxml"));
            /*
             * if "fx:controller" is not set in fxml
             * fxmlLoader.setController(NewWindowController);
             */
            Scene scene = new Scene(fxmlLoader.load(), 506, 548);
            scene.setFill(Color.TRANSPARENT);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("Inserisci nuovo pneumatico");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);

        }
    }
}