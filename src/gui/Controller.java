package gui;

import data.Data;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import tools.TrialInfo.INFO;
import trials.Trial;
import trials.TrialRun;

import java.sql.Timestamp;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Controller {
    private boolean hovered;
    private Timestamp timestamp;

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
    private Button click_start;

    @FXML
    private Line hori_line;

    @FXML
    private Label lbl_info;

    @FXML
    private AnchorPane test_area;

    @FXML
    private Line vert_line;

    @FXML
    void btnClicked(MouseEvent event) {

    }

    @FXML
    void btnHover(MouseEvent event) {
        //TODO: start counting time for exercise
        //timestamp = new Timestamp(System.currentTimeMillis()); // only when new position

        hori_line.setVisible(true);
        vert_line.setVisible(true);
        hovered = true;

    }

    @FXML
    void btnHoverExit(MouseEvent event) {
        hovered = false;
    }


    @FXML
    void btnStartClick(MouseEvent event) {
        if (Data.getInstance().getRows() > 0) { //da verbindung hergestellt zu smartphone
            click_start.setVisible(false);
            click_field.setVisible(true);
            click_field.setDisable(false);

            //setting up Trial Run
            TrialRun.getInstance();

            //show Trial text field
            lbl_info.setVisible(true);
            updateTextField();

            //setup first exercise
            Trial trial = TrialRun.getInstance().getRandomTrial();
            setExercise(trial.getRow(), trial.getCol());

        } else {
            throw new RuntimeException("device not connected");
        }
    }


    public void success() {
        startNewTrial();
        if (TrialRun.getInstance().getActualTrial() != null) {
            TrialRun.getInstance().getActualTrial().setSuccessTrialsLeft(TrialRun.getInstance().getActualTrial().getSuccessTrialsLeft() - 1);
            if (TrialRun.getInstance().getActualTrial().getSuccessTrialsLeft() == 0) {
                TrialRun.getInstance().removeTrial(TrialRun.getInstance().getActualTrial());
            }
        }
    }

    public void noSuccess() {
        startNewTrial();
        if (TrialRun.getInstance().getActualTrial() != null) {
            TrialRun.getInstance().getActualTrial().setFailTrialsLeft(TrialRun.getInstance().getActualTrial().getFailTrialsLeft() - 1);
            if (TrialRun.getInstance().getActualTrial().getFailTrialsLeft() == 0) {
                TrialRun.getInstance().removeTrial(TrialRun.getInstance().getActualTrial());
            }
        }
    }

    public void startNewTrial() {
        Trial newTrial = TrialRun.getInstance().getRandomTrial();
        if (newTrial != null) {//when null take a break and start new Block
            setExercise(newTrial.getRow(), newTrial.getCol());
        } else {
            Platform.runLater(this::takeABrake); //TODO: make a threadsafe brake
            //takeABrake(30);// 30 seconds break and new brake

        }
    }

    /**
     * Take a brake and start new Block if needed
     */
    private void takeABrake() {
        int sec = INFO.BRAKE_TIME;
        click_field.setDisable(true);
        click_field.setVisible(false);

        if (INFO.BLOCK_NR >= INFO.BLOCK_AMOUNT) { //finish test
            lbl_info.setText("finished HCI study! Thanks for your participation");
            try {
                TimeUnit.SECONDS.sleep(sec);
                lbl_info.setText(sec + " seconds brake!");
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            System.exit(0); //exit Program after study

        } else { //brake and next block
            //sleep for @sec seconds
            try {
                TimeUnit.SECONDS.sleep(sec);
                lbl_info.setText(sec + " seconds brake!");
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            //setup new block
            INFO.TRIAL_NR = 0;
            INFO.BLOCK_NR++;
            updateTextField();
            TrialRun.getInstance().startNewBlock();
            click_field.setDisable(false);
            click_field.setVisible(true);
            Trial newTrial = TrialRun.getInstance().getRandomTrial();
            setExercise(newTrial.getRow(), newTrial.getCol());

        }

    }

    /**
     * Getter
     */
    public boolean isHovered() {
        return hovered;
    }

    public void setExercise(int row, int col) {
        Random random = new Random();
        int lineLength = 60;
        int offsetX = lineLength / (Data.getInstance().getCols() - 1);
        int offsetY = lineLength / (Data.getInstance().getRows() - 1);

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
        hori_line.setStartY((row - 1) * offsetY);
        hori_line.setEndY((row - 1) * offsetY);
        vert_line.setStartX((col - 1) * offsetX);
        vert_line.setEndX((col - 1) * offsetX);

        // Set expected symbol
        Data.getInstance().setExpectedX(col);
        Data.getInstance().setExpectedY(row);

        //update Trial Label
        INFO.TRIAL_NR++;
        INFO.TASK_ID++;
        updateTextField();

    }

    private void updateTextField() {
        int trails = Data.getInstance().getRows() * Data.getInstance().getCols() * INFO.TRIAL_AMOUNT_PER_BLOCK;
        lbl_info.setText("Block: " + INFO.BLOCK_NR + "/" + INFO.BLOCK_AMOUNT + "; Trail: " + INFO.TRIAL_NR + "/" + trails);

    }

}
