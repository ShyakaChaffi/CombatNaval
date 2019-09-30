package Model;

public class Case {

    private Position position;
    private Mine mine = null;
    private Boat boat = null;

    public Case(Position p) {
        this.position = p;
    }

    public Position getPosition() {
        return position;
    }

    public Mine getMine() {
        return mine;
    }

    public Boat getBoat() {
        return boat;
    }

    public void setMine(Mine m) {
        this.mine = m;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setBat(Boat b) {
        this.boat = b;
    }
}
