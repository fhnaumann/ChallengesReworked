package wand555.github.io.challengesreworkedgui.controllers.challenges;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import wand555.github.io.challengesreworked.challenges.ChallengeCommon;
import wand555.github.io.challengesreworked.challenges.PunishableChallenge;
import wand555.github.io.challengesreworked.challenges.PunishableChallengeCommon;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;
import wand555.github.io.challengesreworkedgui.ChallengeApplication;
import wand555.github.io.challengesreworkedgui.ResourceBundleWrapper;
import wand555.github.io.challengesreworkedgui.Wrapper;
import wand555.github.io.challengesreworkedgui.controllers.challenges.noblockbreaking.NoBlockBreakingChallengeController;
import wand555.github.io.challengesreworkedgui.controllers.challenges.noblockplacing.NoBlockPlacingChallengeController;
import wand555.github.io.challengesreworkedgui.controllers.challenges.nocrafting.NoCraftingChallengeController;
import wand555.github.io.challengesreworkedgui.controllers.challenges.nodamage.NoDamageChallengeController;
import wand555.github.io.challengesreworkedgui.controllers.punishments.PunishmentOverviewController;
import wand555.github.io.challengesreworkedgui.rows.PunishmentRow;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChallengesOverviewController {


    @FXML
    private NoCraftingChallengeController noCraftingChallengeController;
    @FXML
    private NoBlockPlacingChallengeController noBlockPlacingChallengeController;
    @FXML
    private NoBlockBreakingChallengeController noBlockBreakingChallengeController;
    @FXML
    private NoDamageChallengeController noDamageChallengeController;

    @FXML
    private Label allChallenges;
    @FXML
    private ScrollPane challengesPane;
    @FXML
    private VBox challengesVBox;
    @FXML
    private TitledPane challenge3;
    @FXML
    private Button globalPunishmentsButton;

    private ObservableList<PunishmentRow> globalPunishmentRows = FXCollections.observableArrayList();

    @FXML
    private void initialize() throws FileNotFoundException {
        Font font = Font.loadFont(ChallengeApplication.class.getResourceAsStream("fonts/VT323-Regular.ttf"), 30);

    }

    public List<ChallengeCommon> getAllChallenges() {
        return getAllChallengesController().stream()
                .filter(ChallengeController::isActive)
                .map(challengeController -> challengeController.getCommon().copy())
                .toList();
    }

    public List<ChallengeController> getAllChallengesController() {
        return List.of(
                noCraftingChallengeController,
                noBlockPlacingChallengeController,
                noBlockBreakingChallengeController,
                noDamageChallengeController
        );
    }

    @FXML
    private void editGlobalPunishments(ActionEvent actionEvent) {
        ResourceBundle bundle = new ResourceBundleWrapper(ResourceBundle.getBundle("wand555/github/io/challengesreworkedgui/lang_punishments"));
        FXMLLoader punishmentLoader = new FXMLLoader(ChallengeApplication.class.getResource("punishments/punishment_overview.fxml"), bundle);
        try {
            Parent root = punishmentLoader.load();
            PunishmentOverviewController punishmentOverviewController = punishmentLoader.getController();
            punishmentOverviewController.setSource(null);
            // change only global changes so the punishments become modifiable here
            globalPunishmentRows.forEach(punishmentRow -> punishmentRow.getPunishmentController().setOnlyGlobalChanges(false));
            punishmentOverviewController.setDataFromRows(globalPunishmentRows);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(root, 500, 600);
            stage.setScene(scene);
            stage.setOnCloseRequest(event1 -> {
                globalPunishmentRows = FXCollections.observableArrayList(punishmentOverviewController.getAllActivePunishmentsAsRow());
                List<PunishmentCommon> globallyEnabledPunishmentCommons = punishmentOverviewController.getAllActivePunishmentsAsCommon();
                // add marker for when the row is constructed back
                globalPunishmentRows.forEach(punishmentRow -> {
                    punishmentRow.getPunishmentController().setGlobal(true);
                    punishmentRow.getPunishmentController().setOnlyGlobalChanges(true);
                });
                getAllChallengesController().stream()
                        .filter(challengeController -> challengeController instanceof PunishableChallenge)
                        .map(challengeController -> (PunishableChallengeController) challengeController)
                        .forEach(punishableChallengeController -> {
                            //set in model
                            punishableChallengeController.getCommon().setPunishmentCommons(globallyEnabledPunishmentCommons);

                            //set in ui (the common object was modified in the line above, so set it again, so it reapplies the data into the UI fields)
                            punishableChallengeController.getPunishmentList().setItems(FXCollections.observableArrayList(
                                    globalPunishmentRows.stream()
                                            .map(punishmentRow -> punishmentRow.getPunishmentController().getAsOneLine())
                                            .toList()
                            ));
                            Platform.runLater(() -> {
                                System.out.println(punishableChallengeController.getPunishmentList().getItems());
                                punishableChallengeController.getPunishmentList().refresh();
                            });
                            //punishableChallengeController.setDataFromCommon(punishableChallengeController.getCommon(), false);
                        });
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setGlobalPunishmentRowsFromCommons(List<ChallengeCommon> commons) {
        // Punishments are considered global when every punishment with the exact same settings
        // is present in every challenge.
        // Theoretically a user could enable the exact same punishment on every challenge
        // locally and upon reimporting the data this would be converted to a global punishment.

        List<PunishableChallengeCommon> mapped = commons.stream()
                .filter(challengeCommon -> challengeCommon instanceof PunishableChallengeCommon)
                .map(challengeCommon -> (PunishableChallengeCommon) challengeCommon)
                .toList();
        for(PunishableChallengeCommon common : mapped) {
            for(PunishmentCommon punishmentCommon : common.getPunishmentCommons()) {
                if(mapped.stream().allMatch(punishableChallengeCommon -> punishableChallengeCommon.getPunishmentCommons().contains(punishmentCommon))) {
                    globalPunishmentRows.add(Wrapper.wrapAndInject(punishmentCommon, false).getAsOneLine());
                }
            }
        }
    }
}
