package Model;

import java.util.ArrayList;

public final class SeaFacadeBuilder extends MainClassSeaFacade {

    private final SeaFacade facade;
    private int nbBoatPlaced = 0;
    private int NBBOATSTOTAL;

    public SeaFacadeBuilder(int size, String armyOne, String armyTwo, boolean randomPlacement) {
        if (randomPlacement) {
            this.facade = new SeaFacade(size, armyOne, armyTwo);
        } else {
            this.facade = new SeaFacade();
            this.facade.setSize(size);
            this.facade.setArmyOne(armyOne);
            this.facade.setArmyTwo(armyTwo);
            this.NBBOATSTOTAL = (this.facade.getSizeOfArmy(this.getArmyOne()) * 2);
        }
    }

    public int getNBBOATSTOTAL() {
        return this.NBBOATSTOTAL;
    }

    public int getNbBoatPlaced() {
        return nbBoatPlaced;
    }

    public void boatPlaced() {
        ++nbBoatPlaced;
    }

    public SeaFacade getFacade() {
        return this.facade;
    }

    @Override
    public Army getArmyOne() {
        return this.facade.getArmyOne();
    }

    @Override
    public Army getArmyTwo() {
        return this.facade.getArmyTwo();
    }

    @Override
    public int getSize() {
        return this.facade.getSize();
    }

    @Override
    public ArrayList<Position> getListPositionsOfMine() {
        return this.facade.getListPositionsOfMine();
    }

    @Override
    public Mine getMineInPosition(Position p) {
        return this.facade.getMineInPosition(p);
    }

    @Override
    public void setOneBoatInSea(Boat place, Position p) {
        this.facade.setOneBoatInSea(place, p);
        setChangedAndNotify();
    }

    @Override
    public Boat getOneSmallBoatArmyOne() {
        return this.facade.getOneSmallBoatArmyOne();
    }

    @Override
    public Boat getOneSmallBoatArmyTwo() {
        return this.facade.getOneSmallBoatArmyTwo();
    }

    @Override
    public Boat getOneBigBoatArmyOne() {
        return this.facade.getOneBigBoatArmyOne();
    }

    @Override
    public Boat getOneBigBoatArmyTwo() {
        return this.facade.getOneBigBoatArmyTwo();
    }

    @Override
    public void setMineInSea() {
        this.facade.setMineInSea();
    }
}
