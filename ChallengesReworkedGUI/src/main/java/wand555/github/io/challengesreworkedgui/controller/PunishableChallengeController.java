package wand555.github.io.challengesreworkedgui.controller;

import io.github.wand555.challengesreworkedapi.challenges.PunishableChallenge;
import io.github.wand555.challengesreworkedapi.challenges.PunishableChallengeCommon;
import io.github.wand555.challengesreworkedapi.punishments.Punishment;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public abstract class PunishableChallengeController extends ChallengeController implements PunishableChallenge {

    @FXML
    private Button addPunishmentButton;
    @FXML
    private ListView<Punishment> punishmentList;

    @FXML
    @Override
    protected void initialize() {
        addPunishmentButton.setOnAction(event -> initPunishment());
    }

    private void initPunishment() {

    }

    @Override
    public PunishableChallengeCommon getCommon() {
        return (PunishableChallengeCommon) super.getCommon();
    }
}
