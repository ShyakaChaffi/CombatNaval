package View;

import Control.JavaFxControlleur;
import Model.Army;
import Model.SeaFacade;
import Model.Position;
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

public class MainWindowRoot implements Observer {

    private final int SIZE;
    private final JavaFxControlleur control;
    static BoardGame boardGame;
    static BorderPane root = new BorderPane();
    static VBox messagePlayer = new VBox();
    static VBox centerPanel = new VBox();
    static VBox downPanel = new VBox();
    static VBox infoBox = new VBox();

    public MainWindowRoot(Stage stage, int size, JavaFxControlleur ctrl, Army armee1, Army armee2) {
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
        SeaFacade model = (SeaFacade) o;
        root.getChildren().clear();
        messagePlayer.getChildren().clear();
        centerPanel.getChildren().clear();
        downPanel.getChildren().clear();
        boardGame.getChildren().clear();
        view(model);
    }

    private void view(SeaFacade model) {
        boardGame.createBoardGame(model);
        VBox left = new LeftSidePanel(model.getArmyOne());
        root.setAlignment(left, Pos.CENTER);
        VBox right = new RightSidePanel(model.getArmyTwo());
        root.setAlignment(right, Pos.CENTER);
        root.setLeft(left);
        root.setRight(right);
        createCenter();
        createBottom();
        root.getStylesheets().add("view/BoxView.css");
        root.setCenter(centerPanel);
        root.setBottom(downPanel);
    }

    private static void createBottom() {
        downPanel.setMinHeight(75);
        downPanel.getChildren().add(messagePlayer);
        downPanel.getChildren().add(infoBox);
    }

    private static void createCenter() {
        centerPanel.getChildren().add(boardGame);
        centerPanel.setPrefSize(500, 1000);
        centerPanel.setPadding(new Insets(0, 10, 20, 5));
    }

    public void printMsgShoot(Army currentPlayer) {
        messagePlayer.getChildren().clear();
        Label msg = new Label(currentPlayer.getName() + " - sélectionnez le bateau pour tirer");
        setLabel(msg);
        messagePlayer.getChildren().add(msg);
        messagePlayer.setAlignment(Pos.CENTER);
    }

    public void printMsgWinner(Army currentPlayer) {
        messagePlayer.getChildren().clear();
        Label msg = new Label(currentPlayer.getName() + " - Félicitation ! Vous avez gagné ! ");
        setLabel(msg);
        messagePlayer.getChildren().add(msg);
        messagePlayer.setAlignment(Pos.CENTER);
    }

    public void printMsgMove(Army currentPlayer) {
        messagePlayer.getChildren().clear();
        Label msg = new Label(currentPlayer.getName() + " - sélectionnez le bateau à déplacer");
        setLabel(msg);
        messagePlayer.getChildren().add(msg);
    }

    public static void printMsgWrongMove(Army currentPlayer) {
        messagePlayer.getChildren().clear();
        Label msg = new Label(currentPlayer.getName() + " - Sélectionnez une position valide");
        setLabel(msg);
        messagePlayer.getChildren().add(msg);
    }

    public static void printMineRadio() {
        downPanel.getChildren().remove(infoBox);
        Label msg = new Label(" Booom ! Mine Radio Active");
        setLabel(msg);
        infoBox.getChildren().clear();
        infoBox.getChildren().add(msg);
        infoBox.setAlignment(Pos.CENTER);
        downPanel.getChildren().add(infoBox);
    }

    public static void printMine() {
        downPanel.getChildren().remove(infoBox);
        Label msg = new Label(" Booom ! Mine Normale");
        setLabel(msg);
        infoBox.getChildren().clear();
        infoBox.getChildren().add(msg);
        infoBox.setAlignment(Pos.CENTER);
        downPanel.getChildren().add(infoBox);
    }

    public static void printMsgRangeOfBoat(int range) {
        downPanel.getChildren().remove(infoBox);
        Label msg = new Label("La portée est de : " + range);
        setLabel(msg);
        infoBox.getChildren().clear();
        infoBox.getChildren().add(msg);
        infoBox.setAlignment(Pos.CENTER);
        downPanel.getChildren().add(infoBox);
    }

    public void clearMessagesInfo() {
        downPanel.getChildren().remove(infoBox);
        infoBox.getChildren().clear();
    }

    private static void setLabel(Label msg) {
        msg.setAlignment(Pos.CENTER);
//        msg.setPrefSize(1000, 50);
        msg.setFont(Font.font(null, FontWeight.BOLD, 15));
        msg.setTextFill(Color.web("#FFFFFF"));
    }

    public void showPossiblePosition(Position currentPosition, SeaFacade facade) {
        boardGame.showPossiblePosition(currentPosition, facade);
    }

    public void erasePossiblePosition(Position oldPosition, SeaFacade facade) {
        boardGame.erasePossiblePosition(oldPosition, facade);
    }

}
