module challenges.reworked.gui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.bukkit;
    requires challenges.reworked.plugin;

    opens wand555.github.io.challengesreworkedgui to javafx.fxml;
    opens wand555.github.io.challengesreworkedgui.controller to javafx.fxml;
    exports wand555.github.io.challengesreworkedgui;
    exports wand555.github.io.challengesreworkedgui.controller;

}