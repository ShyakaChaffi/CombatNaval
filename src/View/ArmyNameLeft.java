package View;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ArmyNameLeft extends VBox {

    ArmyNameLeft(String armyName) {
        Label name = new Label(armyName);
        setLabel(name);
        this.getChildren().add(name);
    }

    private void setLabel(Label msg) {
        msg.setPrefHeight(50);
        msg.setPrefWidth(170);
        msg.setFont(Font.font(null, FontWeight.BOLD, 18));
        msg.setTextFill(Color.web("#32CD32"));
    }
}
