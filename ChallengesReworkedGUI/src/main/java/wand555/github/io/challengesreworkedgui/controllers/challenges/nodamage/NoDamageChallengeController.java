package wand555.github.io.challengesreworkedgui.controllers.challenges.nodamage;

import javafx.fxml.FXML;
import org.bukkit.inventory.ItemStack;
import wand555.github.io.challengesreworked.challenges.PunishableChallengeCommon;
import wand555.github.io.challengesreworked.challenges.nodamage.NoDamageChallenge;
import wand555.github.io.challengesreworked.challenges.nodamage.NoDamageChallengeCommon;
import wand555.github.io.challengesreworkedgui.controllers.challenges.PunishableChallengeController;

import java.util.List;

public class NoDamageChallengeController extends PunishableChallengeController implements NoDamageChallenge {


    @FXML
    @Override
    protected void initialize() {
        common = new NoDamageChallengeCommon();
        super.initialize();
    }

    @Override
    public ItemStack getUIDisplay() {
        return null;
    }

    @Override
    public NoDamageChallengeCommon getCommon() {
        return (NoDamageChallengeCommon) super.getCommon();
    }
}
