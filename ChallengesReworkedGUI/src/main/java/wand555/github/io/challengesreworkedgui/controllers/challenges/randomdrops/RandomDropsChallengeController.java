package wand555.github.io.challengesreworkedgui.controllers.challenges.randomdrops;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import org.bukkit.inventory.ItemStack;
import wand555.github.io.challengesreworked.Common;
import wand555.github.io.challengesreworked.challenges.ChallengeCommon;
import wand555.github.io.challengesreworked.challenges.randomdrops.RandomDropsChallenge;
import wand555.github.io.challengesreworked.challenges.randomdrops.RandomDropsChallengeCommon;
import wand555.github.io.challengesreworkedgui.controllers.challenges.ChallengeController;

public class RandomDropsChallengeController extends ChallengeController implements RandomDropsChallenge {

    @FXML
    private CheckBox blockDropsCheckBox;
    @FXML
    private CheckBox mobDropsCheckBox;
    @FXML
    private CheckBox craftingDropsCheckBox;
    @FXML
    private CheckBox furnaceDropsCheckBox;

    @FXML
    @Override
    protected void initialize() {
        common = new RandomDropsChallengeCommon();
        super.initialize();
        blockDropsCheckBox.setSelected(getCommon().isRandomBlockDrops());
        blockDropsCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            getCommon().setRandomBlockDrops(newValue);
        });
        mobDropsCheckBox.setSelected(getCommon().isRandomMobDrops());
        mobDropsCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            getCommon().setRandomMobDrops(newValue);
        });
        craftingDropsCheckBox.setSelected(getCommon().isRandomCraftingDrops());
        craftingDropsCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            getCommon().setRandomCraftingDrops(newValue);
        });
        furnaceDropsCheckBox.setSelected(getCommon().isRandomFurnaceDrops());
        furnaceDropsCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            getCommon().setRandomFurnaceDrops(newValue);
        });
    }

    @Override
    public void setDataFromCommon(Common from, boolean thisActive) {
        super.setDataFromCommon(from, thisActive);
        blockDropsCheckBox.setSelected(getCommon().isRandomBlockDrops());
        mobDropsCheckBox.setSelected(getCommon().isRandomMobDrops());
        craftingDropsCheckBox.setSelected(getCommon().isRandomCraftingDrops());
        furnaceDropsCheckBox.setSelected(getCommon().isRandomFurnaceDrops());
    }

    @Override
    public ItemStack getUIDisplay() {
        return null;
    }

    @Override
    public RandomDropsChallengeCommon getCommon() {
        return (RandomDropsChallengeCommon) super.getCommon();
    }
}
