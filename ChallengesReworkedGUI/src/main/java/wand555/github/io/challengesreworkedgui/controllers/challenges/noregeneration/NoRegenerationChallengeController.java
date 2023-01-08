package wand555.github.io.challengesreworkedgui.controllers.challenges.noregeneration;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import org.bukkit.inventory.ItemStack;
import wand555.github.io.challengesreworked.Common;
import wand555.github.io.challengesreworked.challenges.ChallengeCommon;
import wand555.github.io.challengesreworked.challenges.noregeneration.NoRegenerationChallenge;
import wand555.github.io.challengesreworked.challenges.noregeneration.NoRegenerationChallengeCommon;
import wand555.github.io.challengesreworkedgui.controllers.challenges.ChallengeController;

public class NoRegenerationChallengeController extends ChallengeController implements NoRegenerationChallenge {

    @FXML
    private CheckBox disableAllRegCheckBox;

    @FXML
    @Override
    protected void initialize() {
        common = new NoRegenerationChallengeCommon();
        super.initialize();

        disableAllRegCheckBox.setSelected(getCommon().isDisableAllRegeneration());
        disableAllRegCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            getCommon().setDisableAllRegeneration(newValue);
        });
    }

    @Override
    public void setDataFromCommon(Common from, boolean thisActive) {
        super.setDataFromCommon(from, thisActive);
        disableAllRegCheckBox.setSelected(getCommon().isDisableAllRegeneration());
    }

    @Override
    public ItemStack getUIDisplay() {
        return null;
    }

    @Override
    public NoRegenerationChallengeCommon getCommon() {
        return (NoRegenerationChallengeCommon) super.getCommon();
    }
}
