package View;

import Model.Army;
import Model.Boat;
import Model.BoatType;
import Model.Position;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SidePanel extends VBox {

    Label armyComposition;

    public SidePanel(Army army) {

        this.armyComposition = new Label(printArmy(army));
    }

    private String printArmy(Army army) {
        String res = "";
        res += printNameColumDescriptionArmy();
        for (Boat bat : army.getListBoatOfArmy()) {
            res += printPositionOfBoat(bat.getPos()) + "\t";
            res += printBoatsInArmy(bat);
        }
        return res;
    }

    private static String printNameColumDescriptionArmy() {
        return "Position - Bateaux - Integrité" + "\n";
    }

    private static String printBoatsInArmy(Boat bat) {
        if (bat.getType() == BoatType.BIG) {
            return "BIG" + "\t" + "\t" + bat.getIntegrity() + "\n";
        } else {
            return "SMALL" + "\t" + bat.getIntegrity() + "\n";
        }
    }

    private static String printPositionOfBoat(Position pos) {
        return pos.columnToChar() + (pos.getLine() + 1) + "\t" + "   ";
    }
}
