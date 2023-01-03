package wand555.github.io.challengesreworkedgui.controllers.goals;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import wand555.github.io.challengesreworked.Common;
import wand555.github.io.challengesreworked.challenges.ChallengeCommon;
import wand555.github.io.challengesreworked.goals.Goal;
import wand555.github.io.challengesreworked.goals.GoalCommon;
import wand555.github.io.challengesreworkedgui.ResourceBundleWrapper;
import wand555.github.io.challengesreworkedgui.controllers.Controller;

import java.util.ResourceBundle;

public abstract class GoalController extends Controller implements Goal {

    protected GoalCommon common;

    @FXML
    protected TitledPane titledPane;

    @FXML
    protected ToggleButton activateButton;

    protected ResourceBundle bundle = null;

    @FXML
    protected void initialize() {
        bundle = new ResourceBundleWrapper(ResourceBundle.getBundle("wand555/github/io/challengesreworkedgui/lang_goals"));

        activateButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            Node title = titledPane.lookup(".title");
            if(newValue) {
                activateButton.setText(bundle.getString("goal.deactivate"));
                title.setStyle("-fx-background-color: green;");
            }
            else {
                activateButton.setText(bundle.getString("goal.activate"));
                title.setStyle(null);
            }
        });
    }

    @Override
    public void setDataFromCommon(Common from) {
        common = (GoalCommon) from.copy();
        activateButton.setSelected(true); //set selected because this method is only called if its active
    }

    public boolean isActive() {
        return activateButton.isSelected();
    }
}
