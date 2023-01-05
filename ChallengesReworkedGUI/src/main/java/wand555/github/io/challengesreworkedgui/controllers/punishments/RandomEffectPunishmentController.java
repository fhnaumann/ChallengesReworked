package wand555.github.io.challengesreworkedgui.controllers.punishments;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;
import wand555.github.io.challengesreworked.Common;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;
import wand555.github.io.challengesreworked.punishments.randomeffect.RandomEffectPunishment;
import wand555.github.io.challengesreworked.punishments.randomeffect.RandomEffectPunishmentCommon;
import wand555.github.io.challengesreworked.punishments.randomitem.RandomItemPunishmentCommon;
import wand555.github.io.challengesreworkedgui.ChallengeApplication;
import wand555.github.io.challengesreworkedgui.rows.PunishmentRow;

import java.io.IOException;

public class RandomEffectPunishmentController extends PunishmentController implements RandomEffectPunishment {

    @FXML
    private TextField howManyTextField;
    @FXML
    private TextField durationTextField;

    @FXML
    @Override
    protected void initialize() {
        common = new RandomEffectPunishmentCommon();
        super.initialize();

        howManyTextField.setTextFormatter(new TextFormatter<>(
                new IntegerStringConverter(),
                getCommon().getHowManyEffects(),
                change -> {
                    String newText = change.getControlNewText();
                    if(newText.isEmpty()) {
                        getCommon().setHowManyEffects(1);
                        return change;
                    }
                    if(newText.matches("^[0-9]+$")) {
                        int writtenAmount = Integer.parseInt(newText);
                        if(writtenAmount <= 99999) {
                            getCommon().setHowManyEffects(writtenAmount);
                            return change;
                        }
                    }
                    return null;
                }));
        durationTextField.setTextFormatter(new TextFormatter<>(
                new IntegerStringConverter(),
                getCommon().getEffectDuration(),
                change -> {
                    String newText = change.getControlNewText();
                    if(newText.isEmpty()) {
                        getCommon().setEffectDuration(1);
                        return change;
                    }
                    if(newText.matches("^[0-9]+$")) {
                        int writtenAmount = Integer.parseInt(newText);
                        if(writtenAmount <= 99999) {
                            getCommon().setEffectDuration(writtenAmount);
                            return change;
                        }
                    }
                    return null;
                }));
    }

    @Override
    public PunishmentRow getAsOneLine() {
        try {
            FXMLLoader loader = new FXMLLoader(ChallengeApplication.class.getResource("punishments/random_effect_punishment_row.fxml"), bundle);
            PunishmentRow root = loader.load();
            RandomEffectPunishmentController rowController = loader.getController();
            rowController.setOnlyGlobalChanges(isOnlyGlobalChanges());

            rowController.common = getCommon();
            rowController.howManyTextField.setText(Integer.toString(getCommon().getHowManyEffects()));
            rowController.durationTextField.setText(Integer.toString(getCommon().getEffectDuration()));
            rowController.affectTypeComboBox.setValue(getCommon().getAffectType());

            //changes needed if only global
            if(isOnlyGlobalChanges()) {
                rowController.affectTypeComboBox.setDisable(true);
                rowController.howManyTextField.setDisable(true);
                rowController.durationTextField.setDisable(true);
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
        RandomEffectPunishmentController randomEffectPunishmentController = (RandomEffectPunishmentController) row.getPunishmentController();
        howManyTextField.setText(Integer.toString(randomEffectPunishmentController.getCommon().getHowManyEffects()));
        durationTextField.setText(Integer.toString(randomEffectPunishmentController.getCommon().getEffectDuration()));
        if(isOnlyGlobalChanges()) {
            howManyTextField.setDisable(true);
            durationTextField.setDisable(true);
        }

    }

    @Override
    public void setDataFromCommon(Common from, boolean thisActive) {
        super.setDataFromCommon(from, thisActive);
        howManyTextField.setText(Integer.toString(getCommon().getHowManyEffects()));
        durationTextField.setText(Integer.toString(getCommon().getEffectDuration()));
        if(isOnlyGlobalChanges()) {
            howManyTextField.setDisable(true);
            durationTextField.setDisable(true);
        }
    }

    @Override
    public RandomEffectPunishmentCommon getCommon() {
        return (RandomEffectPunishmentCommon) super.getCommon();
    }

    @Override
    public void setOnlyGlobalChanges(boolean onlyGlobalChanges) {
        super.setOnlyGlobalChanges(onlyGlobalChanges);
        howManyTextField.setDisable(onlyGlobalChanges);
        durationTextField.setDisable(onlyGlobalChanges);
    }
}
