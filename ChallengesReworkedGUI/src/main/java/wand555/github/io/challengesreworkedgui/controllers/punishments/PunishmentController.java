package wand555.github.io.challengesreworkedgui.controllers.punishments;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import wand555.github.io.challengesreworked.Common;
import wand555.github.io.challengesreworked.punishments.AffectType;
import wand555.github.io.challengesreworked.punishments.Punishment;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;
import wand555.github.io.challengesreworkedgui.controllers.Controller;
import wand555.github.io.challengesreworkedgui.controllers.challenges.ChallengeController;
import wand555.github.io.challengesreworkedgui.rows.PunishmentRow;
import wand555.github.io.challengesreworkedgui.util.DisplayComboBox;

import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class PunishmentController extends Controller implements Punishment {

    protected PunishmentCommon common;

    private ChallengeController source;

    @FXML
    protected TitledPane titledPane;

    @FXML
    protected ToggleButton activateButton;

    @FXML
    protected DisplayComboBox<AffectType> affectTypeComboBox;

    protected ResourceBundle bundle = null;

    private boolean inIncompatibleChallenge = false;

    @FXML
    protected void initialize() {
        bundle = ResourceBundle.getBundle("wand555/github/io/challengesreworkedgui/lang_punishments");
        affectTypeComboBox.setDisplayText(affectType -> switch(affectType) {
            case CAUSER -> bundle.getString("punishment.affecttype.causer");
            case ALL -> bundle.getString("punishment.affecttype.all");
        });
        affectTypeComboBox.setItems(FXCollections.observableArrayList(AffectType.values()));
        affectTypeComboBox.setValue(getCommon().getAffectType());
        affectTypeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            getCommon().setAffectType(newValue);
        });
        //will be null when loaded as a row
        if(activateButton != null) {
            activateButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if(inIncompatibleChallenge) {
                    Notifications.create()
                            .title(bundle.getString("incompatible.title"))
                            .text(bundle.getString("incompatible.text"))
                            .owner(titledPane.getScene().getWindow())
                            .hideAfter(Duration.seconds(5))
                            .showError();
                    return;
                }
                System.out.println("changed pane color");
                System.out.println(titledPane.getChildrenUnmodifiable());
                Node title = titledPane.lookup(".title");
                if(title != null) {
                    if(newValue) {
                        activateButton.setText(bundle.getString("punishment.deactivate"));
                        title.setStyle("-fx-background-color: green;");

                    }
                    else {
                        activateButton.setText(bundle.getString("punishment.activate"));
                        title.setStyle(null);
                    }
                }
            });

            /*
            getIncompatibleChallenges().stream()
                    .filter(aClass -> OverviewController.getInstance().isActive(aClass))
                    .findFirst()
                    .ifPresent(challengeClazz -> {
                        activateButton.setDisable(true);
                    });
            */
        }

    }

    public final void setSource(ChallengeController source) {
        this.source = source;
        inIncompatibleChallenge = getIncompatibleChallenges().stream()
                .anyMatch(controllerClazz -> controllerClazz.isInstance(source));
    }

    protected final ChallengeController getSource() {
        return source;
    }

    public abstract PunishmentRow getAsOneLine();

    public void extractDataFromPunishmentRow(PunishmentRow row) {
        System.out.println(this);
        System.out.println(row);
        System.out.println("set in punishment controller");
        activateButton.setSelected(true); //else it wouldnt be a row to begin with
        affectTypeComboBox.setValue(row.getPunishmentController().affectTypeComboBox.getValue());
    }

    @Override
    public void setDataFromCommon(Common from) {
        common = (PunishmentCommon) from.copy();
        activateButton.setSelected(true); //set selected because this method is only called if its active
    }

    @Override
    public PunishmentCommon getCommon() {
        return common;
    }

    /**
     * Checks if this challenge is active.
     * If the punishment determined that it is incompatible with this challenge, it is never active
     * @return if activate button is selected (or false if it is incompatible with the challenge)
     */
    public boolean isActive() {
        return activateButton.isSelected() && !inIncompatibleChallenge;
    }
}
