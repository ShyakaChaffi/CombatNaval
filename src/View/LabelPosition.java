package View;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LabelPosition extends Label {

    public LabelPosition(String text) {
        super(text);
        setLabelTab();
    }

    private void setLabelTab() {
        setFont(Font.font(null, FontWeight.BOLD, 15));
        setTextFill(Color.web("#FFFFFF"));
    }
}
