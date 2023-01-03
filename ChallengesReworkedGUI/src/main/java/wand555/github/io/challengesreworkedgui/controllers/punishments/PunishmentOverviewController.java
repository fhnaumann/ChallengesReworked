package wand555.github.io.challengesreworkedgui.controllers.punishments;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;
import wand555.github.io.challengesreworkedgui.controllers.challenges.ChallengeController;
import wand555.github.io.challengesreworkedgui.rows.PunishmentRow;

import java.util.List;

public class PunishmentOverviewController {

    @FXML
    private HealthPunishmentController healthPunishmentController;
    @FXML
    private RandomItemPunishmentController randomItemPunishmentController;

    private ChallengeController source;


    private List<PunishmentController> controllers;

    @FXML
    private CheckBox showActivePunishments;
    @FXML
    private ScrollPane punishmentsPane;

    @FXML
    protected void initialize() {
        controllers = List.of(
                healthPunishmentController,
                randomItemPunishmentController
        );
    }

    public void setSource(ChallengeController source) {
        this.source = source;
        controllers.forEach(punishmentController -> punishmentController.setSource(source));
    }

    public List<PunishmentRow> getAllActivePunishmentsAsRow() {
        return controllers.stream()
                .filter(PunishmentController::isActive)
                .map(PunishmentController::getAsOneLine)
                .toList();
    }

    public List<PunishmentCommon> getAllActivePunishmentsAsCommon() {
        return controllers.stream()
                .filter(PunishmentController::isActive)
                .map(punishmentController -> (PunishmentCommon) punishmentController.getCommon())
                .toList();
    }

    public List<PunishmentController> getControllers() {
        return controllers;
    }

    public void setDataFromRows(ObservableList<PunishmentRow> punishmentRows) {
        punishmentRows.forEach(punishmentRow -> {
            Platform.runLater(() -> {
                PunishmentController controller = punishmentRow.getPunishmentController();
                controllers.stream()
                        .filter(punishmentController -> punishmentController.getClass().equals(controller.getClass()))
                        .findFirst()
                        .ifPresentOrElse(punishmentController -> {
                            punishmentController.extractDataFromPunishmentRow(punishmentRow);
                        }, () -> {
                            throw new RuntimeException("Couldn't extract data from punishment row because " +
                                    "row of type '%s' is not in list of controllers %s.".formatted(controller, controllers));
                        });
                /*
                if(controller instanceof HealthPunishmentController) {
                    healthPunishmentController.extractDataFromPunishmentRow(punishmentRow);
                }
                else if(controller instanceof RandomItemPunishmentController) {

                }

                 */
            });

        });
    }
}
