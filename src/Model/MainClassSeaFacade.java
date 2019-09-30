package Model;

import java.util.ArrayList;
import java.util.Observable;

public abstract class MainClassSeaFacade extends Observable {

    public abstract Army getArmyOne();

    public abstract Army getArmyTwo();

    public abstract int getSize();

    public abstract ArrayList<Position> getListPositionsOfMine();

    public abstract Mine getMineInPosition(Position p);

    public abstract void setOneBoatInSea(Boat place, Position p);

    public abstract Boat getOneSmallBoatArmyOne();

    public abstract Boat getOneSmallBoatArmyTwo();

    public abstract Boat getOneBigBoatArmyOne();

    public abstract Boat getOneBigBoatArmyTwo();

    public abstract void setMineInSea();

    public void setChangedAndNotify() {
        setChanged();
        notifyObservers();
    }
}
