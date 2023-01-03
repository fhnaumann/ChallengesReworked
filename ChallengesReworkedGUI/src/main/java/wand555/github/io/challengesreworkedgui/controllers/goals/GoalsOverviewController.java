package wand555.github.io.challengesreworkedgui.controllers.goals;

import javafx.fxml.FXML;
import wand555.github.io.challengesreworked.Commonable;
import wand555.github.io.challengesreworked.goals.Goal;
import wand555.github.io.challengesreworked.goals.GoalCommon;

import java.util.List;

public class GoalsOverviewController {

    @FXML
    private MobGoalController mobGoalController;

    private List<GoalController> controllers;

    @FXML
    private void initialize() {
        controllers = List.of(
                mobGoalController
        );
    }

    public List<GoalCommon> getAllGoals() {
        return getAllGoalControllers().stream().map(Goal::getCommon).toList();
    }

    public List<GoalController> getAllGoalControllers() {
        return controllers;
    }

}
