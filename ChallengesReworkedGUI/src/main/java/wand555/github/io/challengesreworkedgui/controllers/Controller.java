package wand555.github.io.challengesreworkedgui.controllers;

import wand555.github.io.challengesreworked.Common;
import wand555.github.io.challengesreworked.challenges.ChallengeCommon;

public abstract class Controller {

    /**
     * In the FXML context, each controller is instantiated when the fxml file associated
     * to that controller is loaded. A common object with default values is then used.
     * When data is loaded (imported) these controllers need to be updated internally with the
     * new common object.
     * Another use case is to set global punishments. In that case the common object is updated by
     * setting the punishments and to update the UI this method is called. In that circumstance
     * each challenge is not meant to be automatically activated.
     * @param from deserialized common object (from yml file)
     * @param thisActive if this controller should also be activated (set selected)
     */
    public abstract void setDataFromCommon(Common from, boolean thisActive);
}
