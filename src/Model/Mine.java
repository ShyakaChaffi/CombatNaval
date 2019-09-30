package Model;

import java.util.Random;

public abstract class Mine {

    MineType type;
    boolean radio = false;

    public abstract void setRadio();

    public MineType getType() {
        return type;
    }

    public boolean getRadio() {
        return radio;
    }

    public void setType(MineType type) {
        this.type = type;
    }

    public static Mine typeMine() {
        int rand = probability();
        if (rand > 0 && rand <= 50) {
            return new MineNormale();
        } else {
            return new MineRadio();
        }
    }

    public static boolean placeMine() {
        int rand = probability();
        return rand > 0 && rand <= 10;
    }

    public static int probability() {
        int res = new Random().nextInt();
        while (res < 0) {
            res = new Random().nextInt();
        }
        return (res % 100) + 1;
    }
}
