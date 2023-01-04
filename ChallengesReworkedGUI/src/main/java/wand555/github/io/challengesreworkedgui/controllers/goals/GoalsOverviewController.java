package wand555.github.io.challengesreworkedgui.controllers.goals;

import javafx.fxml.FXML;
import wand555.github.io.challengesreworked.Commonable;
import wand555.github.io.challengesreworked.goals.Goal;
import wand555.github.io.challengesreworked.goals.GoalCommon;
import wand555.github.io.challengesreworked.goals.itemcollect.ItemCollectGoal;

import java.util.List;

public class GoalsOverviewController {

    @FXML
    private MobGoalController mobGoalController;
    @FXML
    private ItemCollectGoalController itemCollectGoalController;

    private List<GoalController> controllers;

    @FXML
    private void initialize() {
        controllers = List.of(
                mobGoalController,
                itemCollectGoalController
        );
    }

    public List<GoalCommon> getAllGoals() {
        return getAllGoalControllers().stream()
                .filter(GoalController::isActive)
                .map(Goal::getCommon)
                .map(GoalCommon::copy)
                .toList();
    }

    public List<GoalController> getAllGoalControllers() {
        return controllers;
    }

}
