package wand555.github.io.challengesreworkedgui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public abstract class AmountableRow extends Row {

    protected int amount;

    public AmountableRow(int amount) {
        super(5);
        this.amount = amount;
        TextField textField = new TextField(String.valueOf(amount));
        textField.setAlignment(Pos.CENTER);
        textField.setPrefWidth(40);
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(isValid(newValue)) {
                setAmount(Integer.parseInt(newValue));
            }
        });
        getChildren().add(textField);
    }

    private boolean isValid(String userInput) {
        //is numeric and > 0 but below Integer.MAX_VALUE
        return true;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
