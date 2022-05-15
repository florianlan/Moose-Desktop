package sample;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.Random;

public class Controller {
    private final int lineLength = 60;
    private final int cols = 4;
    private final int rows = 3;
    private final int offsetX = lineLength / (cols-1);
    private final int offsetY = lineLength / (rows-1);

    @FXML
    private Pane click_field;

    @FXML
    private Line hori_line;

    @FXML
    private Line vert_line;

    @FXML
    private AnchorPane test_area;

    @FXML
    void btnClicked(MouseEvent event) {
        Random random = new Random();
        int rndHor = random.nextInt(cols);
        int rndVer = random.nextInt(rows);
        if (random.nextFloat() > 0.7f) {
            click_field.setLayoutX(random.nextInt((int) test_area.getWidth()-100));
            click_field.setLayoutY(random.nextInt((int) test_area.getHeight()-100));
        }

        hori_line.setStartY(rndVer*offsetY);
        hori_line.setEndY(rndVer*offsetY);
        vert_line.setStartX(rndHor*offsetX);
        vert_line.setEndX(rndHor*offsetX);

    }

}
