package wand555.github.io.challengesreworkedgui.controller;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import wand555.github.io.challengesreworkedgui.HelloApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ChallengesOverviewController {


    @FXML
    private Label allChallenges;
    @FXML
    private ScrollPane challengesPane;
    @FXML
    private TitledPane challenge1;
    @FXML
    private TitledPane challenge2;
    @FXML
    private VBox challengesVBox;
    @FXML
    private TitledPane challenge3;

    @FXML
    private void initialize() throws FileNotFoundException {
        Font font = Font.loadFont(HelloApplication.class.getResourceAsStream("fonts/VT323-Regular.ttf"), 30);
    }
}
