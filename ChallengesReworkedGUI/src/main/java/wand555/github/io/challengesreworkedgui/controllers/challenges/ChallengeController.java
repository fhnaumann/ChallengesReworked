package wand555.github.io.challengesreworkedgui.controllers.challenges;

import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import wand555.github.io.challengesreworked.Common;
import wand555.github.io.challengesreworked.challenges.Challenge;
import wand555.github.io.challengesreworked.challenges.ChallengeCommon;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import wand555.github.io.challengesreworkedgui.ResourceBundleWrapper;
import wand555.github.io.challengesreworkedgui.controllers.Controller;
import wand555.github.io.challengesreworkedgui.controllers.OverviewController;

import java.util.ResourceBundle;

public abstract class ChallengeController extends Controller implements Challenge {

    protected ChallengeCommon common;

    @FXML
    protected TitledPane titledPane;

    @FXML
    protected ToggleButton activateButton;

    protected ResourceBundle bundle = null;

    private boolean incompatibleGlobalPunishmentActive = false;

    @FXML
    protected void initialize() {
        bundle = new ResourceBundleWrapper(ResourceBundle.getBundle("wand555/github/io/challengesreworkedgui/lang_challenges"));
        activateButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            Node title = titledPane.lookup(".title");

            incompatibleGlobalPunishmentActive = getIncompatiblePunishments().stream()
                    .anyMatch(aClass -> OverviewController.getInstance().isActiveGlobalPunishment(aClass));
            if(incompatibleGlobalPunishmentActive) {
                Notifications.create()
                        .title(bundle.getString("incompatible.title"))
                        .text(bundle.getString("incompatible.text"))
                        .owner(titledPane.getScene().getWindow())
                        .hideAfter(Duration.seconds(5))
                        .showError();
                return;
            }

            if(newValue) {
                activateButton.setText(bundle.getString("challenge.deactivate"));
                title.setStyle("-fx-background-color: green;");
            }
            else {
                activateButton.setText(bundle.getString("challenge.activate"));
                title.setStyle(null);
            }
        });
    }

    /**
     * "from" is put into the "common" variable.
     *
     * @param from deserialized common object
     */
    @Override
    public void setDataFromCommon(Common from, boolean thisActive) {
        common = (ChallengeCommon) from.copy();
        if(thisActive) {
            activateButton.setSelected(true); //set selected because this method is only called if its active
        }
    }

    public boolean isActive() {
        return activateButton.isSelected() && !incompatibleGlobalPunishmentActive;
    }

    public void setIncompatibleGlobalPunishmentActive(boolean incompatibleGlobalPunishmentActive) {
        this.incompatibleGlobalPunishmentActive = incompatibleGlobalPunishmentActive;
    }

    @Override
    public ChallengeCommon getCommon() {
        return common;
    }
}
