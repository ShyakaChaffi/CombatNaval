package Model;

public class BigBoat extends Boat {

    public BigBoat() {
        super();
        super.setResistance(2);
        super.setCapacityMove(1);
        this.type = BoatType.BIG;
    }

    @Override
    public double getIntegrity() {
        return ((double) this.getResistance() / 2) * 100;
    }

    @Override
    public int shootRange() {
        int rand = probability();
        if (rand > 0 && rand <= 50) {
            return 2;
        } else if (rand > 50 && rand <= 80) {
            return 1;
        } else {
            return 0;
        }
    }

}
