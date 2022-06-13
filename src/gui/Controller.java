package gui;

import data.Data;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.Random;

public class Controller {
    private final int lineLength = 60;
    private int offsetX;
    private int offsetY;
    private int oldX = 0;
    private int oldY = 0;
    private boolean hovered = false;

    private static Controller INSTANCE;

    /**
     * Singleton instantiation
     */
    public static Controller getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Controller();
        }
        return INSTANCE;
    }

    public Controller() {
        INSTANCE = this;
    }

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
        newExercise();

    }

    @FXML
    void btnHover(MouseEvent event) {
        hori_line.setVisible(true);
        vert_line.setVisible(true);
        hovered = true;

        //TODO: start counting time for exercise
    }

    @FXML
    void btnHoverExit(MouseEvent event) {
        hovered = false;
    }

    public void success() {
        newExercise();
    }

    /**
     * Getter
     */
    public boolean isHovered() {
        return hovered;
    }

    public void newExercise() {
        Random random = new Random();
        offsetX = lineLength / (Data.getInstance().getCols() - 1);
        offsetY = lineLength / (Data.getInstance().getRows() - 1);

        //new place of square
        if (random.nextFloat() > 0.7f) {
            click_field.setLayoutX(random.nextInt((int) test_area.getWidth() - 100));
            click_field.setLayoutY(random.nextInt((int) test_area.getHeight() - 100));
            //set lines invisible
            hori_line.setVisible(false);
            vert_line.setVisible(false);
            hovered = false;
        }

        //new symbol
        int rndHor;
        int rndVer;
        do {
            rndHor = random.nextInt(Data.getInstance().getCols());
            rndVer = random.nextInt(Data.getInstance().getRows());
            hori_line.setStartY(rndVer * offsetY);
            hori_line.setEndY(rndVer * offsetY);
            vert_line.setStartX(rndHor * offsetX);
            vert_line.setEndX(rndHor * offsetX);
        } while ((int) hori_line.getStartY() == oldY && (int) vert_line.getStartX() == oldX);

        oldY = (int) hori_line.getStartY();
        oldX = (int) vert_line.getStartX();

        // Set expected symbol
        Data.getInstance().setExpectedX(rndHor + 1);
        Data.getInstance().setExpectedY(rndVer + 1);


    }

}
