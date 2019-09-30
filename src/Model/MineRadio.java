package Model;

public class MineRadio extends Mine {

    public MineRadio() {
        type = MineType.RADIO;
    }

    @Override
    public void setRadio() {
        this.radio = true;
    }
}
