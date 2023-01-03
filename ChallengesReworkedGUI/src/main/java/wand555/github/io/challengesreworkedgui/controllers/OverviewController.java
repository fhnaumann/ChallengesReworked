package wand555.github.io.challengesreworkedgui.controllers;

import dev.dejvokep.boostedyaml.YamlDocument;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import wand555.github.io.challengesreworked.Common;
import wand555.github.io.challengesreworked.challenges.ChallengeCommon;
import wand555.github.io.challengesreworked.challenges.nocrafting.NoCraftingChallengeCommon;
import wand555.github.io.challengesreworked.goals.GoalCommon;
import wand555.github.io.challengesreworked.punishments.AffectType;
import wand555.github.io.challengesreworked.punishments.health.HealthPunishmentCommon;
import wand555.github.io.challengesreworked.punishments.randomitem.RandomItemPunishmentCommon;
import wand555.github.io.challengesreworkedgui.Wrapper;
import wand555.github.io.challengesreworkedgui.controllers.challenges.ChallengesOverviewController;
import wand555.github.io.challengesreworkedgui.controllers.goals.GoalsOverviewController;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class OverviewController {

    @FXML
    private ChallengesOverviewController challengesOverviewController;
    @FXML
    private GoalsOverviewController goalsOverviewController;
    @FXML
    private Button exportButton;
    @FXML
    private Button loadButton;

    @FXML
    private void onExport(ActionEvent actionEvent) {
        boolean deleted = new File("data_storage.yml").delete();
        System.out.println("deleted " + deleted);
        try {
            YamlDocument storage = YamlDocument.create(
                    new File("data_storage.yml")
            );
            //storage.set("challenges", null);
            System.out.println(storage);
            List<ChallengeCommon> allChallenges = challengesOverviewController.getAllChallenges();
            storage.set("challenges", allChallenges);
            List<GoalCommon> allGoals = goalsOverviewController.getAllGoals();
            storage.set("goals", allGoals);
            storage.save();
            storage.reload();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void onImport(ActionEvent actionEvent) {

        createDummyObjects();

        try {
            YamlDocument storage = YamlDocument.create(
                    new File("data_storage.yml")
            );
            //storage.set("challenges", null);
            List<ChallengeCommon> list = (List<ChallengeCommon>) storage.getList("challenges");
            Wrapper.setDataInControllerFrom(list, challengesOverviewController.getAllChallengesController());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDummyObjects() {
        // For common objects whose "wrapper" controller is not instantiated at application launch
        // their TypeAdapter is not registered yet because no instance of the common object was
        // created yet.
        // That's why every punishment common object is created once so it's registered.

        new HealthPunishmentCommon(0, AffectType.CAUSER, 0);
        new RandomItemPunishmentCommon();
    }
}
