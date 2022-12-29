package wand555.github.io.challengesreworkedgui.controllers.punishments;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;
import wand555.github.io.challengesreworkedgui.rows.PunishmentRow;

import java.util.List;
import java.util.stream.Stream;

public class PunishmentOverviewController {

    @FXML
    private HealthPunishmentController healthPunishmentController;

    @FXML
    private CheckBox showActivePunishments;
    @FXML
    private ScrollPane punishmentsPane;

    @FXML
    protected void initialize() {

    }

    public List<PunishmentRow> getAllPunishmentsAsRow() {
        return Stream.of(healthPunishmentController)
                .filter(PunishmentController::isActive)
                .map(PunishmentController::getAsOneLine)
                .toList();
    }

    public List<PunishmentCommon> getAllPunishmentsAsCommon() {
        return Stream.of(healthPunishmentController)
                .filter(PunishmentController::isActive)
                .map(punishmentController -> (PunishmentCommon) punishmentController.getCommon())
                .toList();
    }

    public void setDataFromRows(ObservableList<PunishmentRow> punishmentRows) {
        punishmentRows.forEach(punishmentRow -> {
            Platform.runLater(() -> {
                PunishmentController controller = punishmentRow.getPunishmentController();
                if(controller instanceof HealthPunishmentController) {
                    healthPunishmentController.extractDataFromPunishmentRow(punishmentRow);
                }
            });

        });
    }
}
