package Control;

import Model.Army;
import Model.MineType;
import Model.Position;
import View.View;
import Model.SeaFacade;
import java.util.ArrayList;

public class ConsolController {

    private final SeaFacade facade;

    public static void main(String[] args) {
        new ConsolController();
    }

    public ConsolController() {
        this.facade = new SeaFacade();
        this.facade.addObserver(View.getInstance());
        initData();
    }

    private void initData() {
        this.facade.createNewArmee(View.askName("1"));
        this.facade.createNewArmee(View.askName("2"));
        this.facade.initSea();
        playing();
    }

    private void playing() {
        while (playerOneInGame() && playerTwoInGame()) {
//            playing();
//        } else {
        }
        View.montrerGagnant(this.facade);

    }

    private boolean playerOneInGame() {
        return phaseShootConsole(this.facade.getArmyOne(), this.facade.getArmyTwo())
                && phaseMoveConsole(this.facade.getArmyOne());
    }

    private boolean playerTwoInGame() {
        return phaseShootConsole(this.facade.getArmyTwo(), this.facade.getArmyOne())
                && phaseMoveConsole(this.facade.getArmyTwo());
    }

    private boolean phaseShootConsole(Army currentPlayer, Army playerWaiting) {
        Position currentPosition = getPositionOfBoatToShootInConsole(currentPlayer);
        if (this.facade.positionInArmy(currentPosition, currentPlayer)) {
            View.shootingRange(this.facade.boatShooting(currentPosition, currentPlayer, playerWaiting));
        }
        return this.facade.ArmysIsStillAlive();
    }

    private boolean phaseMoveConsole(Army currentPlayer) {
        Position currentPosition = getPositionOfBoatToMoveInConsole(currentPlayer);
        ArrayList<Position> possiblePositions = this.facade.getPositionsForMove(this.facade.getCaseInPosition(currentPosition));
        View.showPositionPossible(possiblePositions);
        Position nextPosition = getNewPositionForSelectedBoatInConsole(possiblePositions);
        if (checkIfMineInPositionAndIfBoatSurvived(nextPosition, currentPosition, currentPlayer)) {
            this.facade.move(currentPosition, nextPosition, currentPlayer);
        }
        return this.facade.ArmysIsStillAlive();
    }

    // Fonction identique en faisant des boucles en dessous /////////////////////////
    private Position getPositionOfBoatToMoveInConsole(Army currentPlayer) {
        Position currentPosition = this.facade.getPositionStringIntoObjectPosition(View.selectBoat(currentPlayer));
        if (this.facade.positionInArmy(currentPosition, currentPlayer)) {
            return currentPosition;
        }
        return getPositionOfBoatToMoveInConsole(currentPlayer);
    }

    private Position getPositionOfBoatToShootInConsole(Army currentPlayer) {
        Position currentPosition = this.facade.getPositionStringIntoObjectPosition(View.selectBoatToShoot(currentPlayer));
        if (this.facade.positionInArmy(currentPosition, currentPlayer)) {
            return currentPosition;
        }
        return getPositionOfBoatToShootInConsole(currentPlayer);
    }

    private Position getNewPositionForSelectedBoatInConsole(ArrayList<Position> possiblePositions) {
        Position nextPosition = this.facade.getPositionStringIntoObjectPosition(View.selectPosition());
        if (possiblePositions.contains(nextPosition)) {
            return nextPosition;
        }
        return getNewPositionForSelectedBoatInConsole(possiblePositions);
    }
    ///////////////////////////////////////////////////////////////////////////////
    // fonction avec boucle
//    private Position getPositionOfBoatToShootInConsole(Army currentPlayer) {
//        Position currentPosition = this.facade.getPositionStringIntoObjectPosition(View.selectBoatToShoot(currentPlayer));
//        while (!this.facade.positionInArmy(currentPosition, currentPlayer)) {
//            currentPosition = this.facade.getPositionStringIntoObjectPosition(View.selectBoatToShoot(currentPlayer));
//        }
//        return currentPosition;
//    }
//
//    private Position getPositionOfBoatToMoveInConsole(Army currentPlayer) {
//        Position currentPosition = this.facade.getPositionStringIntoObjectPosition(View.selectBoat(currentPlayer));
//        while (!this.facade.positionInArmy(currentPosition, currentPlayer)) {
//            currentPosition = this.facade.getPositionStringIntoObjectPosition(View.selectBoat(currentPlayer));
//        }
//        return currentPosition;
//    }
//
//    private Position getNewPositionForSelectedBoatInConsole(ArrayList<Position> possiblePositions) {
//        Position nextPosition = this.facade.getPositionStringIntoObjectPosition(View.selectPosition());
//        while (!possiblePositions.contains(nextPosition)) {
//            nextPosition = this.facade.getPositionStringIntoObjectPosition(View.selectPosition());
//        }
//        return nextPosition;
//    }

    private boolean checkIfMineInPositionAndIfBoatSurvived(Position nextPosition, Position currentPosition, Army currentPlayer) {
        if (this.facade.checkIfMineInPosition(nextPosition)) {
            if (this.facade.getMineInPosition(nextPosition).getType() == MineType.RADIO) {
                return activeMineRadio(currentPosition, currentPlayer, nextPosition);
            } else if (this.facade.getMineInPosition(nextPosition).getType() == MineType.NORMAL) {
                return activeMineNormal(currentPosition, currentPlayer, nextPosition);
            }
        }
        return true;
    }

    private boolean activeMineRadio(Position currentPosition, Army currentPlayer, Position nextPosition) {
        View.printMineRadio();
        this.facade.removeBoatInArmy(currentPosition, currentPlayer);
        this.facade.setMineInRadioactive(nextPosition);
        return false;
    }

    private boolean activeMineNormal(Position currentPosition, Army currentPlayer, Position nextPosition) {
        View.printMine();
        if (this.facade.setDegatMineAndCheckIfBoatLive(currentPosition, currentPlayer)) {
            return true;
        } else {
            this.facade.removeMineInPosition(nextPosition);
            return false;
        }
    }

}
