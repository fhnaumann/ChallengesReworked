package wand555.github.io.challengesreworkedgui;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

public abstract class Row extends HBox {

    protected Row() {
        super();
    }

    protected Row(double spacing) {
        super(spacing);
    }

    protected Row(Node... children) {
        super(children);
    }

    protected Row(double spacing, Node... children) {
        super(spacing, children);
    }

    public abstract Row copy();
}
