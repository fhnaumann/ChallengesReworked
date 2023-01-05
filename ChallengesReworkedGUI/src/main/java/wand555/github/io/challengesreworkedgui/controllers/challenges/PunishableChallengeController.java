package wand555.github.io.challengesreworkedgui.controllers.challenges;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import wand555.github.io.challengesreworked.Common;
import wand555.github.io.challengesreworked.challenges.PunishableChallenge;
import wand555.github.io.challengesreworked.challenges.PunishableChallengeCommon;
import wand555.github.io.challengesreworked.punishments.Punishment;
import javafx.fxml.FXML;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;
import wand555.github.io.challengesreworkedgui.ChallengeApplication;
import wand555.github.io.challengesreworkedgui.ResourceBundleWrapper;
import wand555.github.io.challengesreworkedgui.Wrapper;
import wand555.github.io.challengesreworkedgui.controllers.OverviewController;
import wand555.github.io.challengesreworkedgui.controllers.punishments.PunishmentController;
import wand555.github.io.challengesreworkedgui.rows.PunishmentRow;
import wand555.github.io.challengesreworkedgui.controllers.punishments.PunishmentOverviewController;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public abstract class PunishableChallengeController extends ChallengeController implements PunishableChallenge {

    @FXML
    private Button addPunishmentButton;
    @FXML
    private ListView<PunishmentRow> punishmentList;
    @FXML
    @Override
    protected void initialize() {
        super.initialize();

        addPunishmentButton.setOnAction(event -> {
            ResourceBundle bundle = new ResourceBundleWrapper(ResourceBundle.getBundle("wand555/github/io/challengesreworkedgui/lang_punishments"));
            FXMLLoader punishmentLoader = new FXMLLoader(ChallengeApplication.class.getResource("punishments/punishment_overview.fxml"), bundle);
            try {
                Parent root = punishmentLoader.load();
                PunishmentOverviewController punishmentOverviewController = punishmentLoader.getController();
                punishmentOverviewController.setSource(this);
                punishmentOverviewController.setDataFromRows(punishmentList.getItems());
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                Scene scene = new Scene(root, 500, 600);
                stage.setScene(scene);
                stage.setOnCloseRequest(event1 -> {
                    punishmentList.setItems(FXCollections.observableArrayList(punishmentOverviewController.getAllActivePunishmentsAsRow()));
                    getCommon().setPunishmentCommons(punishmentOverviewController.getAllActivePunishmentsAsCommon());
                });
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    @Override
    public void setDataFromCommon(Common from, boolean thisActive) {
        super.setDataFromCommon(from, thisActive);
        Collection<PunishmentCommon> punishmentCommons = getCommon().getPunishmentCommons();
        List<PunishmentRow> rows = punishmentCommons.stream()
                .map(punishmentCommon -> Wrapper.wrapAndInject(punishmentCommon))
                .map(PunishmentController::getAsOneLine)
                .toList();
        punishmentList.setItems(FXCollections.observableArrayList(rows));
    }

    public boolean punishmentIsGloballyEnabled(PunishmentCommon punishmentCommon) {
        // Punishments are considered global when every punishment with the exact same settings
        // is present in every challenge.
        // Theoretically a user could enable the exact same punishment on every challenge
        // locally and upon reimporting the data this would be converted to a global punishment.

        // f- this static
        boolean test = OverviewController.getInstance().getChallengesOverviewController().getAllChallenges().stream()
                .peek(challengeCommon -> System.out.println(challengeCommon + "!!"))
                .filter(challengeCommon -> challengeCommon instanceof PunishableChallengeCommon)
                .map(challengeCommon -> (PunishableChallengeCommon) challengeCommon)
                .peek(punishableChallengeCommon -> {
                    System.out.println("current " + punishmentCommon);
                    System.out.println("punishablechallenge (source) " + punishableChallengeCommon);
                    System.out.println("all in challenge " + punishableChallengeCommon.getPunishmentCommons());
                })
                .allMatch(punishableChallengeCommon -> punishableChallengeCommon.getPunishmentCommons().contains(punishmentCommon));
        System.out.println(test);
        return test;
    }

    @Override
    public Collection<? extends Punishment> getPunishments() {
        return null;
    }

    @Override
    public void setPunishments(Collection<? extends Punishment> punishments) {

    }

    public ListView<PunishmentRow> getPunishmentList() {
        return punishmentList;
    }

    @Override
    public PunishableChallengeCommon getCommon() {
        return (PunishableChallengeCommon) super.getCommon();
    }
}
