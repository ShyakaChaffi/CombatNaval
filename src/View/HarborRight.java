package View;

import Control.JavaFxControlleur;
import Model.Boat;
import Model.BoatType;
import java.util.List;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

public class HarborRight extends GridPane {

    private final JavaFxControlleur control;
    int size;

    HarborRight(String armyName, List<Boat> listBoatOfArmy, JavaFxControlleur ctrl, int size) {
        control = ctrl;
        this.size = size;
        setSizeConstraints(listBoatOfArmy.size());
        insertBoats(listBoatOfArmy, armyName);
    }

    private void setSizeConstraints(int size) {
        for (int i = 0; i < size; ++i) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setMinWidth(50);
            getColumnConstraints().add(cc);
            RowConstraints rc = new RowConstraints();
            rc.setMinHeight(50);
            getRowConstraints().add(rc);

        }
    }

    private void insertBoats(List<Boat> listBoatOfArmy, String armyName) {
        insertBoatsForPlayerOne(listBoatOfArmy, armyName);
    }

    private void insertBoatsForPlayerOne(List<Boat> boats, String armyName) {
        int x = 0;
        int y = 0;
        for (Boat boat : boats) {
            if (boat.getPos() == null) {
                if (boat.getType() == BoatType.SMALL) {
                    add(new SmallBoatViewArmyTwo(armyName, boat), x, y); //x et y de viewboat est surement inutile
                } else if (boat.getType() == BoatType.BIG) {
                    add(new BigBoatViewArmyTwo(armyName, boat), x, y); //x et y de viewboat est surement inutile
                }
                ++x;
            }
        }
    }

    private abstract class BoxView extends Pane {

        public BoxView() {
            getStylesheets().add("view/BoxView.css");
        }
    }

    // La vue d'un bateau de l'armee1
    private class SmallBoatViewArmyTwo extends BoxView {

        public SmallBoatViewArmyTwo(String armyName, Boat boat) {
            getStyleClass().add("SmallBoat2");
            setOnMouseClicked(e -> control.boatClickedInHarbor(armyName, boat));
        }
    }

    private class BigBoatViewArmyTwo extends BoxView {

        public BigBoatViewArmyTwo(String armyName, Boat boat) {
            getStyleClass().add("BigBoat2");
            setOnMouseClicked(e -> control.boatClickedInHarbor(armyName, boat));
        }
    }
}
