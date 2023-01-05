package wand555.github.io.challengesreworkedgui.controllers.punishments;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.util.converter.IntegerStringConverter;
import wand555.github.io.challengesreworked.Common;
import wand555.github.io.challengesreworked.punishments.AffectType;
import wand555.github.io.challengesreworked.punishments.health.HealthPunishment;
import wand555.github.io.challengesreworked.punishments.health.HealthPunishmentCommon;
import wand555.github.io.challengesreworkedgui.ChallengeApplication;
import wand555.github.io.challengesreworkedgui.rows.PunishmentRow;

import java.io.IOException;
import java.util.ResourceBundle;

public class HealthPunishmentController extends PunishmentController implements HealthPunishment {

    @FXML
    private TextField heartsTextField;

    @FXML
    protected void initialize() {
        common = new HealthPunishmentCommon(0, AffectType.CAUSER, 1);
        super.initialize();
        heartsTextField.setTextFormatter(new TextFormatter<>(
                new IntegerStringConverter(),
                getCommon().getHealthAmount(),
                change -> {
                    String newText = change.getControlNewText();
                    if(newText.isEmpty()) {
                        getCommon().setHealthAmount(1);
                        return change;
                    }
                    if(newText.matches("^[0-9]+$")) {
                        int writtenAmount = Integer.parseInt(newText);
                        if(writtenAmount <= 99999) {
                            getCommon().setHealthAmount(writtenAmount);
                            return change;
                        }
                    }
                    return null;
                }));
    }

    @Override
    public PunishmentRow getAsOneLine() {
        try {
            FXMLLoader loader = new FXMLLoader(ChallengeApplication.class.getResource("punishments/health_punishment_row.fxml"), bundle);
            PunishmentRow root = loader.load();
            HealthPunishmentController rowController = loader.getController();
            rowController.setOnlyGlobalChanges(isOnlyGlobalChanges());

            rowController.common = getCommon();
            rowController.heartsTextField.setText(Integer.toString(getHealthAmount()));
            rowController.affectTypeComboBox.setValue(getCommon().getAffectType());

            // changes needed if only global changes is true
            if(isOnlyGlobalChanges()) {
                rowController.affectTypeComboBox.setEditable(false);
                rowController.affectTypeComboBox.setDisable(true);
                rowController.heartsTextField.setEditable(false);
                rowController.heartsTextField.setDisable(true);
            }


            root.setPunishmentController(rowController);
            return root;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void extractDataFromPunishmentRow(PunishmentRow row) {
        super.extractDataFromPunishmentRow(row);
        HealthPunishmentController healthPunishmentController = (HealthPunishmentController) row.getPunishmentController();
        heartsTextField.setText(healthPunishmentController.heartsTextField.getText());
        System.out.println(heartsTextField.getText() + "!!!");
        if(isOnlyGlobalChanges()) {
            heartsTextField.setEditable(false);
            heartsTextField.setDisable(true);
        }
    }

    @Override
    public void setDataFromCommon(Common from, boolean thisActive) {
        super.setDataFromCommon(from, thisActive);
        heartsTextField.setText(Integer.toString(getCommon().getHealthAmount()));
        if(isOnlyGlobalChanges()) {
            heartsTextField.setDisable(true);
        }
    }

    @Override
    public HealthPunishmentCommon getCommon() {
        return (HealthPunishmentCommon) common;
    }

    @Override
    public void setOnlyGlobalChanges(boolean onlyGlobalChanges) {
        super.setOnlyGlobalChanges(onlyGlobalChanges);
        heartsTextField.setDisable(onlyGlobalChanges);
    }
}
