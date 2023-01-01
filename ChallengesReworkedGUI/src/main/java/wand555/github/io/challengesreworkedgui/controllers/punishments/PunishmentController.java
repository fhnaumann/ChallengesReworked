package wand555.github.io.challengesreworkedgui.controllers.punishments;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import wand555.github.io.challengesreworked.Common;
import wand555.github.io.challengesreworked.challenges.ChallengeCommon;
import wand555.github.io.challengesreworked.punishments.AffectType;
import wand555.github.io.challengesreworked.punishments.Punishment;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;
import wand555.github.io.challengesreworkedgui.controllers.Controller;
import wand555.github.io.challengesreworkedgui.rows.PunishmentRow;
import wand555.github.io.challengesreworkedgui.util.DisplayComboBox;

import java.util.ResourceBundle;

public abstract class PunishmentController extends Controller implements Punishment {

    protected PunishmentCommon common;

    @FXML
    protected TitledPane titledPane;

    @FXML
    protected ToggleButton activateButton;

    @FXML
    protected DisplayComboBox<AffectType> affectTypeComboBox;

    @FXML
    protected CheckBox forAllChallenges;

    protected ResourceBundle bundle = null;

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
            forAllChallenges.disableProperty().bind(Bindings.not(activateButton.selectedProperty()));
            activateButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
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
                        forAllChallenges.setSelected(false);
                    }
                }
            });
            forAllChallenges.selectedProperty().addListener((observable, oldValue, newValue) -> {
                Node title = titledPane.lookup(".title");
                if(title != null) {
                    if(newValue && activateButton.isSelected()) {
                        title.setStyle("-fx-background-color: orange");
                    }
                    else {
                        title.setStyle(null);
                    }
                }
            });
        }

    }

    public abstract PunishmentRow getAsOneLine();

    public void extractDataFromPunishmentRow(PunishmentRow row) {
        System.out.println(this);
        System.out.println(row);
        System.out.println("set in punishment controller");
        activateButton.setSelected(true); //else it wouldnt be a row to begin with
        affectTypeComboBox.setValue(row.getPunishmentController().affectTypeComboBox.getValue());
        forAllChallenges.setSelected(row.getPunishmentController().forAllChallenges.isSelected());
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

    public boolean isActive() {
        return activateButton.isSelected();
    }
}
