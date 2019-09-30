package View;

import Model.Army;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class RightSidePanel extends SidePanel {

    public RightSidePanel(Army army) {
        super(army);
        this.getChildren().add(new ArmyNameRight(army.getName()));
        setLabel(this.armyComposition);
        setSidePanel(this.armyComposition);
    }

    private void setSidePanel(Label army) {
        getChildren().add(army);
        setMaxHeight(300);
        setMaxWidth(200);
        setPadding(new Insets(10, 5, 10, 5));
        setStyle("-fx-border-color: #FF0000");
    }

    private void setLabel(Label msg) {
        msg.setPrefHeight(200);
        msg.setPrefWidth(170);
        msg.setFont(Font.font(null, FontWeight.BOLD, 12));
        msg.setTextFill(Color.web("#FF0000"));
    }
}
