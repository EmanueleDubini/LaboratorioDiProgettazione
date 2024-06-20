package it.bicocca.laboratoriodiprogettazione_java.controller;

import it.bicocca.laboratoriodiprogettazione_java.GroupeRMain;
import it.bicocca.laboratoriodiprogettazione_java.entity.Staff;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

import static it.bicocca.laboratoriodiprogettazione_java.controller.LoginController.ruolo;

/**
 * Controller per la gestione degli eventi della pagina di selezione del magazzino.
 *
 * Questa classe contiene i metodi che gestiscono gli eventi del mouse associati alla
 * gestione delle pagine del magazzino e all'interazione con l'interfaccia utente.
 */
public class WarehouseController {

    /**
     * Gestisce l'evento di click del mouse per aprire la pagina per la gestione del magazzino.
     *
     * Questo metodo viene chiamato quando si verifica un evento di click del mouse
     * associato all'apertura della pagina del magazzino. Utilizza il controller della
     * pagina principale per caricare la pagina "01_PrevTiers".
     *
     * @param mouseEvent l'evento di click del mouse che ha attivato questo metodo
     * @throws IOException se si verifica un errore durante il caricamento della pagina
     */
    public void openPrevTiers(MouseEvent mouseEvent) throws IOException {
        switch (ruolo) {
            case "Capo magazzino":
            case "Capo officina":
            case "Direttore di filiale":
            case "root":
                StaffController controllerStaff = StaffController.getInstance();
                controllerStaff.loadPage("01_PrevTires");
                break;
            case "Officina":
            case "Magazzino":
                MainPageController controllerMainPage = MainPageController.getInstance();
                controllerMainPage.loadPage("01_PrevTires");
                break;
            default:
                //Aggiunta la logica nel caso in cui il ruolo recuperato non sia valido.
                System.out.println("Accesso negato. Ruolo non autorizzato.");
                break;
        }
    }

    /**
     * Gestisce l'evento di click del mouse per aprire la pagina per la gestione del magazzino.
     *
     * Questo metodo viene chiamato quando si verifica un evento di click del mouse
     * associato all'apertura della pagina del magazzino. Utilizza il controller della
     * pagina principale per caricare la pagina "01_PrevTiers".
     *
     * @param mouseEvent l'evento di click del mouse che ha attivato questo metodo
     * @throws IOException se si verifica un errore durante il caricamento della pagina
     */
    public void openWarehouse(MouseEvent mouseEvent) throws IOException {
        switch (ruolo) {
            case "Capo magazzino":
            case "Capo officina":
            case "Direttore di filiale":
            case "root":
                StaffController controllerStaff = StaffController.getInstance();
                controllerStaff.loadPage("01_PartsList");
                break;
            case "Officina":
            case "Magazzino":
                MainPageController controllerMainPage = MainPageController.getInstance();
                controllerMainPage.loadPage("01_PartsList");
                break;
            default:
                //Aggiunta la logica nel caso in cui il ruolo recuperato non sia valido.
                System.out.println("Accesso negato. Ruolo non autorizzato.");
                break;
        }
    }

    /**
     * Chiude l'applicazione.
     *
     * @param mouseEvent L'evento di click che ha innescato il metodo.
     */
    public void CloseApplication(MouseEvent mouseEvent) {
        Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.hide();
    }

    /**
     * Minimizza l'applicazione nella barra delle applicazioni.
     *
     * @param mouseEvent L'evento di click che ha innescato il metodo.
     */
    public void MinimizeApplication(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}
