package wand555.github.io.challengesreworkedgui.controllers.challenges;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import wand555.github.io.challengesreworked.challenges.PunishableChallenge;
import wand555.github.io.challengesreworked.challenges.PunishableChallengeCommon;
import wand555.github.io.challengesreworked.punishments.Punishment;
import javafx.fxml.FXML;
import wand555.github.io.challengesreworkedgui.ChallengeApplication;
import wand555.github.io.challengesreworkedgui.rows.PunishmentRow;
import wand555.github.io.challengesreworkedgui.controllers.punishments.PunishmentOverviewController;

import java.io.IOException;
import java.util.Collection;
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
            ResourceBundle bundle = ResourceBundle.getBundle("wand555/github/io/challengesreworkedgui/lang_bundle");
            FXMLLoader punishmentLoader = new FXMLLoader(ChallengeApplication.class.getResource("punishments/punishment_overview.fxml"), bundle);
            try {
                Parent root = punishmentLoader.load();
                PunishmentOverviewController punishmentOverviewController = punishmentLoader.getController();
                punishmentOverviewController.setDataFromRows(punishmentList.getItems());
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                Scene scene = new Scene(root, 400, 400);
                stage.setScene(scene);
                stage.setOnCloseRequest(event1 -> {
                    punishmentList.setItems(FXCollections.observableArrayList(punishmentOverviewController.getAllPunishmentsAsRow()));
                    getCommon().setPunishmentCommons(punishmentOverviewController.getAllPunishmentsAsCommon());
                });
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    @Override
    public Collection<? extends Punishment> getPunishments() {
        return null;
    }

    @Override
    public void setPunishments(Collection<? extends Punishment> punishments) {

    }

    @Override
    public PunishableChallengeCommon getCommon() {
        return (PunishableChallengeCommon) super.getCommon();
    }
}
