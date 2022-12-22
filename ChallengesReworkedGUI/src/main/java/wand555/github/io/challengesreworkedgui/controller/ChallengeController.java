package wand555.github.io.challengesreworkedgui.controller;

import io.github.wand555.challengesreworkedapi.challenges.Challenge;
import io.github.wand555.challengesreworkedapi.challenges.ChallengeCommon;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;

public abstract class ChallengeController implements Challenge {

    protected ChallengeCommon common;

    @FXML
    protected TitledPane titledPane;

    @FXML
    protected ToggleButton activateButton;

    @FXML
    protected void initialize() {
        activateButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            Node title = titledPane.lookup(".title");
            if(newValue) {
                activateButton.setText("Deaktivieren");
                title.setStyle("-fx-background-color: green;");
            }
            else {
                activateButton.setText("Aktivieren");
                title.setStyle(null);
            }
        });
    }

    @Override
    public ChallengeCommon getCommon() {
        return common;
    }
}
