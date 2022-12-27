package wand555.github.io.challengesreworkedgui;

import javafx.scene.layout.HBox;
import wand555.github.io.challengesreworkedgui.controller.PunishmentController;

public class PunishmentRow extends HBox {

    private PunishmentController punishmentController;

    public PunishmentRow() {

    }

    public PunishmentController getPunishmentController() {
        return punishmentController;
    }

    public void setPunishmentController(PunishmentController punishmentController) {
        this.punishmentController = punishmentController;
    }
}
