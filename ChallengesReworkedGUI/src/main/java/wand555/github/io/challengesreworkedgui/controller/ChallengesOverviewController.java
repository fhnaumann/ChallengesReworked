package wand555.github.io.challengesreworkedgui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import wand555.github.io.challengesreworkedgui.ChallengeApplication;

import java.io.FileNotFoundException;

public class ChallengesOverviewController {


    @FXML
    private NoCraftingChallengeController noCraftingChallengeController;

    @FXML
    private Label allChallenges;
    @FXML
    private ScrollPane challengesPane;
    @FXML
    private VBox challengesVBox;
    @FXML
    private TitledPane challenge3;

    @FXML
    private void initialize() throws FileNotFoundException {
        Font font = Font.loadFont(ChallengeApplication.class.getResourceAsStream("fonts/VT323-Regular.ttf"), 30);
    }
}
