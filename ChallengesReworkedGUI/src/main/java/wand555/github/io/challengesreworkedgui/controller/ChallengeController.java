package wand555.github.io.challengesreworkedgui.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;

public class ChallengeController {

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
}
