package wand555.github.io.challengesreworkedgui.controllers;

import dev.dejvokep.boostedyaml.YamlDocument;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import wand555.github.io.challengesreworked.challenges.ChallengeCommon;
import wand555.github.io.challengesreworked.challenges.nocrafting.NoCraftingChallengeCommon;
import wand555.github.io.challengesreworkedgui.controllers.challenges.ChallengesOverviewController;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class OverviewController {

    @FXML
    private ChallengesOverviewController challengesOverviewController;
    @FXML
    private Button exportButton;

    @FXML
    private void onExport(ActionEvent actionEvent) {
        boolean deleted = new File("data_storage2.yml").delete();
        System.out.println("deleted " + deleted);
        try {
            YamlDocument storage = YamlDocument.create(
                    new File("data_storage2.yml")
            );
            //storage.set("challenges", null);
            System.out.println(storage);
            List<ChallengeCommon> allChallenges = challengesOverviewController.getAllChallenges();
            storage.set("challenges", allChallenges);
            storage.save();
            storage.reload();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
