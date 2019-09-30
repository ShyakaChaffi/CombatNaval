package View;

import Control.JavaFxControlleur;
import Model.Boat;
import Model.BoatType;
import Model.MainClassSeaFacade;
import Model.MineType;
import Model.Position;
import Model.SeaFacade;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

public class BoardGame extends GridPane {

    // Change attribut showMine pour montrer les mines ou non
    private final boolean showMine = true;
    private final JavaFxControlleur control;
    private final int SIZE;

    BoardGame(int size, JavaFxControlleur ctrl) {
        this.control = ctrl;
        this.SIZE = size;
        setSizeConstraints();
        getStyleClass().add("board");
        setPrefSize(500, 850);
    }

    private void setSizeConstraints() {
        for (int i = 0; i < this.SIZE + 1; ++i) {
            if (i == 0) {
                ColumnConstraints cc = new ColumnConstraints();
                cc.setPercentWidth(60 / this.SIZE);
                cc.setHalignment(HPos.CENTER);
                getColumnConstraints().add(cc);
                RowConstraints rc = new RowConstraints();
                rc.setPercentHeight(50 / this.SIZE);
                rc.setValignment(VPos.CENTER);
                getRowConstraints().add(rc);
            } else {
                ColumnConstraints cc = new ColumnConstraints();
                cc.setPercentWidth(120 / this.SIZE);
                cc.setHalignment(HPos.CENTER);
                getColumnConstraints().add(cc);
                RowConstraints rc = new RowConstraints();
                rc.setPercentHeight(120 / this.SIZE);
                rc.setValignment(VPos.CENTER);
                getRowConstraints().add(rc);

            }
        }
    }

    private Label getLabelLine(int column) {
        return new LabelPosition(String.valueOf(column));
    }

    private Label getLabelColumn(int line) {
        return new LabelPosition(intToChar(line));
    }

    private void showMineRadio(MainClassSeaFacade facade) {
        ArrayList<Position> minePosition = facade.getListPositionsOfMine();
        for (Position p : minePosition) {
            if (facade.getMineInPosition(p).getType() == MineType.RADIO && facade.getMineInPosition(p).getRadio()) {
                add(new MineRadioExplodedView(p.getColumn() + 1, p.getLine() + 1), p.getColumn() + 1, p.getLine() + 1);
            }
        }
    }

    private void insertMine(MainClassSeaFacade facade) {
        ArrayList<Position> minePosition = facade.getListPositionsOfMine();
        for (Position p : minePosition) {
            if (facade.getMineInPosition(p).getType() == MineType.NORMAL) {
                add(new MineView(p.getColumn() + 1, p.getLine() + 1), p.getColumn() + 1, p.getLine() + 1);
            } else if (facade.getMineInPosition(p).getType() == MineType.RADIO && !facade.getMineInPosition(p).getRadio()) {
                add(new MineRadioView(p.getColumn() + 1, p.getLine() + 1), p.getColumn() + 1, p.getLine() + 1);
            }
        }
    }

    private void insertBoats(MainClassSeaFacade facade) {
        insertBoatsForPlayerOne(facade.getArmyOne().getListBoatOfArmy());
        insertBoatsForPlayerTwo(facade.getArmyTwo().getListBoatOfArmy());
    }

    private void insertBoatsForPlayerOne(List<Boat> boats) {
        for (Boat bat : boats) {
            Position p = bat.getPos();
            if (p != null) {
                if (bat.getType() == BoatType.SMALL) {
                    add(new SmallBoatViewArmyOne(p.getColumn() + 1, p.getLine() + 1), p.getColumn() + 1, p.getLine() + 1);
                } else if (bat.getType() == BoatType.BIG) {
                    add(new BigBoatViewArmyOne(p.getColumn() + 1, p.getLine() + 1), p.getColumn() + 1, p.getLine() + 1);
                }
            }
        }
    }

    private void insertBoatsForPlayerTwo(List<Boat> boats) {
        for (Boat bat : boats) {
            Position p = bat.getPos();
            if (p != null) {
                if (bat.getType() == BoatType.SMALL) {
                    add(new SmallBoatViewArmyTwo(p.getColumn() + 1, p.getLine() + 1), p.getColumn() + 1, p.getLine() + 1);
                } else if (bat.getType() == BoatType.BIG) {
                    add(new BigBoatViewArmyTwo(p.getColumn() + 1, p.getLine() + 1), p.getColumn() + 1, p.getLine() + 1);
                }
            }
        }
    }

    private static String intToChar(int valeur) {
        char car = (char) (valeur + (int) 'A' - 1);
        return Character.toString(car);
    }

    public void showPossiblePosition(Position currentPosition, SeaFacade facade) {
        ArrayList<Position> possiblePositions = facade.getPositionsForMove(facade.getCaseInPosition(currentPosition));
        for (Position p : possiblePositions) {
            add(new PositionView(p.getColumn() + 1, p.getLine() + 1), p.getColumn() + 1, p.getLine() + 1);
        }
    }

    public void erasePossiblePosition(Position oldPosition, SeaFacade facade) {
        ArrayList<Position> possiblePositions = facade.getPositionsForMove(facade.getCaseInPosition(oldPosition));
        for (Position p : possiblePositions) {
            add(new EmptyBoxView(p.getColumn() + 1, p.getLine() + 1), p.getColumn() + 1, p.getLine() + 1);
        }
        if (facade.positionInArmy(oldPosition, facade.getArmyOne())) {
            if (facade.getBoatInPosition(oldPosition).getType() == BoatType.SMALL) {
                add(new SmallBoatViewArmyOne(oldPosition.getColumn() + 1, oldPosition.getLine() + 1), oldPosition.getColumn() + 1, oldPosition.getLine() + 1);
            } else {
                add(new BigBoatViewArmyOne(oldPosition.getColumn() + 1, oldPosition.getLine() + 1), oldPosition.getColumn() + 1, oldPosition.getLine() + 1);
            }
        } else if (facade.positionInArmy(oldPosition, facade.getArmyTwo())) {
            if (facade.getBoatInPosition(oldPosition).getType() == BoatType.SMALL) {
                add(new SmallBoatViewArmyTwo(oldPosition.getColumn() + 1, oldPosition.getLine() + 1), oldPosition.getColumn() + 1, oldPosition.getLine() + 1);
            } else {
                add(new BigBoatViewArmyTwo(oldPosition.getColumn() + 1, oldPosition.getLine() + 1), oldPosition.getColumn() + 1, oldPosition.getLine() + 1);
            }
        }
    }

    void createBoardGame(MainClassSeaFacade model) {
        for (int line = 0; line < model.getSize() + 1; ++line) {
            createColumBoardGame(line, model);
        }
        insertBoats(model);
        showMineRadio(model);
        if (this.showMine) {
            insertMine(model);
        }
    }

    private void createColumBoardGame(int line, MainClassSeaFacade model) {
        for (int column = 0; column < model.getSize() + 1; ++column) {
            createCharColum(line, column, model);
            createNumberLine(line, column, model);
            createEmptyCase(line, column, model);
        }
    }

    private void createCharColum(int line, int column, MainClassSeaFacade model) {
        if (column == 0 && line > 0 && line < model.getSize() + 1) {
            add(getLabelColumn(line), line, column);
        }
    }

    private void createNumberLine(int line, int column, MainClassSeaFacade model) {
        if (column > 0 && line == 0 && column < model.getSize() + 1) {
            add(getLabelLine(column), line, column);
        }
    }

    private void createEmptyCase(int line, int column, MainClassSeaFacade model) {
        if (column > 0 && line > 0 && line < model.getSize() + 1) {
            add(new EmptyBoxView(line, column), line, column);
        }
    }

    ////////////////////////////////////////////////////////////////////
    // La vue d'une "case"
    private abstract class BoxView extends Pane {

        public BoxView() {
            getStylesheets().add("view/BoxView.css");
        }
    }

    // La vue d'une "case" vide
    private class EmptyBoxView extends BoxView {

        public EmptyBoxView(int x, int y) {
            getStyleClass().add("empty");
            setOnMouseClicked(e -> control.emptyBoxClicked(x, y));
        }
    }

    // La vue d'un bateau de l'armee1
    private class SmallBoatViewArmyOne extends BoxView {

        public SmallBoatViewArmyOne(int x, int y) {
            getStyleClass().add("SmallBoat1");
            setOnMouseClicked(e -> control.boatClicked(x, y));
        }
    }

    private class BigBoatViewArmyOne extends BoxView {

        public BigBoatViewArmyOne(int x, int y) {
            getStyleClass().add("BigBoat1");
            setOnMouseClicked(e -> control.boatClicked(x, y));
        }
    }

    // La vue d'un bateau de l'armee2
    private class SmallBoatViewArmyTwo extends BoxView {

        public SmallBoatViewArmyTwo(int x, int y) {
            getStyleClass().add("SmallBoat2");
            setOnMouseClicked(e -> control.boatClicked(x, y));
        }
    }

    private class BigBoatViewArmyTwo extends BoxView {

        public BigBoatViewArmyTwo(int x, int y) {
            getStyleClass().add("BigBoat2");
            setOnMouseClicked(e -> control.boatClicked(x, y));
        }
    }

    // La vue d'une mine
    private class MineView extends BoxView {

        public MineView(int x, int y) {
            getStyleClass().add("mine");
            setOnMouseClicked(e -> control.emptyBoxClicked(x, y));
        }
    }

    //La vue d'une mine radio
    private class MineRadioView extends BoxView {

        public MineRadioView(int x, int y) {
            getStyleClass().add("mineRadio");
            setOnMouseClicked(e -> control.emptyBoxClicked(x, y));
        }
    }

    // La vue d'une mine Radio activé
    private class MineRadioExplodedView extends BoxView {

        public MineRadioExplodedView(int x, int y) {
            getStyleClass().add("mineRadioExploded");
            setOnMouseClicked(e -> control.emptyBoxClicked(x, y));
        }
    }

    //La vue des positions possible
    private class PositionView extends BoxView {

        public PositionView(int x, int y) {
            getStyleClass().add("Position");
            setOnMouseClicked(e -> control.emptyBoxClicked(x, y));
        }
    }
}
