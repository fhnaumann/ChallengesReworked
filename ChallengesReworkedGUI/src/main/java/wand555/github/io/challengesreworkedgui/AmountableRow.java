package wand555.github.io.challengesreworkedgui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.util.converter.IntegerStringConverter;

public abstract class AmountableRow extends Row {

    protected int amount;

    public AmountableRow(int amount) {
        super(5);
        this.amount = amount;
        TextField textField = new TextField(String.valueOf(amount));
        textField.setAlignment(Pos.CENTER);
        textField.setPrefWidth(40);
        textField.setTextFormatter(new TextFormatter<>(
                new IntegerStringConverter(),
                getAmount(),
                change -> {
                    String newText = change.getControlNewText();
                    if(newText.isEmpty()) {
                        setAmount(0);
                        return change;
                    }
                    if(newText.matches("^[0-9]+$")) {
                        int writtenAmount = Integer.parseInt(newText);
                        if(writtenAmount <= 99999) {
                            setAmount(writtenAmount);
                            return change;
                        }
                    }
                    return null;
                }));
        getChildren().add(textField);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
