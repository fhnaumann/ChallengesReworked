package wand555.github.io.challengesreworkedgui.controller;

import wand555.github.io.challengesreworked.challenges.PunishableChallenge;
import wand555.github.io.challengesreworked.challenges.PunishableChallengeCommon;
import wand555.github.io.challengesreworked.punishments.Punishment;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.Collection;

public abstract class PunishableChallengeController extends ChallengeController implements PunishableChallenge {

    @FXML
    private Button addPunishmentButton;
    @FXML
    private ListView<Punishment> punishmentList;

    @FXML
    @Override
    protected void initialize() {
        super.initialize();
        addPunishmentButton.setOnAction(event -> initPunishment());
    }

    private void initPunishment() {

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
