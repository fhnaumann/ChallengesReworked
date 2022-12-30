package wand555.github.io.challengesreworkedgui.controllers.challenges;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import wand555.github.io.challengesreworked.challenges.ChallengeCommon;
import wand555.github.io.challengesreworkedgui.ChallengeApplication;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Stream;

public class ChallengesOverviewController {


    @FXML
    private NoCraftingChallengeController noCraftingChallengeController;
    @FXML
    private NoBlockPlacingChallengeController noBlockPlacingChallengeController;

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

    public List<ChallengeCommon> getAllChallenges() {
        return Stream.of(
                noCraftingChallengeController,
                noBlockPlacingChallengeController
                )
                .filter(ChallengeController::isActive)
                .map(challengeController -> (ChallengeCommon) challengeController.getCommon().copy())
                .toList();
    }
}
