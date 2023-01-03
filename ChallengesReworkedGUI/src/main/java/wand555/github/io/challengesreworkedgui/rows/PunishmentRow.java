package wand555.github.io.challengesreworkedgui.rows;

import javafx.scene.layout.HBox;
import wand555.github.io.challengesreworkedgui.controllers.punishments.PunishmentController;

public class PunishmentRow extends HBox {

    private PunishmentController punishmentController;

    public PunishmentRow() {

    }

    public PunishmentController getPunishmentController() {
        return punishmentController;
    }

    /**
     * Called when all fields are copied over.
     * @param punishmentController copied from
     */
    public void setPunishmentController(PunishmentController punishmentController) {
        this.punishmentController = punishmentController;
    }
}
