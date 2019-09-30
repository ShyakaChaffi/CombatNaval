package Model;

public class SmallBoat extends Boat {

    public SmallBoat() {
        super();
        super.setResistance(1);
        super.setCapacityMove(2);
        this.type = BoatType.SMALL;
    }

    @Override
    public double getIntegrity() {
        return ((double) this.getResistance() / 1) * 100;
    }

    @Override
    public int shootRange() {
        int rand = probability();
        if (rand > 0 && rand <= 50) {
            return 0;
        } else if (rand > 50 && rand <= 80) {
            return 1;
        } else {
            return 2;
        }
    }

}
