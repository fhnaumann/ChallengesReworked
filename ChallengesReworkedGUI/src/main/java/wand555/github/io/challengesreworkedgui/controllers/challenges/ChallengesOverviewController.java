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
import javafx.scene.layout.AnchorPane;
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
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.stream.Collectors;

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
    private AnchorPane anchorPane;

    @FXML
    private void initialize() throws FileNotFoundException {
        Font font = Font.loadFont(ChallengeApplication.class.getResourceAsStream("fonts/VT323-Regular.ttf"), 30);

    }

    public List<ChallengeCommon> getAllChallenges() {
        return getAllChallengesController().stream()
                .filter(ChallengeController::isActive)
                .map(challengeController -> {
                    if(challengeController instanceof PunishableChallengeController punishableChallengeController) {
                        // In case if punishments are present in this challenge, they are currently set in the rows.
                        // These rows need to be explicitly set as punishment commons so the right commons are exported.
                        punishableChallengeController.getCommon().setPunishmentCommons(
                                punishableChallengeController.getPunishmentList().getItems().stream()
                                        .map(punishmentRow -> punishmentRow.getPunishmentController().getCommon())
                                        .collect(Collectors.toCollection(TreeSet::new))
                        );
                        return punishableChallengeController.getCommon().copy();
                    }
                    else {
                        return challengeController.getCommon().copy();
                    }
                })
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
            globalPunishmentRows.forEach(punishmentRow -> {
                punishmentRow.getPunishmentController().setUnmodifiable(false);
                punishmentRow.getPunishmentController().setGlobal(true);
            });
            punishmentOverviewController.getControllers().forEach(punishmentController -> {
                punishmentController.setGlobal(true);
            });
            punishmentOverviewController.setDataFromRows(globalPunishmentRows);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(root, 500, 600);
            stage.setScene(scene);
            stage.setOnCloseRequest(event1 -> {
                globalPunishmentRows = FXCollections.observableArrayList(punishmentOverviewController.getAllActivePunishmentsAsRow());
                Collection<PunishmentCommon> globallyEnabledPunishmentCommons = punishmentOverviewController.getAllActivePunishmentsAsCommon();
                // add marker for when the row is constructed back
                globalPunishmentRows.forEach(punishmentRow -> {
                    punishmentRow.getPunishmentController().setGlobal(true);
                    punishmentRow.getPunishmentController().setUnmodifiable(true);
                });
                getAllChallengesController().stream()
                        .filter(challengeController -> challengeController instanceof PunishableChallenge)
                        .map(challengeController -> (PunishableChallengeController) challengeController)
                        .forEach(punishableChallengeController -> {
                            //set in model
                            punishableChallengeController.getCommon().getPunishmentCommons().addAll(globallyEnabledPunishmentCommons);
                            //set in ui (the common object was modified in the line above, so set it again, so it reapplies the data into the UI fields)
                            punishableChallengeController.getPunishmentList().getItems().addAll(FXCollections.observableArrayList(
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

    public void checkForGlobalPunishments() {
        getAllChallengesController().stream()
                .filter(challengeController -> challengeController instanceof PunishableChallengeController)
                .map(challengeController -> (PunishableChallengeController) challengeController)
                .forEach(punishableChallengeController -> {
                    for(PunishmentCommon punishmentCommon : punishableChallengeController.getCommon().getPunishmentCommons()) {
                        boolean globallyEnabled = punishableChallengeController.punishmentIsGloballyEnabled(punishmentCommon);
                        if(globallyEnabled) {
                            //go through every controller and set this punishment ("punishmentCommon") in there to global (non-editable)
                            markAsGlobalPunishment(punishmentCommon);
                        }
                    }
                });
    }

    private void markAsGlobalPunishment(PunishmentCommon punishmentCommon) {
        getAllChallengesController().stream()
                .filter(challengeController -> challengeController instanceof PunishableChallengeController)
                .map(challengeController -> (PunishableChallengeController) challengeController)
                .forEach(punishableChallengeController -> {
                    System.out.println(punishableChallengeController);
                    punishableChallengeController.getPunishmentList().getItems().stream()
                            .filter(punishmentRow -> punishmentRow.getPunishmentController().getCommon().equals(punishmentCommon))
                            .forEach(punishmentRow -> {
                                punishmentRow.getPunishmentController().setUnmodifiable(true);
                    });
                });
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
                    globalPunishmentRows.add(Wrapper.wrapAndInject(punishmentCommon).getAsOneLine());
                }
            }
        }
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public ObservableList<PunishmentRow> getGlobalPunishmentRows() {
        return globalPunishmentRows;
    }
}
