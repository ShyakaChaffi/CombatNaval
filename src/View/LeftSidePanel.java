package View;

import Model.Army;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LeftSidePanel extends SidePanel {

    public LeftSidePanel(Army army) {
        super(army);
        this.getChildren().add(new ArmyNameLeft(army.getName()));
        setLabel(this.armyComposition);
        setSidePanel(this.armyComposition);
    }

    private void setSidePanel(Label army) {
        getChildren().add(army);
        setMaxHeight(300);
        setMaxWidth(200);
        setPadding(new Insets(20, 5, 20, 5));
        setStyle("-fx-border-color: green");
    }

    private void setLabel(Label msg) {
        msg.setPrefHeight(200);
        msg.setPrefWidth(170);
        msg.setFont(Font.font(null, FontWeight.BOLD, 12));
        msg.setTextFill(Color.web("#32CD32"));
    }
}
