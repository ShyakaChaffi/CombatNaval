package Model;

import java.util.ArrayList;

public final class SeaFacade extends MainClassSeaFacade {

    private final Sea sea;
    private Army playerOne;
    private Army playerTwo;

    public SeaFacade() {
        this.sea = new Sea();
    }

    public SeaFacade(int size, String armee1, String armee2) {
        this.sea = new Sea(size);
        this.playerOne = new Army(armee1);
        this.playerTwo = new Army(armee2);
        initSea();
    }

    public void initSea() {
        this.sea.setBoatsInSea(playerOne);
        this.sea.setBoatsInSea(playerTwo);
        this.setMineInSea();
        setChangedAndNotify();
    }

    public void createNewArmee(String name) {
        if (this.playerOne == null) {
            createPlayerOne(name);
        } else {
            createPlayerTwo(name);
        }
    }

    private void createPlayerOne(String name) {
        this.playerOne = new Army(name);
        this.playerOne.setCol(Color.GREEN);
    }

    private void createPlayerTwo(String name) {
        this.playerTwo = new Army(name);
        this.playerTwo.setCol(Color.BLUE);
    }

    public void setBoatsInSea(Army currentPlayer) {
        this.sea.setBoatsInSea(currentPlayer);
    }

    public void setSize(int x) {
        this.sea.setSize(x);
    }

    void setArmyOne(String armee1) {
        this.playerOne = new Army(armee1);
    }

    @Override
    public Boat getOneSmallBoatArmyOne() {
        return this.playerOne.getOneSmallBoat();
    }

    @Override
    public Boat getOneSmallBoatArmyTwo() {
        return this.playerTwo.getOneSmallBoat();
    }

    @Override
    public Boat getOneBigBoatArmyOne() {
        return this.playerOne.getOneBigBoat();
    }

    @Override
    public Boat getOneBigBoatArmyTwo() {
        return this.playerTwo.getOneBigBoat();
    }

    void setArmyTwo(String armee2) {
        this.playerTwo = new Army(armee2);
    }

    public void setArmyInSea(Army currentPlayer) {
        this.sea.setBoatsInSea(currentPlayer);
    }

    @Override
    public void setOneBoatInSea(Boat boat, Position p) {
        this.sea.setOneBoatsInSea(boat, p);
    }

    @Override
    public void setMineInSea() {
        this.sea.setMineInSea();
    }

    @Override
    public int getSize() {
        return this.sea.getSize();
    }

    public int getSizeOfArmy(Army currentPlayer) {
        return currentPlayer.getSize();
    }

    @Override
    public Army getArmyOne() {
        return this.playerOne;
    }

    @Override
    public Army getArmyTwo() {
        return this.playerTwo;
    }

    public Boat getBoatInPosition(Position p) {
        return this.sea.getBoatInPosition(p);
    }

    @Override
    public Mine getMineInPosition(Position p) {
        return this.sea.getMineInPosition(p);
    }

    @Override
    public ArrayList<Position> getListPositionsOfMine() {
        return this.sea.getListPositionsOfMine();
    }

    public Position getPositionStringIntoObjectPosition(String selectionBateau) {
        return this.sea.convertStringPositionIntoObjectPosition(selectionBateau);
    }

    public Case getCaseInPosition(Position p) {
        return this.sea.getCaseInPosition(p);
    }

    public ArrayList getPositionsForMove(Case c) {
        return this.sea.getPositionsForMove(c);
    }

    public boolean checkIfMineInPosition(Position p) {
        return this.sea.checkIfMineInPosition(p);
    }

    public boolean checkIfBoatInPosition(Position p) {
        return this.sea.checkIfBoatInPosition(p);
    }

    public boolean positionInArmy(Position p, Army army) {
        return Army.positionInArmy(p, army);
    }

    public boolean checkIfCaseIsRadioactive(Position p) {
        return this.sea.checkIfCaseIsRadioactive(p);
    }

    public boolean setDegatMineAndCheckIfBoatLive(Position p, Army army) {
        if (army.SetDegatBoatInArmy(p)) {
            this.removeBoatInArmy(p, army);
            return false;
        }
        return true;
    }

    public boolean ArmysIsStillAlive() {
        return this.getSizeOfArmy(playerOne) > 0 && this.getSizeOfArmy(playerTwo) > 0;
    }

    public void removeBoatInArmy(Position p, Army army) {
        army.removeBoatInArmy(p);
        this.sea.removeCaseInPosition(p);
    }

    public void removeMineInPosition(Position currentPosition) {
        this.sea.removeCaseInPosition(currentPosition);
        setChangedAndNotify();
    }

    public void setMineInRadioactive(Position currentPosition) {
        this.getMineInPosition(currentPosition).setRadio();
        setChangedAndNotify();
    }

    public void move(Position currentPosition, Position nextPosition, Army currentPlayer) {
        currentPlayer.moveBoat(currentPosition, nextPosition);
        this.sea.moveBoatInNewPosition(currentPosition, nextPosition);
        setChangedAndNotify();
    }

    public int boatShooting(Position currentPosition, Army currentPlayer, Army playerWaiting) {
        Boat boatSelected = this.getBoatInPosition(currentPosition);
        int range = boatSelected.shootRange();
        ArrayList<Position> listPos = this.sea.getPositionsInRangeShooting(currentPosition, range);
        if (!listPos.isEmpty()) {
            if (boatSelected.getType() == BoatType.BIG) {
                shootBigBoat(listPos, playerWaiting);
            } else {
                shootSmallBoat(listPos, playerWaiting);
            }
        }
        setChangedAndNotify();
        return range;
    }

    private void shootBigBoat(ArrayList<Position> possiblePositions, Army playerWaiting) {
        for (Position p : possiblePositions) {
            this.setDegatMineAndCheckIfBoatLive(p, playerWaiting);
        }
    }

    private void shootSmallBoat(ArrayList<Position> possiblePositions, Army playerWaiting) {
        ArrayList<Position> boatInRange = new ArrayList<>();
        for (Position p : possiblePositions) {
            if (Army.positionInArmy(p, playerWaiting)) {
                boatInRange.add(p);
            }
        }
        if (!boatInRange.isEmpty()) {
            this.setDegatMineAndCheckIfBoatLive(this.sea.getRandomPositionForAShoot(boatInRange), playerWaiting);
        }
    }

}
