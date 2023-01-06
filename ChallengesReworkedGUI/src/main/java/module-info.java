module challenges.reworked.gui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.bukkit;
    requires challenges.reworked.api;
    requires boosted.yaml.spigot;
    requires atlantafx.base;

    opens wand555.github.io.challengesreworkedgui;
    //opens wand555.github.io.challengesreworkedgui to javafx.fxml;
    opens wand555.github.io.challengesreworkedgui.controllers to javafx.fxml;
    exports wand555.github.io.challengesreworkedgui.util to javafx.fxml;
    exports wand555.github.io.challengesreworkedgui;
    exports wand555.github.io.challengesreworkedgui.controllers;
    exports wand555.github.io.challengesreworkedgui.controllers.challenges;
    opens wand555.github.io.challengesreworkedgui.controllers.challenges;
    //opens wand555.github.io.challengesreworkedgui.controllers.challenges to javafx.fxml;
    exports wand555.github.io.challengesreworkedgui.controllers.goals;
    opens wand555.github.io.challengesreworkedgui.controllers.goals to javafx.fxml;
    exports wand555.github.io.challengesreworkedgui.controllers.punishments;
    opens wand555.github.io.challengesreworkedgui.controllers.punishments to javafx.fxml;
    exports wand555.github.io.challengesreworkedgui.rows;
    opens wand555.github.io.challengesreworkedgui.rows to javafx.fxml;
    exports wand555.github.io.challengesreworkedgui.exceptions;
    opens wand555.github.io.challengesreworkedgui.exceptions to javafx.fxml;
    exports wand555.github.io.challengesreworkedgui.controllers.challenges.nocrafting;
    opens wand555.github.io.challengesreworkedgui.controllers.challenges.nocrafting;
    exports wand555.github.io.challengesreworkedgui.controllers.challenges.noblockplacing;
    opens wand555.github.io.challengesreworkedgui.controllers.challenges.noblockplacing;
    exports wand555.github.io.challengesreworkedgui.controllers.challenges.noblockbreaking;
    opens wand555.github.io.challengesreworkedgui.controllers.challenges.noblockbreaking;
    exports wand555.github.io.challengesreworkedgui.controllers.challenges.nodamage;
    opens wand555.github.io.challengesreworkedgui.controllers.challenges.nodamage;

}