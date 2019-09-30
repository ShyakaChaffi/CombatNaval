package View;

import Control.JavaFxControlleur;
import Model.Army;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class LeftSidePanelPlacement extends VBox {

    public LeftSidePanelPlacement(Army army, JavaFxControlleur ctrl, int size) {
        this.getChildren().add(new ArmyNameLeft(army.getName()));
        this.getChildren().add(new HarborLeft(army.getName(), army.getListBoatOfArmy(), ctrl, size));
        setSidePanel();
    }

    private void setSidePanel() {
        setMaxHeight(300);
        setMaxWidth(200);
        setPadding(new Insets(20, 5, 20, 5));
        setStyle("-fx-border-color: green");
    }

}
