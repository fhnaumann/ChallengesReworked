package wand555.github.io.challengesreworkedgui.controllers;

import wand555.github.io.challengesreworked.Common;
import wand555.github.io.challengesreworked.challenges.ChallengeCommon;

public abstract class Controller {

    /**
     * In the FXML context, each controller is instantiated when the fxml file associated
     * to that controller is loaded. A common object with default values is then used.
     * When data is loaded (imported) these controllers need to be updated internally with the
     * new common object.
     * @param from deserialized common object (from yml file)
     */
    public abstract void setDataFromCommon(Common from);
}
