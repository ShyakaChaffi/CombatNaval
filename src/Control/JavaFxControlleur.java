package Control;

import Model.Army;
import Model.Boat;
import Model.BoatType;
import Model.MineType;
import View.MainWindowRoot;
import javafx.application.Application;
import javafx.stage.Stage;
import Model.SeaFacade;
import Model.Position;
import Model.SeaFacadeBuilder;
import View.ConstructWindow;
import View.SplashWindowRoot;
import java.util.ArrayList;

public class JavaFxControlleur extends Application {

    private Stage stage;
    private SeaFacade facade;
    private SeaFacadeBuilder builder;
    private Position currentPosition = null;
    private boolean playerOne = true;
    private boolean phaseShoot = true;
    private boolean inGame = false;
    private boolean gameFinished = false;
    private MainWindowRoot mainWindow = null;
    private ConstructWindow constructWindow = null;
    private Boat boatToPlace;
    private int size;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        new SplashWindowRoot(stage, this);
    }

    public void switchToMainWindow(Integer size, String army1, String army2, char placement) {
        this.size = size;
        if (placement == '1') {
            buildRandomSea(army1, army2, true);
            switchToMainWindowRoot();
        } else {
            switchToConstructWindow(size, army1, army2);
        }
    }

    private void switchToMainWindowRoot() {
        this.inGame = true;
        this.mainWindow = new MainWindowRoot(stage, this.size, this, this.facade.getArmyOne(), this.facade.getArmyTwo());
        this.facade.addObserver(this.mainWindow);
        this.facade.setChangedAndNotify();
        this.mainWindow.printMsgShoot(this.facade.getArmyOne());
    }

    private void switchToConstructWindow(int size, String army1, String army2) {
        this.builder = new SeaFacadeBuilder(size, army1, army2, false);
        this.constructWindow = new ConstructWindow(stage, size, this, this.builder.getArmyOne(), this.builder.getArmyTwo());
        this.builder.addObserver(constructWindow);
        this.builder.setChangedAndNotify();
        this.constructWindow.printMsgPlace(this.builder.getArmyOne());
    }

    private void buildRandomSea(String army1, String army2, boolean placement) {
        this.builder = new SeaFacadeBuilder(this.size, army1, army2, placement);
        this.facade = this.builder.getFacade();
    }

    private void phaseConstruct(int x, int y) {
        placeBoatInSea(x, y);
        if (this.playerOne) {
            switchToTurnPlayerTwo();
            this.constructWindow.printMsgPlace(this.builder.getArmyTwo());
        } else {
            switchToTurnPlayerOne();
            this.constructWindow.printMsgPlace(this.builder.getArmyOne());
        }
        if (this.builder.getNbBoatPlaced() == this.builder.getNBBOATSTOTAL()) {
            finalSeaBuilding();
            switchToMainWindowRoot();
        }
    }

    private void placeBoatInSea(int x, int y) {
        this.builder.setOneBoatInSea(this.boatToPlace, new Position(y - 1, x - 1));
        this.builder.boatPlaced();
        this.boatToPlace = null;
    }

    private void finalSeaBuilding() {
        this.builder.setMineInSea();
        this.facade = this.builder.getFacade();
    }

    private void turnPlayerOne(int x, int y) {
        if (this.phaseShoot && inGame) {
            phaseShoot(this.facade.getArmyOne(), this.facade.getArmyTwo(), x, y);
        } else {
            phaseSelectBoatToMovePlayer(x, y, this.facade.getArmyOne());
            this.mainWindow.clearMessagesInfo();
        }
    }

    private void turnPlayerTwo(int x, int y) {
        if (this.phaseShoot) {
            phaseShoot(this.facade.getArmyTwo(), this.facade.getArmyOne(), x, y);
        } else {
            phaseSelectBoatToMovePlayer(x, y, this.facade.getArmyTwo());
            this.mainWindow.clearMessagesInfo();
        }
    }

    private void phaseSelectBoatToMovePlayer(int x, int y, Army currentPlayer) {
        Position ancienneSelection = this.currentPosition;
        selectBoat(y - 1, x - 1);
        if (this.facade.positionInArmy(this.currentPosition, currentPlayer)) {
            if (ancienneSelection != null) {
                this.mainWindow.erasePossiblePosition(ancienneSelection, this.facade);
            }
            this.mainWindow.showPossiblePosition(currentPosition, this.facade);
        } else {
            if (ancienneSelection != null) {
                this.mainWindow.erasePossiblePosition(ancienneSelection, this.facade);
            }
        }
    }

    private void phaseShoot(Army currentPlayer, Army waitingPlayer, int x, int y) {
        selectBoat(y - 1, x - 1);
        if (this.facade.positionInArmy(currentPosition, currentPlayer)) {
            executeShoot(currentPosition, currentPlayer, waitingPlayer);
            switchToPhaseMove();
            this.mainWindow.printMsgMove(currentPlayer);
            if (waitingPlayer.getListBoatOfArmy().isEmpty()) {
                finishGame(currentPlayer);
            }
        }
    }

    private void selectBoat(int x, int y) {
        this.currentPosition = new Position(x, y);
    }

    private void phaseMoveBoatPlayer(Army currentPlayer, Army playerWaiting, int x, int y) {
        if (this.facade.positionInArmy(this.currentPosition, currentPlayer) && this.inGame) {
            if (checkIfNextPositionIsPossible(this.currentPosition, new Position(y - 1, x - 1), currentPlayer)) {
                moveBoatInNextPosition(this.currentPosition, new Position(y - 1, x - 1), currentPlayer);
                switchTurnPlayer();
                this.mainWindow.printMsgShoot(playerWaiting);
                if (currentPlayer.getListBoatOfArmy().isEmpty()) {
                    finishGame(playerWaiting);
                }
            }
        }
    }

    private void finishGame(Army currentPlayer) {
        this.inGame = false;
        this.gameFinished = true;
        this.mainWindow.printMsgWinner(currentPlayer);
        this.mainWindow.clearMessagesInfo();
    }

    private void executeShoot(Position currentPosition, Army currentPlayer, Army playerWaiting) {
        int range = this.facade.boatShooting(currentPosition, currentPlayer, playerWaiting);
        MainWindowRoot.printMsgRangeOfBoat(range);
    }

    private void moveBoatInNextPosition(Position currentPosition, Position nextPosition, Army currentPlayer) {
        if (!this.facade.checkIfMineInPosition(currentPosition) || !this.facade.checkIfBoatInPosition(currentPosition)) {
            if (!caseContainMineAndBoatSurvive(nextPosition, currentPosition, currentPlayer)) {
                this.facade.move(currentPosition, nextPosition, currentPlayer);
            }
        }
    }

    private boolean checkIfNextPositionIsPossible(Position currentPosition, Position nextPosition, Army currentPlayer) {
        ArrayList<Position> positionPossibles = this.facade.getPositionsForMove(this.facade.getCaseInPosition(currentPosition));
        if (positionPossibles.contains(nextPosition)) {
            return true;
        }
        MainWindowRoot.printMsgWrongMove(currentPlayer);
        return false;
    }

    private boolean caseContainMineAndBoatSurvive(Position nextPosition, Position currentPosition, Army currentPlayer) {
        if (this.facade.checkIfMineInPosition(nextPosition)) {
            if (this.facade.getMineInPosition(nextPosition).getType() == MineType.RADIO) {
                return executeMineRadio(nextPosition, currentPosition, currentPlayer);
            } else if (this.facade.getMineInPosition(nextPosition).getType() == MineType.NORMAL) {
                return executeMineNormal(nextPosition, currentPosition, currentPlayer);
            }
        }
        return false;
    }

    private boolean executeMineRadio(Position nextPosition, Position currentPosition, Army currentPlayer) {
        MainWindowRoot.printMineRadio();
        this.facade.removeBoatInArmy(currentPosition, currentPlayer);
        this.facade.setMineInRadioactive(nextPosition);
        return true;
    }

    private boolean executeMineNormal(Position nextPosition, Position currentPosition, Army currentPlayer) {
        MainWindowRoot.printMine();
        if (this.facade.setDegatMineAndCheckIfBoatLive(currentPosition, currentPlayer)) {
            return false;
        } else {
            this.facade.removeMineInPosition(nextPosition);
            return true;
        }
    }

    private void switchToPhaseMove() {
        this.phaseShoot = false;
    }

    private void switchTurnPlayer() {
        if (this.playerOne) {
            switchToTurnPlayerTwo();
        } else {
            switchToTurnPlayerOne();
        }
        switchToPhaseShoot();
    }

    private void switchToPhaseShoot() {
        this.phaseShoot = true;
    }

    private void switchToTurnPlayerOne() {
        this.playerOne = true;
    }

    private void switchToTurnPlayerTwo() {
        this.playerOne = false;
    }

    public void emptyBoxClicked(int x, int y) {
        if (!this.inGame && !this.gameFinished) {
            if (this.boatToPlace != null) {
                phaseConstruct(x, y);
            } else {
                printErrorMessage();
            }
        } else if (this.inGame) {
            emptyBoxClickedGamePhase(x, y);
        }
    }

    public void boatClicked(int x, int y) {
        if (this.playerOne && this.inGame) {
            turnPlayerOne(x, y);
          
        } else if (!this.playerOne && this.inGame) {
            turnPlayerTwo(x, y);
            
        }
    }

    private void printErrorMessage() {
        if (this.playerOne) {
            this.constructWindow.printErrorMsgEmptyBoat(this.builder.getArmyOne());
        } else {
            this.constructWindow.printErrorMsgEmptyBoat(this.builder.getArmyTwo());
        }
    }

    public void emptyBoxClickedGamePhase(int x, int y) {
        if (!this.phaseShoot && this.inGame) {
            if (this.playerOne) {
                phaseMoveBoatPlayer(this.facade.getArmyOne(), this.facade.getArmyTwo(), x, y);
            } else {
                phaseMoveBoatPlayer(this.facade.getArmyTwo(), this.facade.getArmyOne(), x, y);
            }
        }
    }

    public void boatClickedInHarbor(String armyName, Boat boat) {
        if (this.playerOne) {
            phasePlacementPlayerOne(armyName, boat);
        } else {
            phasePlacementPlayerTwo(armyName, boat);
        }
    }

    private void phasePlacementPlayerOne(String armyName, Boat boat) {
        if (armyName.equals(this.builder.getArmyOne().getName())) {
            if (boat.getType() == BoatType.SMALL && boat.getPos() == null) {
                this.boatToPlace = this.builder.getOneSmallBoatArmyOne();
            } else if (boat.getType() == BoatType.BIG && boat.getPos() == null) {
                this.boatToPlace = this.builder.getOneBigBoatArmyOne();
            }
            this.constructWindow.printPlaceBoat(this.builder.getArmyOne());
        } else {
            this.constructWindow.printErrorMsgWrongBoat(this.builder.getArmyOne());
        }
    }

    private void phasePlacementPlayerTwo(String armyName, Boat boat) {
        if (armyName.equals(this.builder.getArmyTwo().getName())) {
            if (boat.getType() == BoatType.SMALL && boat.getPos() == null) {
                this.boatToPlace = this.builder.getOneSmallBoatArmyTwo();
            } else if (boat.getType() == BoatType.BIG && boat.getPos() == null) {
                this.boatToPlace = this.builder.getOneBigBoatArmyTwo();
            }
            this.constructWindow.printPlaceBoat(this.builder.getArmyTwo());
        } else {
            this.constructWindow.printErrorMsgWrongBoat(this.builder.getArmyTwo());
        }
    }
}
