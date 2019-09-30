package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Control.JavaFxControlleur;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class SplashWindowRoot extends VBox {

    private final JavaFxControlleur control;

    public SplashWindowRoot(Stage stage, JavaFxControlleur ctrl) {
        control = ctrl;
        setup();
        stage.setTitle("Choose Size");
        stage.setScene(new Scene(this, 200, 350));
        stage.show();
    }

    private void setup() {
        TextField sizeOfSea = new InputNumber();
        Label labelArmyOne = new Label("Armee 1:");
        Label labelArmyTwo = new Label("Armee 2:");
        Label tailleMer = new Label("Taille de la mer:");
        TextField inputArmyOne = new InputArmy1();
        TextField inputArmyTwo = new InputArmy2();
        ComboBox<String> choosePlacement = new ComboBox();
        choosePlacement.getItems().addAll(
                "1. Emplacement aléatoire",
                "2. Choisir emplacement bateaux"
        );
        choosePlacement.getSelectionModel().selectFirst();
        Button bt = new Button("OK");
        bt.setOnAction(e -> {
            if (!sizeOfSea.getText().isEmpty() && Integer.parseInt(sizeOfSea.getText()) >= 3 && Integer.parseInt(sizeOfSea.getText()) <= 26) {
                switchToMainWindow(Integer.valueOf(sizeOfSea.getText()), inputArmyOne.getText(), inputArmyTwo.getText(), choosePlacement.getValue().charAt(0));
            } else {
                sizeOfSea.requestFocus();
            }
        });
        getChildren().addAll(labelArmyOne, inputArmyOne, labelArmyTwo, inputArmyTwo, tailleMer, sizeOfSea, choosePlacement, bt);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(20));
        setSpacing(20);
    }

    private void switchToMainWindow(Integer size, String text, String text1, char placement) {
        control.switchToMainWindow(size, text, text1, placement);
    }

    private class InputNumber extends TextField {

        InputNumber() {
            super("5");
            setAlignment(Pos.CENTER);
            setMaxWidth(150);
            installListeners();
        }

        private void installListeners() {
            textProperty().addListener((obs, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    setText(oldValue);
                }
            });

        }
    }

    private class InputArmy1 extends TextField {

        InputArmy1() {
            super("Rebelle");
            setAlignment(Pos.CENTER);
            setMaxWidth(150);

        }
    }

    private class InputArmy2 extends TextField {

        InputArmy2() {
            super("Empire");
            setAlignment(Pos.CENTER);
            setMaxWidth(150);

        }
    }
}
