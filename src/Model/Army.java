package Model;

import java.util.ArrayList;
import java.util.List;

public class Army {

    private final String name;
    private final int numerOfSmallBoat = 2;
    private final int numberOfBigBoat = 1;
    private Color col = Color.BLACK;
    private final List<Boat> listBoatsInArmy = new ArrayList<>();

    public Army() {
        this("");
    }

    public Army(String nom) {
        this.name = nom;
        for (int i = 0; i < this.numberOfBigBoat; ++i) {
            listBoatsInArmy.add(new BigBoat());
        }
        for (int i = 0; i < this.numerOfSmallBoat; ++i) {
            listBoatsInArmy.add(new SmallBoat());
        }
    }

    public String getName() {
        return name;
    }

    public List<Boat> getListBoatOfArmy() {
        return listBoatsInArmy;
    }

    public int getSize() {
        return this.listBoatsInArmy.size();
    }

    public Color getCol() {
        return col;
    }

    public void setCol(Color col) {
        this.col = col;
        for (Boat b : listBoatsInArmy) {
            b.setCol(col);
        }
    }

    public boolean removeBoatInArmy(Position pos) {
        for (Boat bat : this.listBoatsInArmy) {
            if (bat.getPos().equals(pos)) {
                this.listBoatsInArmy.remove(bat);
                return true;
            }
        }
        return false;
    }

    public boolean SetDegatBoatInArmy(Position pos) {
        for (Boat bat : this.listBoatsInArmy) {
            if (bat.getPos().equals(pos)) {
                int index = this.listBoatsInArmy.lastIndexOf(bat);
                Boat batSelected = this.listBoatsInArmy.get(index);
                batSelected.setResistance(-1);
                if (batSelected.getResistance() == 0) {
                    return this.removeBoatInArmy(pos);
                }
            }
        }
        return false;
    }

    void moveBoat(Position p1, Position p2) {
        for (Boat bat : this.listBoatsInArmy) {
            if (bat.getPos().equals(p1)) {
                int index = this.listBoatsInArmy.lastIndexOf(bat);
                Boat batSelected = this.listBoatsInArmy.get(index);
                batSelected.setPos(p2);
            }
        }
    }

    public static boolean positionInArmy(Position pos, Army armee) {
        for (Boat bat : armee.listBoatsInArmy) {
            if (bat.getPos().equals(pos)) {
                return true;
            }
        }
        return false;
    }

    public Boat getOneSmallBoat() {
        Boat boatToReturn = null;
        for (Boat b : this.listBoatsInArmy) {
            if (b.getType() == BoatType.SMALL && b.getPos() == null) {
                boatToReturn = b;
            }
        }
        return boatToReturn;
    }

    public Boat getOneBigBoat() {
        Boat boatToReturn = null;
        for (Boat b : this.listBoatsInArmy) {
            if (b.getType() == BoatType.BIG && b.getPos() == null) {
                boatToReturn = b;
            }
        }
        return boatToReturn;
    }

}
