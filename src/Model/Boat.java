package Model;

import java.util.Random;

public abstract class Boat {

    private int capacityMove;
    private int resistance;
    private Position pos = null;
    BoatType type;
    Color col = Color.BLACK;

    public abstract int shootRange();

    public abstract double getIntegrity();

    public int getCapacityMove() {
        return capacityMove;
    }

    public int getResistance() {
        return resistance;
    }

    public Position getPos() {
        return pos;
    }

    public BoatType getType() {
        return type;
    }

    public Color getCol() {
        return col;
    }

    public void setCapacityMove(int capaciteMove) {
        this.capacityMove = capaciteMove;
    }

    public void setResistance(int resistance) {
        this.resistance += resistance;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public void setCol(Color col) {
        this.col = col;
    }

    public int probability() {
        int res = new Random().nextInt();
        while (res < 0) {
            res = new Random().nextInt();
        }
        return (res % 100) + 1;
    }
}
