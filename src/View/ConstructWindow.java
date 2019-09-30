package View;

import Control.JavaFxControlleur;
import Model.Army;
import Model.SeaFacadeBuilder;
import java.util.Observable;
import java.util.Observer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ConstructWindow implements Observer {

    private final int SIZE;
    private final JavaFxControlleur control;
    static BoardGame boardGame;
    static BorderPane root = new BorderPane();
    static VBox messagePlayer = new VBox();
    static VBox centerPanel = new VBox();
    static VBox downPanel = new VBox();
    static VBox infoBox = new VBox();

    public ConstructWindow(Stage stage, int size, JavaFxControlleur ctrl, Army armee1, Army armee2) {
        control = ctrl;
        SIZE = size;
        boardGame = new BoardGame(size, ctrl);
        root.getStyleClass().add("root");
        stage.setScene(new Scene(root, 800, 600));
        stage.sizeToScene();
        stage.setTitle("Bataille Navale - Dooremont Jeremy / Shyaka Chaffi");
        stage.show();
    }

    @Override
    public void update(Observable o, Object arg) {
        SeaFacadeBuilder model = (SeaFacadeBuilder) o;
        root.getChildren().clear();
        messagePlayer.getChildren().clear();
        centerPanel.getChildren().clear();
        boardGame.getChildren().clear();
        view(model);
    }

    private void view(SeaFacadeBuilder model) {
        boardGame.createBoardGame(model);
        VBox left = new LeftSidePanelPlacement(model.getArmyOne(), control, SIZE);
        BorderPane.setAlignment(left, Pos.CENTER);
        VBox right = new RightSidePanelPlacement(model.getArmyTwo(), control, SIZE);
        BorderPane.setAlignment(right, Pos.CENTER);
        root.setLeft(left);
        root.setRight(right);
        createCenter();
        root.getStylesheets().add("view/BoxView.css");
        root.setCenter(centerPanel);
    }

    private static void createCenter() {
        centerPanel.getChildren().add(boardGame);
        centerPanel.setPrefSize(500, 1000);
        centerPanel.setPadding(new Insets(0, 10, 20, 5));
    }

    public void printMsgPlace(Army currentPlayer) {
        downPanel.getChildren().clear();
        downPanel.getChildren().add(messagePlayer);
        downPanel.setMinHeight(75);
        root.setBottom(downPanel);
        messagePlayer.getChildren().clear();
        Label msg = new Label(currentPlayer.getName() + " - sélectionnez le bateau à placer");
        setLabel(msg);
        messagePlayer.getChildren().add(msg);
        messagePlayer.setAlignment(Pos.CENTER);
    }

    public void printErrorMsgEmptyBoat(Army currentPlayer) {
        downPanel.getChildren().clear();
        downPanel.getChildren().add(messagePlayer);
        downPanel.setMinHeight(75);
        root.setBottom(downPanel);
        messagePlayer.getChildren().clear();
        Label msg = new Label(currentPlayer.getName() + " - Vous n'avez encore sélectionner aucun bateaux dans le port");
        setLabel(msg);
        messagePlayer.getChildren().add(msg);
        messagePlayer.setAlignment(Pos.CENTER);
    }

    public void printPlaceBoat(Army currentPlayer) {
        downPanel.getChildren().clear();
        downPanel.getChildren().add(messagePlayer);
        downPanel.setMinHeight(75);
        root.setBottom(downPanel);
        messagePlayer.getChildren().clear();
        Label msg = new Label(currentPlayer.getName() + " - Sélectionnez la case de la mer ou placer le bateau");
        setLabel(msg);
        messagePlayer.getChildren().add(msg);
        messagePlayer.setAlignment(Pos.CENTER);
    }

    public void printErrorMsgWrongBoat(Army currentPlayer) {
        downPanel.getChildren().clear();
        downPanel.getChildren().add(messagePlayer);
        downPanel.setMinHeight(75);
        root.setBottom(downPanel);
        messagePlayer.getChildren().clear();
        Label msg = new Label(currentPlayer.getName() + " - Ce bateau ne fait pas partie de votre armée");
        setLabel(msg);
        messagePlayer.getChildren().add(msg);
        messagePlayer.setAlignment(Pos.CENTER);
    }

    public static void printMsgWrongMove(Army currentPlayer) {
        messagePlayer.getChildren().clear();
        Label msg = new Label(currentPlayer.getName() + " - Sélectionnez une position valide");
        setLabel(msg);
        messagePlayer.getChildren().add(msg);
    }

    private static void setLabel(Label msg) {
        msg.setAlignment(Pos.CENTER);
        msg.setPrefSize(1000, 50);
        msg.setFont(Font.font(null, FontWeight.BOLD, 15));
        msg.setTextFill(Color.web("#FFFFFF"));
    }

}
