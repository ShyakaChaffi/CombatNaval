package Model;

public class MineNormale extends Mine {

    public MineNormale() {
        type = MineType.NORMAL;
    }

    @Override
    public void setRadio() {
        this.radio = false;
    }

}
