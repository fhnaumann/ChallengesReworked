package wand555.github.io.challengesreworkedgui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public abstract class PunishableChallengeController extends ChallengeController {

    @FXML
    private Button addPunishmentButton;
    @FXML
    private ListView punishmentList;



    @FXML
    @Override
    protected void initialize() {
        addPunishmentButton.setOnAction(event -> initPunishment());
    }

    private void initPunishment() {

    }
}
